(ns api.routes
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [api.models :as models]
            [api.controllers :as controllers]))


(def app
  (api
    {:swagger
      {:ui "/v1/docs"
       :spec "/v1/docs/swagger.json"
       :data {:info {:title "Movie API"
                     :description "A movie web API."
                     :version "1.0.0"}}}}

  (context "/v1/api" []
    (POST "/" []
      :return models/Movie
      :body [movie models/MovieRequest]
      :summary "Add new movie."
      (ok (controllers/add-movie! movie)))

    (GET "/:id" []
      :return models/Movie
      :path-params [id :- Long]
      :summary "Get movie by ID."
      (ok (controllers/get-movie! id)))

    (GET "/" []
      :return [models/Movie]
      :query-params [title :- String]
      :summary "Search movies by name."
      (ok (controllers/list-movies! title)))

    (PUT "/:id" []
      :return models/Movie
      :path-params [id :- Long]
      :body [movie models/MovieRequest]
      :summary "Update movie by ID."
      (ok (controllers/update-movie! id movie)))

    (DELETE "/:id" []
      :return models/Movie
      :path-params [id :- Long]
      :summary "Delete movie by ID."
      (ok (controllers/delete-movie! id))))))
