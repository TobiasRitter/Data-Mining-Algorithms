(use 'clojure.math.numeric-tower)

(defn distance [vector_a, vector_b] (if (= vector_a []) 0 (sqrt (+ (expt (- (first vector_a) (first vector_b)) 2) (expt (distance (rest vector_a) (rest vector_b)) 2)))))

(defn generate_starting_centroids [amount, vectors] (if (= amount 0) [] (conj (generate_starting_centroids (- amount 1) vectors) (get vectors (rand-int (count vectors))))))

(defn add_vectors [vector_a, vector_b] (if(= (count vector_a) 1) (vector (+ (get vector_a 0) (get vector_b 0))) (into [] (concat (vector (+ (get vector_a 0) (get vector_b 0))) (add_vectors (into [] (rest vector_a)) (into [] (rest vector_b)))))))

(defn get_sum_vector [cluster] (if (= cluster []) [] (if (= (count cluster) 1) (get cluster 0) (add_vectors (get cluster 0) (get_sum_vector (into [] (rest cluster)))))))

(defn get_median_vector [sum_vector, cluster_size] (if (= sum_vector []) [] (into [] (concat (vector (/ (get sum_vector 0) cluster_size)) (get_median_vector (into [] (rest sum_vector)) cluster_size)))))

(defn generate_centroids [clusters] (if (= clusters []) [] (if (= (get clusters 0) []) [] (into [] (concat [(get_median_vector (get_sum_vector (get clusters 0)) (count (get clusters 0)))] (generate_centroids (into [] (rest clusters))))))))

(defn get_closest_centroid [centroids, vector] (if (= (count centroids) 1) (get centroids 0) (if (< (distance (get centroids 0) vector) (distance (get_closest_centroid (into [] (rest centroids)) vector) vector)) (get centroids 0) (get_closest_centroid (into [] (rest centroids)) vector))))

(defn get_index_of_closest_centroid [centroids, vector] (.indexOf centroids (get_closest_centroid centroids vector)))

(defn get_empty_clusters [centroids] (if (= centroids []) [] (into [] (concat [[]] (get_empty_clusters (into [] (rest centroids)))))))

(defn add_vector_at_index [clusters, vector, index] (if (= index 0) (into [] (concat [(into [] (concat (get clusters 0) [vector]))] (into [] (rest clusters)))) (into [] (concat [(get clusters 0)] (add_vector_at_index (into [] (rest clusters)) vector (- index 1))))))

(defn generate_vector_clusters [vectors, centroids] (if (= vectors []) (get_empty_clusters centroids) (add_vector_at_index (generate_vector_clusters (into [] (rest vectors)) centroids) (get vectors 0) (get_index_of_closest_centroid centroids (get vectors 0)))))

(defn remove_empty_clusters [clusters] (if (= clusters []) [] (if (= (get clusters 0) []) (remove_empty_clusters (into [] (rest clusters))) (into [] (concat [(get clusters 0)] (remove_empty_clusters (into [] (rest clusters))))))))

(defn kmeans [vectors, amount_of_clusters, cycles] (if (= cycles 0) (generate_starting_centroids amount_of_clusters vectors) (generate_centroids (remove_empty_clusters (generate_vector_clusters vectors (kmeans vectors amount_of_clusters (- cycles 1)))))))