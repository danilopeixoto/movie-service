(defproject api (System/getenv "API_VERSION")
  :description "A movie web API."
  :url "https://github.com/danilopeixoto/movie-service"
  :license {:name "BSD-3-Clause"
            :url "https://github.com/danilopeixoto/movie-service/LICENSE"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [environ "1.2.0"]
                 [com.datomic/datomic-free "0.9.5697"]
                 [metosin/compojure-api "1.1.10"]]
  :ring {:handler api.routes/app
         :port ~(Integer/valueOf (System/getenv "API_PORT"))}
  :uberjar-name "api.jar"
  :profiles {:dev {:dependencies [[javax.servlet/javax.servlet-api "3.1.0"]]
                   :plugins      [[lein-ring "0.12.0"]]}})
