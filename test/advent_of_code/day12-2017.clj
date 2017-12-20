(ns advent-of-code.test.day12-2017
  (:require [advent-of-code.day12-2017 :as day12]
            [clojure.string :as string])
  (:use [clojure.test])
  (:import [com.google.common.graph GraphBuilder Graphs]))

(def sample-input "0 <-> 2
1 <-> 1
2 <-> 0, 3, 4
3 <-> 2, 4
4 <-> 2, 3, 6
5 <-> 6
6 <-> 4, 5")

(def sample-strings (string/split sample-input #"\n"))

(def sample-graph-start (-> (GraphBuilder/undirected)
                      (.allowsSelfLoops true)
                      .build))

(def sample-graph (day12/create-communication-graph sample-graph-start sample-strings))

(deftest count-connected-nodes
  (is (= 6 (day12/count-connected-nodes sample-graph "0")))
  )

(deftest num-groups-test
  (is (= 2 (day12/num-groups sample-graph))))
