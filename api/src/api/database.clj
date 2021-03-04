(ns api.database
  (:require [environ.core :as env]
            [datomic.api :as datomic]
            [api.models :as models]))


(defn get-hostname []
  (env/env :database-hostname))

(defn get-port []
  (env/env :database-port))

(defn get-password []
  (-> :datomic-password-file env/env slurp .trim))

(defn get-uri []
  (format "datomic:free://%s:%s/database?password=%s"
          (get-hostname) (get-port) (get-password)))

(defn get-connection []
  (datomic/create-database (get-uri))
  (datomic/connect (get-uri)))

(defn create-models [connection]
  (datomic/transact connection models/models))
