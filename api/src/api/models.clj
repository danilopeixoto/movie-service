(ns api.models)


(def Movie [
  {:db/ident :movie/id
   :db/valueType :db.type/uuid
   :db/cardinality :db.cardinality/one
   :db/doc "Movie ID attribute."}

  {:db/ident :movie/title
   :db/valueType :db.type/string
   :db/cardinality :db.cardinality/one
   :db/doc "Movie title attribute."}

  {:db/ident :movie/release-year
   :db/valueType :db.type/long
   :db/cardinality :db.cardinality/one
   :db/doc "Movie release year attribute."}])

(def models (-> [Movie] flatten vec))
