(ns api.controllers
  (:require [datomic.api :as datomic]
            [api.utils :as utils]
            [api.database :as database]))


; Note: :keys query argument requires
; com.datomic/datomic-free >= 0.9.5930 (not available).

(defn get-movie-entity
  [id]
  (let [results (datomic/q '[:find ?e ?id ?title ?release-year
            :in $ ?id
            :where [?e :movie/id ?id]
                   [?e :movie/title ?title]
                   [?e :movie/release-year ?release-year]]
          (-> (database/get-connection) datomic/db)
          id)]
        (first (utils/to-map results :e :id :title :release-year))))

(defn get-movie
  [id]
  (dissoc (get-movie-entity id) :e))

(defn add-movie
  [movie-request]
  (let [id (:v (second (:tx-data @(datomic/transact
          (database/get-connection)
          [{:movie/id (datomic/squuid)
            :movie/title (movie-request :title)
            :movie/release-year (movie-request :release-year)}]))))]
        (get-movie id)))

(defn search-movie
  [title]
  (let [results (datomic/q '[:find ?id ?title ?release-year
            :in $ ?title
            :where [?e :movie/id ?id]
                   [?e :movie/title ?title]
                   [?e :movie/release-year ?release-year]]
          (-> (database/get-connection) datomic/db)
          title)]
        (utils/to-map results :id :title :release-year)))

(defn list-movies
  []
  (let [results (datomic/q '[:find ?id ?title ?release-year
            :where [?e :movie/id ?id]
                   [?e :movie/title ?title]
                   [?e :movie/release-year ?release-year]]
          (-> (database/get-connection) datomic/db))]
        (utils/to-map results :id :title :release-year)))

(defn update-movie
  [id movie-request]
  (if-let [movie-entity (get-movie-entity id)]
          (do @(datomic/transact
                (database/get-connection)
                [{:db/id (movie-entity :e)
                  :movie/title (movie-request :title)
                  :movie/release-year (movie-request :release-year)}])
              (get-movie id))
          nil))

(defn delete-movie
  [id]
  (if-let [movie-entity (get-movie-entity id)]
          (do @(datomic/transact
                (database/get-connection)
                [[:db/retractEntity (movie-entity :e)]])
              (dissoc movie-entity :e))
          nil))
