(ns advent-of-code.day1-2017
  (:require [clojure.string :as str])
  (:gen-class))

(def input-file "resources/day1-2017.txt")

(def input-string ((str/split  (slurp input-file) #"\n") 0))

(defn parse-char-to-int
  [c]
  (Character/getNumericValue c))

(def input-vec (into [] (map parse-char-to-int input-string)))

(defn rotate-vec
  [s i]
  (into [] (concat (subvec s i) (subvec s 0 i))))

(defn sum-match-values
  [inp1 inp2]
  (apply + 
         (map #(if (== %1 %2) %1 0)
              inp1 inp2)))

(def part-1 (count-how-many-match input-vec (rotate-vec input-vec 1)))

(def part-2 (count-how-many-match input-vec (rotate-vec input-vec (/ (count input-vec) 2))))
