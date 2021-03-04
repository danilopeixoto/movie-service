(ns api.core
  (:require [environ.core :as env]
            [ring.adapter.jetty :refer [run-jetty]]
            [api.database :as database]
            [api.routes :as routes])
  (:gen-class))


(defn get-port []
  (-> :api-port env/env Integer/valueOf))

(defn -main
  [& args]
  (-> (database/get-connection) database/create-models)
  (run-jetty routes/app {:port (get-port)}))
