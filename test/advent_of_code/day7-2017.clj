(ns advent-of-code.test.day7-2017
  (:use [clojure.test])
  (:require [advent-of-code.day7-2017 :as day7])
  (:import [advent_of_code.day7_2017 Program]))

(def sample-string "pbga (66)
  xhth (57)
  ebii (61)
  havc (66)
  ktlj (57)
  fwft (72) -> ktlj, cntj, xhth
  qoyq (66)
  padx (45) -> pbga, havc, qoyq
  tknk (41) -> ugml, padx, fwft
  jptl (61)
  ugml (68) -> gyxo, ebii, jptl
  gyxo (61)
cntj (57)")

(def sample-input-string (day7/parse-input-string sample-string))

(deftest parse-input-string-test
  (is (= ["pbga" "66"] (first sample-input-string)))
  (is (= ["tknk" "41" "ugml" "padx" "fwft"] (nth sample-input-string 8))))

(def sample-input-objects (day7/input-objects sample-input-string))

(deftest input-objects-test
  (is (= (day7/Program. :pbga 66 '()) (first sample-input-objects)))
  (is (= (day7/Program. :padx 45 '(:pbga :havc :qoyq)) (nth sample-input-objects 7))))

(def sample-input-map (day7/map-input-objects sample-input-objects))

(deftest map-input-objects-test
  (is (= (day7/Program. :padx 45 '(:pbga :havc :qoyq)) (:padx sample-input-map)))
  (is (= (day7/Program. :tknk 41 '(:ugml :padx :fwft)) (:tknk sample-input-map))))

(deftest bottom-prog-test
  (is (= #{:tknk} (day7/bottom-prog sample-input-objects))))

(deftest find-weight-at-test
  (is (= 61 (day7/find-weight-at sample-input-map :ebii)))
  (is (= 243 (day7/find-weight-at sample-input-map :fwft)))
  (is (= (+ 737 41) (day7/find-weight-at sample-input-map :tknk))))

(deftest find-unbalanced-node-test
  (is (= :ugml (day7/find-unbalanced-node sample-input-map))))

(deftest get-child-weights
  (is (=)))
