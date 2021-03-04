(ns api.schemas
  (:require [schema.core :as schema]))


(schema/defschema ReleaseYear
  (schema/constrained schema/Int #(>= % 0) 'ReleaseYear))

(schema/defschema Movie {:id schema/Uuid
                         :title schema/Str
                         :release-year ReleaseYear})

(schema/defschema MovieRequest {:title schema/Str
                                :release-year ReleaseYear})
