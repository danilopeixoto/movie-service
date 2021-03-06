(ns api.utils)


(defn to-map
  [values & keys]
  (vec (map #(apply assoc {} (interleave keys %))
            values)))
