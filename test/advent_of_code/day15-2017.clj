(ns advent-of-code.test.day15-2017
  (:require [advent-of-code.day15-2017 :as day15])
  (:use [clojure.test]))

(def test-starting-vals
  {:A 65 :B 8921})

(deftest get-next-val-test
  (is (= 1092455 (day15/get-next-val (:A day15/factors) day15/divisor 65)))
  (is (= 430625591 (day15/get-next-val (:B day15/factors) day15/divisor 8921))))

(deftest to-binary-string
  (is (= "00000000000100001010101101100111" (day15/to-binary-string 1092455)))
  (is (= "00011001101010101101001100110111" (day15/to-binary-string 430625591))))

(deftest get-lowest-n-bits-test
  (is (= "43210" (day15/get-lowest-n-bits 5 "6543210")))
  (is (= "6543210" (day15/get-lowest-n-bits 15 "6543210"))))


