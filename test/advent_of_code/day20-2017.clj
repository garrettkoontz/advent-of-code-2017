(ns advent-of-code.test.day20-2017
  (:require [advent-of-code.day20-2017 :as day20])
  (:import [advent_of_code.day20_2017 Particle])
  (:use [clojure.test]))

(def sample-string 
  "p=<3,0,0>, v=<2,0,0>, a=<-1,0,0>"
;;  "p=<4,0,0>, v=<0,0,0>, a=<-2,0,0>"
)



(deftest parse-input-test
  (is (= 
         {:p [3 0 0] :v [2 0 0] :a [-1 0 0]}
        ;; {:p [4 0 0] :v [2 0 0] :a [-2 0 0]}
           (day20/parse-input sample-string))))

(def sample-input (day20/parse-input sample-string))

(deftest make-move-test
  (is (= {:p [4 0 0] :v [1 0 0] :a [-1 0 0]} (day20/make-move sample-input))))
