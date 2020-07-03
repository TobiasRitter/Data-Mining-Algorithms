(declare sum)

(defn fac [n] (if (= n 0) 1 (* n (fac (- n 1)))))

(defn binom [n, k] (/ (fac n) (* (fac (- n k)) (fac k))))

(defn bell [n] (if (< n 2) 1 (sum (- n 1) (- n 1))))

(defn sum [n, k] (if (= k 0) (* (bell k) (binom n k)) (+ (* (bell k) (binom n k)) (sum n (- k 1)))))