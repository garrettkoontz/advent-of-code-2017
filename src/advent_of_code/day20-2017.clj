(ns advent-of-code.day20-2017
  (:require [clojure.string :as str])
  (:gen-class))

(def input-file "resources/day20-2017.txt")

(def input-string (str/split (slurp input-file) #"\n"))

(defn parse-input
  [strng]
  (let [parts (str/split strng #">[, ]?")
        [p v a] (mapv #(str/split (str/replace % #" ?[pav]=<" "") #",") parts)
        ]
    {:p (mapv #(Integer/parseInt %) p) :v (mapv #(Integer/parseInt %) v) :a (mapv #(Integer/parseInt %) a)}
    ))

(def parsed-input (into [] (pmap #(parse-input %) input-string)))

(defn make-move
  [{p :p v :v a :a}]
  (let [newv (mapv + a v)
        newp (mapv + newv p)]
    {:p newp :v newv :a a})
  )

(defn abs
  [x]
  (if (> 0 x) (* -1 x) x))

(defn distance
  [{p :p}]
  (apply + (map abs p)))

(defn make-moves
  [move-vec cnt]
  (loop [mv move-vec
         cntr cnt]
    (if (>= 0 cntr)
      mv
      (recur (make-move mv) (dec cntr)))))

(def long-time 1000)

(def indexed-input (into {} (map-indexed (fn [idx itm] [idx itm]) parsed-input)))

(def after-long-time (into {} (pmap (fn [[k v]] [k (make-moves v long-time)]) indexed-input )))

(def distances (map (fn [[k v]] [k (distance v)]) after-long-time))

(comment def part-1 
  (reduce (fn     
            [[i d] [k v]]
            (let [dp (distance v)]
              (if (< dp d) 
                [k dp] 
                [i d]))) 
          [-1 Integer/MAX_VALUE] 
          after-long-time))

(defn index-on-position
  [mp]
  (group-by #(:p %) mp))

(defn resolve-collisions
  [mp]
  (let [gbmp (index-on-position mp)
        no-cols (filter #(not (< 1 (count (% 1)))) gbmp)
        new-mp (flatten (map #(% 1) no-cols))]
    (into [] new-mp)))

(defn make-moves-with-collisions
  [inp-map limit]
  (loop [cnt limit
         mps inp-map]
    (if (>= 0 cnt)
      mps
      (let [mp' (map #(make-move %) mps)
            mp'' (resolve-collisions mp')]
        (recur (dec cnt) mp'')))))

(def part-2 (make-moves-with-collisions parsed-input 1000))
