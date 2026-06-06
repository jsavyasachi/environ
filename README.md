# Environ

Environ is a Clojure library for managing environment settings from a
number of different sources. It works well for applications following
the [12 Factor App](http://12factor.net/) pattern.

Currently, Environ supports four sources, resolved in the following
order:

1. A `.lein-env` file in the project directory
2. A `.boot-env` file on the classpath
3. Environment variables
4. Java system properties

The `.lein-env` file is set by the lein-environ plugin and should not
be edited manually. It is populated with the content of the `:env` key
in the Leiningen project map. The `.boot-env` file is read from the
classpath if present (e.g. when produced by an external Boot task).


## Installation

Include the following dependency in your `project.clj` file:

```clojure
:dependencies [[environ "1.2.0"]]
```

If you want to be able to draw settings from the Leiningen project
map, you'll also need the following plugin:

```clojure
:plugins [[lein-environ "1.2.0"]]
```

> **Note:** the Boot plugin (`boot-environ`) is unmaintained and is not
> republished in this fork. The core library still reads a `.boot-env`
> file from the classpath, so an externally produced one keeps working.


## Usage

Let's say you have an application that requires a database connection.
Often you'll need three different databases, one for development, one
for testing, and one for production.

Lets pull the database connection details from the key `:database-url`
on the `environ.core/env` map.

```clojure
(require '[environ.core :refer [env]])

(def database-url
  (env :database-url))
```

The value of this key can be set in several different ways. The most
common way during development is to use a local `profiles.clj` file in
your project directory. This file contains a map with profiles that will
be merged with the profiles specified in the standard `project.clj`, but
can be kept out of version control and reserved for local development options.

```clojure
{:dev  {:env {:database-url "jdbc:postgresql://localhost/dev"}}
 :test {:env {:database-url "jdbc:postgresql://localhost/test"}}}
```

In this case we add a database URL for the dev and test environments.
This means that if you run `lein repl`, the dev database will be used,
and if you run `lein test`, the test database will be used.

So that profiles you define in `profiles.clj` are merged into, rather than
replacing profiles defined in `project.clj`, a composite profile can be
created in `project.clj`:

```clojure
:profiles {:dev [:project/dev :profiles/dev]
           :test [:project/test :profiles/test]
           ;; only edit :profiles/* in profiles.clj
           :profiles/dev  {}
           :profiles/test {}
           :project/dev {:source-paths ["src" "tool-src"]
                         :dependencies [[midje "1.6.3"]]
                         :plugins [[lein-auto "0.1.3"]]}
           :project/test {}}
```

And then use the `:profiles/dev` key in your `profiles.clj`.

Keywords with a `project` namespace are looked up in the project
map. For example:

```clojure
{:env {:app-version :project/version}}
```

This looks up the `:version` key in the Leiningen project map. You can
view the full project map by using [lein-pprint][].

When you deploy to a production environment, you can make use of
environment variables, like so:

```bash
DATABASE_URL=jdbc:postgresql://localhost/prod java -jar standalone.jar
```

Or use Java system properties:

```bash
java -Ddatabase.url=jdbc:postgresql://localhost/prod -jar standalone.jar
```

Note that Environ automatically lowercases keys, and replaces the
characters "_" and "." with "-". The environment variable
`DATABASE_URL` and the system property `database.url` are therefore
both converted to the same keyword `:database-url`.

[lein-pprint]: https://github.com/technomancy/leiningen/tree/master/lein-pprint

*Important* -- environ will not pick up configuration settings from the 
`project.clj` when called from a compiled uberjar. So for any compiled 
code you produce with `lein uberjar`, you will want to set your 
configuration values via shell environment and/or system properties.

## License

Copyright © 2020 James Reeves

Distributed under the Eclipse Public License, the same as Clojure.
