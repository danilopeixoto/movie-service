(ns api.controllers
  (:require [datomic.api :as datomic]))


(def hostname (System/getenv "DATABASE_HOSTNAME"))
(def port (System/getenv "DATABASE_PORT"))
(def password (slurp (System/getenv "DATOMIC_PASSWORD_FILE")))

(def uri (format "datomic:free://%s:%s/database?password=%s"
                 hostname port password))

(def connection (datomic/connect uri))

(defn add-movie
  [movie]
  ())

(defn get-movie
  [id]
  ())

(defn list-movies
  [title]
  ())

(defn update-movie
  [id movie]
  ())

(defn delete-movie
  [id]
  ())
