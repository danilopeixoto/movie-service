(ns api.routes
  (:require [clojure.string :as str]
            [environ.core :as env]
            [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as schema]
            [api.schemas :as schemas]
            [api.controllers :as controllers]))


(def version (env/env :api-version))

(def path-version
  (str "v" (first (str/split version #"\."))))

(def app
  (api
    {:swagger
      {:ui (format "/%s/docs" path-version)
       :spec (format "/%s/docs/swagger.json" path-version)
       :data {:info {:title "Movie API"
                     :description "A movie web API."
                     :version version}}}}

    (context (format "/%s/api" path-version) []
      (POST "/" []
        :return schemas/Movie
        :body [movie-request schemas/MovieRequest]
        :summary "Add new movie."
        (ok (controllers/add-movie movie-request)))

      (GET "/:id" []
        :return schemas/Movie
        :path-params [id :- schema/Uuid]
        :summary "Get movie by ID."
        (ok (controllers/get-movie id)))

      (GET "/" []
        :return [schemas/Movie]
        :query-params [{title :- schema/Str nil}]
        :summary "List and search movies by name."
        (ok (controllers/list-movies title)))

      (PUT "/:id" []
        :return schemas/Movie
        :path-params [id :- schema/Uuid]
        :body [movie-request schemas/MovieRequest]
        :summary "Update movie by ID."
        (ok (controllers/update-movie id movie-request)))

      (DELETE "/:id" []
        :return schemas/Movie
        :path-params [id :- schema/Uuid]
        :summary "Delete movie by ID."
        (ok (controllers/delete-movie id))))))
