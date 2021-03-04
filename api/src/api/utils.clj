(ns api.utils)


(defn to-map [keys values]
  (vec (map #(apply assoc {} (interleave keys %))
       values)))
