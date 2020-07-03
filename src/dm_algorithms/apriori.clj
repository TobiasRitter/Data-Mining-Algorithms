(defn add_new_primaries [primaries, existing_list]
  (if(= primaries [])
    existing_list
    (if(.contains existing_list [(get primaries 0)])
      (add_new_primaries (into [] (rest primaries)) existing_list)
      (into []
            (concat [[(get primaries 0)]]
                    (add_new_primaries (into [] (rest primaries)) existing_list))))))

(defn get_primaries [rules]
  (if (= rules [])
    []
    (add_new_primaries (get rules 0) (get_primaries (into [] (rest rules))))))

(defn create_new_combinations [prev_combinations, primary]
  (if (= prev_combinations [])
    []
    (into []
          (concat [(into [] (concat primary (get prev_combinations 0)))]
                  (create_new_combinations (into [] (rest prev_combinations)) primary)))))

(defn get_combinations [primaries, combination_length]
  (if (= combination_length 1)
    primaries
    (if (= primaries [])
      []
      (into []
            (concat
             (create_new_combinations
              (get_combinations (into [] (rest primaries)) (- combination_length 1))
              (get primaries 0))
             (get_combinations (into [] (rest primaries)) combination_length))))))

(defn contains_primaries [primaries, rule]
  (if (= primaries [])
    true
    (and (.contains rule (get primaries 0))
         (contains_primaries (into [] (rest primaries)) rule))))

(defn get_item_count [rules, item]
  (if (= rules [])
    0
    (if (contains_primaries item (get rules 0))
      (+ (get_item_count (into [] (rest rules)) item) 1)
      (get_item_count (into [] (rest rules)) item))))

(defn get_count [items, rules]
  (if (= items [])
    []
    (into []
          (concat [[(get items 0) (get_item_count rules (get items 0))]]
                  (get_count (into [] (rest items)) rules)))))

(defn items_above [pairs, threshold]
  (if (= pairs [])
    []
    (if (>= (get (get pairs 0) 1) threshold)
      (into []
            (concat (items_above (into [] (rest pairs)) threshold) [(get (get pairs 0) 0)]))
      (items_above (into [] (rest pairs)) threshold))))

(defn apriori_step [rules, threshold, cycles]
  (if (= cycles 0)
    []
    (if (>
         (count
          (items_above (get_count (get_combinations (get_primaries rules) cycles) rules) threshold))
         0)
      (into []
            (concat (apriori_step rules threshold (- cycles 1))
                    [(items_above (get_count (get_combinations (get_primaries rules) cycles) rules) threshold)]))
      (apriori_step rules threshold (- cycles 1)))))

(defn apriori [rules, threshold]
  (apriori_step rules threshold (count (get_primaries rules))))