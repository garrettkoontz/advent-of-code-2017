(ns advent-of-code.test.day10-2017
  (:require [advent-of-code.day10-2017 :as day10])
  (:use [clojure.test])
  (:import [advent_of_code.day10_2017 KnotState]))

(def in-list (into [] (range 5)))
(def start-pos 0)
(def start-skip-size 0)

(def lengths [3 4 1 5])
(def start-state (KnotState. in-list start-pos start-skip-size))

(deftest make-move-test
  (is (= (KnotState. [2 1 0 3 4] 3 1) (day10/make-move start-state (lengths 0))))
  (is (= (KnotState. [4 3 0 1 2] 3 2) (day10/make-move (KnotState. [2 1 0 3 4] 3 1) (lengths 1))))
  (is (= (KnotState. [4 3 0 1 2] 1 3) (day10/make-move (KnotState. [ 4 3 0 1 2] 3 2) (lengths 2))))
  (is (= (KnotState. [3 4 2 1 0] 4 4) (day10/make-move (KnotState. [4 3 0 1 2] 1 3) (lengths 3)))))

(deftest dense-hash-test
  (is (= '(3 7) (day10/dense-hash [1 2 3 4] 2)))
  )

(deftest tohex-test
  (is (= "4007ff" (day10/tohex [64 7 255]))))

(deftest knot-hash-test
  (is (= "a2582a3a0e66e6e86e3812dcb672a272" (day10/knot-hash "")))
  (is (= "33efeb34ea91902bb2f59c9920caa6cd" (day10/knot-hash "AoC 2017")))
  (is (= "3efbe78a8d82f29979031a4aa0b16a9d" (day10/knot-hash "1,2,3")))
  (is (= "63960835bcdc130f0b66d7ff4f6a5a8e" (day10/knot-hash "1,2,4"))))

