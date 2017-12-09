(ns advent-of-code.test.day1-2017
  (:use [clojure.test])
  (:require [advent-of-code.day1-2017 :as day1]))

(deftest rotate-vec-test
  (let [v [1 2 3]]
    (is (= [2 3 1] (day1/rotate-vec v 1)))))

(deftest sum-match-values-test-easy
  (let [v1 [1 2 3] v2 [1 2 4]]
    (is (= 3 (day1/sum-match-values v1 v2))))
  (let [v1 [] v2 []]
    (is (= 0 (day1/sum-match-values v1 v2))))
  (let [v1 [1 2 3 4] v2 [1 3]]
    (is (= 1 (day1/sum-match-values v2 v2)))))

(deftest sum-match-values-test-examples
  (let [a [1 1 2 2]]
    (is (= 3 (day1/sum-match-values a (day1/rotate-vec a 1)))))
  (let [a [1 1 1 1]]
    (is (= 4 (day1/sum-match-values a (day1/rotate-vec a 1)))))
  (let [a [1 2 3 4]]
    (is (= 0 (day1/sum-match-values a (day1/rotate-vec a 1)))))
  (let [a [9 1 2 1 2 1 2 9]]
    (is (= 9 (day1/sum-match-values a (day1/rotate-vec a 1))))))


