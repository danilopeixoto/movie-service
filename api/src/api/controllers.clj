(ns api.controllers
  (:require [api.database :as database]))


(def connection (database/connect))

(database/create-models connection)

(defn add-movie
  [movie-request]
  {:id "bf9b863c-7c28-11eb-9439-0242ac130002" :title "Her" :year 2013})

(defn get-movie
  [id]
  {:id "bf9b863c-7c28-11eb-9439-0242ac130002" :title "Her" :year 2013})

(defn list-movies
  [title]
  [{:id "bf9b863c-7c28-11eb-9439-0242ac130002" :title "Her" :year 2013}])

(defn update-movie
  [id movie-request]
  {:id "bf9b863c-7c28-11eb-9439-0242ac130002" :title "Her" :year 2013})

(defn delete-movie
  [id]
  {:id "bf9b863c-7c28-11eb-9439-0242ac130002" :title "Her" :year 2013})
