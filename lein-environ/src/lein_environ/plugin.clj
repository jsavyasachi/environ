(ns lein-environ.plugin
  (:require [clojure.java.io :as io]
            [robert.hooke :refer [add-hook]]
            leiningen.core.main))

(defn- as-edn [& args]
  (binding [*print-dup*    false
            *print-meta*   false
            *print-length* false
            *print-level*  false]
    (apply prn-str args)))

(defn- map-vals [f m]
  (reduce-kv #(assoc %1 %2 (f %3)) {} m))

(defn- replace-project-keyword [value project]
  (if (and (keyword? value) (= (namespace value) "project"))
    (project (keyword (name value)))
    value))

(defn read-env
  "Returns the `:env` map from the Leiningen `project`, resolving any
  `:project/foo` keyword values to the corresponding `:foo` key in the project
  map."
  [project]
  (map-vals #(replace-project-keyword % project) (:env project {})))

(defn env-file
  "Returns the `.lein-env` java.io.File in the `project` root, where the resolved
  environment is written for environ.core to read."
  [project]
  (io/file (:root project) ".lein-env"))

(defn- write-env-to-file [func task-name project args]
  (spit (env-file project) (as-edn (read-env project)))
  (func task-name project args))

(defn hooks
  "Leiningen hook entry point, applied automatically when lein-environ is in
  `:plugins`. Wraps `apply-task` so the resolved `:env` map is written to the
  project's `.lein-env` file before each task runs, for environ.core to read."
  []
  (add-hook #'leiningen.core.main/apply-task #'write-env-to-file))
