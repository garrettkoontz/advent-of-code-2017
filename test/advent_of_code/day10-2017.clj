(ns advent-of-code.test.day10-2017
  (:require [advent-of-code.day10-2017 :as day10])
  (:use [clojure.test])
  (:import [advent_of_code.day10_2017 KnotState]))

(def in-list (into [] (range 5)))
(def start-pos 0)
(def start-skip-size 0)

(def lengths '(3 4 1 5))
(def start-state (KnotState. in-list start-pos start-skip-size))

(deftest make-move-test
  (is (= (KnotState. [2 1 0 3 4] 3 1) (day10/make-move start-state))))
