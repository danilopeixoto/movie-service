(ns api.controllers
  (:require [datomic.api :as datomic]
            [api.utils :as utils]
            [api.database :as database]))


; Note: query :keys argument requires
; com.datomic/datomic-free >= 0.9.5930 (not available).

(defn get-movie
  [id]
  (let [results (datomic/q '[:find ?id ?title ?release-year
            :in $ ?id
            :where [?e :movie/id ?id]
                   [?e :movie/title ?title]
                   [?e :movie/release-year ?release-year]]
          (-> (database/get-connection) datomic/db)
          id)]
        (first (utils/to-map [:id :title :release-year] results))))

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
        (utils/to-map [:id :title :release-year] results)))

(defn list-movies
  []
  (let [results (datomic/q '[:find ?id ?title ?release-year
            :where [?e :movie/id ?id]
                   [?e :movie/title ?title]
                   [?e :movie/release-year ?release-year]]
          (-> (database/get-connection) datomic/db))]
        (utils/to-map [:id :title :release-year] results)))

; TODO: implement update and delete movie.

(defn update-movie
  [id movie-request]
  {:id "bf9b863c-7c28-11eb-9439-0242ac130000" :title "Her" :release-year 2013})

(defn delete-movie
  [id]
  {:id "bf9b863c-7c28-11eb-9439-0242ac130001" :title "Oblivion" :release-year 2013})
