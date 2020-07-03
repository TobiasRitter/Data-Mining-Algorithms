(load "gradientDescent")

(defn h [a, b, x] (+ a (* b x)))

(defn f1 [data, a, b, i] (- (h a b (get (get data (- i 1)) 0)) (get (get data (- i 1)) 1)))

(defn f2 [data, a, b, i] (* (f1 data a b i) (get (get data (- i 1)) 0)))

(defn sum [data, f, a, b, i] (if (= i 1) (f data a b i) (+ (f data a b i) (sum data f a b (- i 1)))))

(defn mean [data, f, a, b, length] (/ (sum data f a b length) length))

(defn cost_gradient [data, a, b] [(mean data f1 a b (count data)) (mean data f2 a b (count data))])

(defn linear_regression [data, learning_rate, starting_pos, cycles] (gradient_descent learning_rate (fn [params] (cost_gradient data (get params 0) (get params 1))) starting_pos cycles))