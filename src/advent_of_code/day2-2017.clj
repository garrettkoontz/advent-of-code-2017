(ns advent-of-code.day2-2017
  (:require [clojure.string :as str])
  (:gen-class))

(def input-file "resources/day2-2017.txt")

(def input-ints
  (into []  (map 
             #(into [] (map (fn [a] (Integer/parseInt a)) (str/split % #"\t"))) 
             (str/split (slurp input-file) #"\n"))))

(defn max-min
  [vec]
  [(apply max vec) (apply min vec)])

(defn divides?
  [d n]
  (and
   (== 0 (mod n d))
   (not (== n d))))

(defn filter-divides
  [vec n]
  (filter #(divides? n %) vec))

(defn does-filter?
  [vec]
  (into [] (map #(filter-divides vec %) vec)))

(defn divide-by-does-filter
  [vec]
  (into [] (first (filter 
                   #(first %) 
                   (map 
                    (fn [lst d] 
                      [(first lst) d]) 
                    (does-filter? vec) 
                    vec)
                   )))
  )

(defn div-d-f
  [vec]
  (map #(let [[n d] (divide-by-does-filter %)]
          (/ n d) ) vec))

(defn sub-max-min
  [vec]
  (map #(let [[mx mn] (max-min %)]
          (- mx mn)) vec))

(defn calc-checksum
  [vec f]
  (apply + (f vec)))

(def part-1 (calc-checksum input-ints sub-max-min))

(def part-2 (calc-checksum input-ints div-d-f))
