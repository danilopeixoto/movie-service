(defproject api "1.0.0"
  :description "A movie web API."
  :url "https://github.com/danilopeixoto/movie-service"
  :license {:name "BSD-3-Clause"
            :url "https://github.com/danilopeixoto/movie-service/LICENSE"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [environ "1.2.0"]
                 [com.datomic/datomic-free "0.9.5697"]
                 [siili/humanize "0.1.0"]
                 [ring/ring-jetty-adapter "1.9.0"]
                 [metosin/compojure-api "1.1.10"]]
  :main ^:skip-aot api.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
