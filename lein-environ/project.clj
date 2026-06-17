(defproject net.clojars.savya/lein-environ "1.4.3"
  :description "Leiningen plugin for Environ"
  :url "https://github.com/jsavyasachi/environ"
  :scm {:dir ".."}
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :eval-in-leiningen true
  ;; provided (non-transitive) so cljdoc can resolve leiningen.core.* for API docs
  :profiles {:provided {:dependencies [[leiningen-core "2.13.0"]]}})
