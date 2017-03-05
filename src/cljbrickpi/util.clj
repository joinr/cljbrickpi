;;useful utils for all the things
(ns cljbrickpi.util)
(defn get! [m k]
  (if-let [res (get m k)]
    res
    (throw (Exception. (str [:unknown-key k :in m])))))
