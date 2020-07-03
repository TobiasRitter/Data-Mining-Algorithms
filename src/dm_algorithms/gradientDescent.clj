(use
 'clojure.math.numeric-tower)

(defn listsub [list1, list2]
  (if (= list1 [])
    []
    (into []
          (concat [(- (get list1 0) (get list2 0))]
                  (listsub (into [] (rest list1)) (into [] (rest list2)))))))

(defn listmul [list1, factor]
  (if (= list1 [])
    []
    (into [] (concat [(* (get list1 0) factor)] (listmul (into [] (rest list1)) factor)))))

(defn gradient_descent [learning_rate, gradient_func, starting_pos, cycles]
  (if (= cycles 0)
    starting_pos
    (let [prev_pos (gradient_descent learning_rate gradient_func starting_pos (- cycles 1))]
      (listsub prev_pos (listmul (gradient_func prev_pos) learning_rate)))))