(ns advent-of-code.day13-2017
  (:require [clojure.string :as string])
  (:gen-class))

(def input-file "resources/day13-2017.txt")

(def input-string (string/split (slurp input-file) #"\n"))

(defn parse-input-string
  [strng]
  (into {} (map 
            #(let [[k v] (string/split % #": ")
                   key (Integer/parseInt k)
                   val (Integer/parseInt v)]
               [key val]) 
            strng)))

(def input-map (parse-input-string input-string))

(defn position-after
  [picos size]
  (if (= size 0) 
    -1
    (let [mod-val (- (* 2 size) 2)
               sz (- size 1)
               n (mod picos mod-val)
               back? (> n sz)]
           (if back?
             (- mod-val n)
             n)
           )))

(defn is-caught?
  [picos size delay]
  (= 0 (position-after (+ delay picos) size)))

(defn severity
  [picos size depth delay]
  (let [pos (position-after (+ delay picos) size)]
    (if (= pos 0)
      (* depth size)
      0)))

(defn get-severity-of-trip-with-delay
  [mp delay]
  (reduce + (map #(let [k (% 0) v (% 1)] (severity k v k delay)) mp))
  )

(defn get-severity-of-trip
  [mp]
  (get-severity-of-trip-with-delay mp 0))

(def part-1 (get-severity-of-trip input-map))

(def part-2 (take 10 (take-while #(= 0 (get-severity-of-trip-with-delay input-map %)) (range) )))
