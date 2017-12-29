(ns advent-of-code.test.day14-2017
  (:require [advent-of-code.day14-2017 :as day14])
  (:import [com.google.common.graph Graph GraphBuilder Graphs])
  (:use [clojure.test]))

(deftest hex-to-bits-test
  (is (= ["0000" "0001" "0010" "0011" "0100" "0101" "0110" "0111" "1000" "1001" "1010" "1011" "1100" "1101" "1110" "1111"] (into [] (map #(day14/hex-to-bits %) (concat (map str (range 0 10)) '("a" "b" "c" "d" "e" "f"))))))
  (is (= "10100000110000100000000101110000" (apply str (map #(day14/hex-to-bits (str %)) "a0c20170")))))

(deftest get-binary-string-from-hex-string
  (is (= "10100000110000100000000101110000" (day14/get-binary-string-from-hex-string "a0c20170"))))

(deftest count-used-test
  (is (= 8108 (day14/count-used "flqrgnkx"))))

(deftest get-val-test
  (is (= 10 (day14/get-val [[0 1 2] [3 4 5] [6 7 8] [9 10 11]] [1 3]))))

(deftest get-neighbors-test
  (is (= #{[0 1] [1 0]} (into #{} (day14/get-neighbors [0 0]))))
  (is (= #{[0 1] [1 0] [1 2] [2 1]} (into #{} (day14/get-neighbors [1 1])))))

(deftest convert-strings-to-vecs-test
  (is (= [[1 0 1] [0 1 0]] (day14/convert-strings-to-vecs ["101" "010"]))))

(deftest get-valid-neighbors-test
  (is (= '([0 1]) (day14/get-valid-neighbors [[1 0 1] [1 0 0] [0 0 0]] [0 0]))))

(deftest get-connected-pairs-test
  (is (= #{[[0 0] [0 1]] [[0 0] [0 0]] [[0 1] [0 0]] [[0 1] [0 1]]} (into #{} (day14/get-connected-pairs (day14/convert-strings-to-vecs ["101" "100" "000"]) 2 2)))))

(def connection-graph 
  (.build (.allowsSelfLoops (GraphBuilder/undirected) true)))

(deftest integration-test
  (is (= 1242 (day14/num-groups (day14/create-partitions "flqrgnkx" connection-graph 128 128)))))


