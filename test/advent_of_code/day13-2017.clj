(ns advent-of-code.test.day13-2017
  (:require [advent-of-code.day13-2017 :as day13]
            [clojure.string :as string])
  (:use [clojure.test]))

(def sample-input "0: 3
1: 2
4: 4
6: 4")

(def sample-string (string/split sample-input #"\n"))

(deftest parse-input-string-test
  (is (= {0 3 1 2 4 4 6 4} (day13/parse-input-string sample-string))))

(deftest position-after-test
  (is (= '(0 1 0 1 0 1) (map #(day13/position-after % 2) (range 0 6))))
  (is (= '(0 1 2 1 0 1 2 1 0 1)(map #(day13/position-after % 3) (range 0 10))))
  (is (= '(0 1 2 3 2 1 0 1 2 3 2) (map #(day13/position-after % 4) (range 0 11)))))

(deftest is-caught?-test
  (is (= '(true false false false false false true) (map #(day13/is-caught? %1 %2 0) (range 0 7) [3 2 0 0 4 0 4]))))

(deftest severity-test
  (is (= '(0 0 0 0 0 0 24) (map #(day13/severity %1 %2 %1 0)  (range 0 7) [3 2 0 0 4 0 4]))))

(deftest get-severity-of-trip-test 
  (is (= 24 (day13/get-severity-of-trip (day13/parse-input-string sample-string)))))


