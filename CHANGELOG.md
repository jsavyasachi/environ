## 1.3.0 (2026-06-06)

Maintenance fork, published to Clojars as `net.clojars.savya/environ` and
`net.clojars.savya/lein-environ`.

* Verified on JDK 8/11/17/21 and Clojure 1.10/1.11/1.12 via GitHub Actions CI
* Drop the Nashorn ClojureScript test target (Nashorn was removed in JDK 15);
  ClojureScript is now tested on Node
* Bump ClojureScript 1.10.439 -> 1.11.132, lein-cljsbuild 1.1.7 -> 1.1.8,
  lein-doo 0.1.10 -> 0.1.11
* Enable `*warn-on-reflection*`; core is reflection-clean
* Remove the unmaintained boot-environ module (Boot toolchain is dead); the core
  still reads a `.boot-env` file from the classpath
* Add an EPL-1.0 `LICENSE` file

## 1.2.0 (2020-05-05)

* Add support for ClojureScript on node.js (#87)

## 1.1.0 (2016-08-04)

* Replace `:project/foo` keywords with the value of `:foo` key in project map

## 1.0.3 (2016-05-06)

* Fix boot-environ to return updated fileset
* Print key when santizing value for easier debugging
* Set `*print-xxx*` vars to false when writing env-file

## 1.0.2 (2016-01-27)

* Cast non-string values to strings and output warning
* Fix boot-environ to work with pods

## 1.0.1 (2014-08-16)

* Use safer `clojure.edn/read-string` function to read environment files

## 1.0.0 (2014-08-16)

* First stable release
