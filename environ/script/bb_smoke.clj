#!/usr/bin/env bb
;; Babashka smoke test: environ should load and read the environment under bb.
;; Run from the environ/ directory: FOO_BAR=baz bb -cp src script/bb_smoke.clj
(require '[environ.core :refer [env read-env]])

(defn- check [pred msg]
  (when-not pred
    (binding [*out* *err*] (println "FAIL:" msg))
    (System/exit 1)))

(check (map? env)                     "env is a map")
(check (= "baz" (:foo-bar env))       "reads FOO_BAR -> :foo-bar")
(check (= (System/getenv "USER")
          (:user env))                "reads USER -> :user")
(check (map? (read-env))              "read-env re-reads at runtime")

(println "babashka smoke test: OK")
