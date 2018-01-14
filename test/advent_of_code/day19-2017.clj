(ns advent-of-code.test.day19-2017
  (:require [advent-of-code.day19-2017 :as day19])
  (:use [clojure.test])
  (:gen-class))

(def sample-input 
"     |          
     |  +--+    
     A  |  C    
 F---|----E|--+ 
     |  |  |  D 
     +B-+  +--+ "
)

(deftest parse-input-test
  (is (= [\space \F \- \- \- \| \- \- \- \- \E \| \- \- \+ \space] ((day19/parse-input sample-input) 3))))

(deftest move-dir-test
  (is (= [1 2] (day19/move-dir [1 2] [0 0])))
  (is (= [-2 -3] (day19/move-dir [-2 -2] [0 -1]))))

(deftest new-dir-test
  (is (= [0 1] (day19/new-dir [[\space \space \space \space] 
                                [\space \+     \-     \space] 
                                [\space \|     \space \space] 
                                [\space \space \space \space]] 
                               [[1 1] [-1 0]])))
  (is (= [1 0] (day19/new-dir  [[\space \space \space \space] 
                                [\space \+     \-     \space] 
                                [\space \|     \space \space] 
                                [\space \space \space \space]]  
                              [[1 1] [0 1]])))
  (is (= [-1 0] (day19/new-dir [[\space \space \space \space] 
                                [\space \|     \space \space] 
                                [\-     \+     \space \space]
                                [\space \space \space \space]] [[1 2] [0 1]])))
  (is (= [0 -1] (day19/new-dir [[\space \space \space \space] 
                                [\space \|     \space \space] 
                                [\-     \+     \space \space]
                                [\space \space \space \space]] [[1 2] [1 0]])) ;; | ^ -+
))

  (def sample-diag (day19/parse-input sample-input))

(deftest run-op-test
  (is (= [[5 2] [0 1] nil] (day19/run-op sample-diag [[5 1] [0 1]])))
  (is (= [[5 3] [0 1] \A] (day19/run-op sample-diag [[5 2] [0 1]])))
  (is (= [[6 5] [1 0] nil] (day19/run-op sample-diag [[5 5] [0 1]]))))

(deftest navigate-diag-test
  (is (= [[\A \B \C \D \E \F] 38] (day19/navigate-diag sample-diag))))
