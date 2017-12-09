(ns advent-of-code.day6-2017
  (:require [clojure.string :as string])
  (:gen-class))

(def input-file "resources/day6-2017.txt")

(def input-blocks (into [] (map #(Integer/parseInt %) (string/split (slurp input-file) #"[\t\n]"))))

(defn mod-block
  [blocks idx]
  (assoc blocks idx (inc (blocks idx))))

(defn redistribute
  [blocks]
  (let [total-blocks (count blocks)
        max-val (apply max blocks)
        max-idx (.indexOf blocks max-val)
        start-blocks (assoc blocks max-idx 0)]
    (reduce 
     #(mod-block %1 
                 (mod %2 total-blocks)) 
     start-blocks
         (range (+ 1 max-idx) (+ 1 max-val max-idx)))))

(defn redis-until-same
  [return-f]
  (loop [i input-blocks s [input-blocks]]
    (let [n (redistribute i)] 
      (if (.contains s n)
        (return-f s) 
        (recur n (conj s n))))))

(def part-1 (redis-until-same (fn [s] (count s))))
(def part-2 (redis-until-same (fn [s] (- (count s) (.indexOf s (redistribute (last s)))))))

