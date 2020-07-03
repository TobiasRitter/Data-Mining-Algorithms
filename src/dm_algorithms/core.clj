(ns dm-algorithms.core
  (:gen-class))

(load "apriori")
(load "bellNumbers")
(load "decisionTree")
(load "gradientDescent")
(load "kmeans")
(load "linearRegression")

(defn -main
  [& args]
  ; perform a linear regression where the vector [a b] returned describes a*x^0 + b*x^1 
  (println (linear_regression [[0 0] [1 1] [2 2]] 0.03 [0 0] 1000)))
