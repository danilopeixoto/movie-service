(ns api.schemas
  (:require [schema.core :as schema]))


(schema/defschema Year
  (schema/constrained schema/Int #(>= % 0)))

(schema/defschema Movie {:id schema/Uuid
                         :title schema/Str
                         :year Year})

(schema/defschema MovieRequest {:title schema/Str
                                :year Year})
