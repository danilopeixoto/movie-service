(ns api.core
  (:require [environ.core :as env]
            [ring.adapter.jetty :refer [run-jetty]]
            [api.database :as database]
            [api.routes :as routes]))


(defn get-port []
  (env/env :api-port))

(defn -main
  [& args]
  (-> (database/get-connection) database/create-models)
  (run-jetty routes/app {:port (get-port)}))
