(defproject net.clojars.savya/environ "1.4.2"
  :description "Library for accessing environment variables"
  :url "https://github.com/jsavyasachi/environ"
  :scm {:dir ".."}
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :global-vars {*warn-on-reflection* true}
  :profiles {:provided {:dependencies [[org.clojure/clojurescript "1.12.145"]]}
             :clojure-1-10 {:dependencies [[org.clojure/clojure "1.10.3"]]}
             :clojure-1-11 {:dependencies [[org.clojure/clojure "1.11.4"]]}
             :clojure-1-12 {:dependencies [[org.clojure/clojure "1.12.0"]]}}
  :plugins [[lein-cljsbuild "1.1.8"]
            [lein-doo "0.1.11"]]
  :aliases
  {"all" ["with-profile" "+clojure-1-10:+clojure-1-11:+clojure-1-12"]
   "ci" ["do"
         ["clean"]
         ["test"]
         ;; cljs 1.12 toolchain needs Clojure 1.10+ (and JDK 21+)
         ["with-profile" "+clojure-1-12" "doo" "node" "test-nodejs" "once"]]}
  :cljsbuild
  {:builds
   [{:id "test-nodejs"
     :compiler
     {:main environ.test.runner
      :output-dir "target/test-nodejs"
      :output-to "target/environ.test.nodejs.js"
      :target :nodejs}
     :source-paths ["src" "test"]}]})
