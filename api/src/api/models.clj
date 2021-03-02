(ns api.models
  (:require [schema.core :as schema]))


(schema/defschema MovieRequest {:title String
                                :year Long})

(schema/defschema Movie {:id Long
                         :title String
                         :year Long})
