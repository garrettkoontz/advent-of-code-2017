(ns advent-of-code.day4-2017
  (:require [clojure.string :as str])
  (:gen-class))

(def input-file "resources/day4-2017.txt")

(def input-text (map #(str/split % #" ") (str/split (slurp input-file) #"\n")))

(defn has-dupes
  [vec]
  (not (== (count vec) (count (into #{} vec)))))

(defn has-anagrams
  [vec]
  (not (== (count vec) (count (into #{} (map #(into #{} %) vec))))))

(defn validate
  [f input]
  (reduce + (map #(if (f %) 0 1) input)))

(def part-1 (validate has-dupes input-text))

(def part-2 (validate has-anagrams input-text))

