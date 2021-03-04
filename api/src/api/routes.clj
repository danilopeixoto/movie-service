(ns api.routes
  (:require [environ.core :as env]
            [compojure.api.sweet :refer :all]
            [compojure.api.exception :as exception]
            [humanize.schema :as humanize]
            [ring.util.http-response :as response]
            [schema.core :as schema]
            [api.schemas :as schemas]
            [api.controllers :as controllers]))


(defn get-version []
  (env/env :api-version))

(defn get-path-version []
  (str "v" (-> (get-version) (.split "\\.") first)))

(defn humanize-handler [f]
  (fn [^Exception e data request]
    (f (dissoc (#'humanize/explain
          (ex-data e)
          (fn [x]
            (clojure.core.match/match
              x
              ['not ['ReleaseYear xx]]
              (str xx " is not a valid release year.")
              :else x))) :type))))

(def app
  (api
    {:exceptions {:handlers
      {::exception/request-validation (humanize-handler response/bad-request)}}
    :swagger
      {:ui (format "/%s/docs" (get-path-version))
       :spec (format "/%s/docs/swagger.json" (get-path-version))
       :data {:info {:title "Movie API"
                     :description "A movie web API."
                     :version (get-version)}}}}

    (context (format "/%s/api" (get-path-version)) []
      (POST "/" []
        :return schemas/Movie
        :body [movie-request schemas/MovieRequest]
        :summary "Add new movie."
        (response/ok (controllers/add-movie movie-request)))

      (GET "/:id" []
        :return schemas/Movie
        :path-params [id :- schema/Uuid]
        :summary "Get movie by ID."
        (if-let [movie (controllers/get-movie id)]
          (response/ok movie)
          (response/not-found {:message "Invalid resource ID."})))

      (GET "/" []
        :return [schemas/Movie]
        :query-params [{title :- schema/Str nil}]
        :summary "List or search movies by name."
        (response/ok (if title
                         (controllers/search-movie title)
                         (controllers/list-movies))))

      (PUT "/:id" []
        :return schemas/Movie
        :path-params [id :- schema/Uuid]
        :body [movie-request schemas/MovieRequest]
        :summary "Update movie by ID."
        (if-let [movie (controllers/update-movie id movie-request)]
          (response/ok movie)
          (response/not-found {:message "Invalid resource ID."}))))))
