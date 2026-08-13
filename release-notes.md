
* lein-environ: declare `leiningen-core` as a provided dependency so cljdoc can
  resolve the `leiningen.core.*` requires and build the plugin's API docs.
  Provided scope is non-transitive, so dependent projects are unaffected. No
  code changes; `environ` is released alongside to keep the versions matched.

