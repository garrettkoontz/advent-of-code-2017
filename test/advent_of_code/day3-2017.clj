(ns advent-of-code.test.day3-2017
  (:use [clojure.test])
  (:require [advent-of-code.day3-2017 :as day3]))

(deftest last-odd-square-before-test
  (is (= [3 9] (day3/last-odd-square-before 10)))
  (is (= [3 9] (day3/last-odd-square-before 25)))
  (is (= [5 25] (day3/last-odd-square-before 26)))
  (is (= [5 25] (day3/last-odd-square-before 49)))
  (is (= [7 49] (day3/last-odd-square-before 50))))

(deftest add-vec-test
  (is (= [2 2] (day3/add-vec [1 1] [1 1])))
  (is (= [3 3] (day3/add-vec [1 1] [1 1] [1 1]))))

(deftest scale-vec-test
  (is (= [2 4] (day3/scale-vec 2 [1 2]))))

(def easy-map {:R [1 0] :U [0 1]})

(deftest scale-map-vec-add-test
  (is (= [3 8] (day3/scale-map-vec-add easy-map {:R 3 :U 8})))
  (is (= [-1 -6] (day3/scale-map-vec-add easy-map {:R -1 :U -6}))))

(deftest make-move-test
  (is (= [0 0] (day3/make-move-vec 0 0)))
  (is (= [1 0] (day3/make-move-vec 0 1)))
  (is (= [-3 2] (day3/make-move-vec 2 9)))
  (is (= [-2 -1] (day3/make-move-vec 2 13))))

(deftest find-x-y-test
  (is (= [0 0] (day3/find-x-y 1)))
  (is (= [1 -1] (day3/find-x-y 9)))
  (is (= [0 3] (day3/find-x-y 34))))

(deftest get-neighbors-test
  (is (= #{[0 1] [1 0] [1 1] [-1 0] [0 -1] [-1 1] [1 -1] [-1 -1]} (into #{} (day3/get-neighbors [0 0]))))
  (is (= #{[-3 8] [-2 7] [-2 8] [-4 7] [-3 6] [-4 8] [-2 6] [-4 6]} (into #{} (day3/get-neighbors [-3 7])))))

(comment deftest make-next-val-test
  (is (= )))

