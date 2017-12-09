(ns advent-of-code.test.day2-2017
  (:use [clojure.test])
  (:require [advent-of-code.day2-2017 :as day2]))

(deftest max-min-test
  (is (= [10 1] (day2/max-min [9 5 4 2 1 3 8 10]))))

(deftest divides?-test
  (is (= true (day2/divides? 5 10)))
  (is (= false (day2/divides? 3 5)))
  (is (= false (day2/divides? 10 5)))
  (is (= false (day2/divides? 5 5 ))))

(deftest examples-test
  (let [input [[5 1 9 5]
               [7 5 3]
               [2 4 6 8]]]
    (day2/calc-checksum input day2/sub-max-min))
  (let [input [[5 9 2 8]
               [9 4 7 3]
               [3 8 6 5]]]
    (day2/calc-checksum input day2/div-d-f)))






