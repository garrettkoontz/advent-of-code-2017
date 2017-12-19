(ns advent-of-code.test.day11-2017
  (:require [advent-of-code.day11-2017 :as day11])
  (:use [clojure.test]))

(deftest add-vec-test
  (is (= [3 3 3] (day11/add-vec [1 1 1] [2 2 2]))))

(def make-moves (partial reduce #(day11/make-move %1 %2) [0 0]))

(deftest make-move-test
  (is (= [-1 0] (make-moves [:nw])))
  (is (= [3 -7] (make-moves [:nw :n :n :ne :ne :ne :n :ne])))
  (is (= [2 0] (make-moves [:ne :ne :s :s])))
  (is (= [-1 3] (make-moves [:se :sw :se :sw :sw]))))

(deftest num-steps-test
  (is (= 3 (day11/num-steps (make-moves [:ne :ne :ne]))))
  (is (= 0 (day11/num-steps (make-moves [:ne :ne :sw :sw]))))
  (is (= 2 (day11/num-steps (make-moves [:ne :ne :s :s])))))

