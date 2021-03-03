(ns api.database
  (:require [clojure.string :as str]
            [environ.core :as env]
            [datomic.api :as datomic]
            [api.models :as models]))


(def hostname (env/env :database-hostname))
(def port (env/env :database-port))
(def password (str/trim (slurp (env/env :datomic-password-file))))

(def uri (format "datomic:free://%s:%s/database?password=%s"
                 hostname port password))

(defn connect []
  (datomic/create-database uri)
  (datomic/connect uri))

(defn create-models [connection]
  (datomic/transact connection models/Models))
