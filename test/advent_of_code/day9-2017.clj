(ns advent-of-code.test.day9-2017
  (:require [advent-of-code.day9-2017 :as day9])
  (:use [clojure.test]))

(def garbage-list
  ["<>" "<random characters>" "<<<<>" "<{!>}>" "<!!>"
   "<!!!>>" "<{o\"i!a,>{i<a>"])

(def delete-cancelled-default (partial day9/delete-cancelled "!"))

(def remove-garbage-default (partial day9/remove-garbage "<" ">"))

(def no-cancelled-garbage-list
  (into [] (map #(delete-cancelled %) garbage-list)))

(deftest delete-cancelled-test
  (is (= "<>" (delete-cancelled-default (garbage-list 0))))
  (is (= "<{}>" (delete-cancelled-default (garbage-list 3))))
  (is (= "<>" (delete-cancelled-default (garbage-list 4))))
  (is (= "<>" (delete-cancelled-default (garbage-list 5))))
  (is (= "<{o\"i,>{i<a>" (delete-cancelled-default (garbage-list 6)))))

(deftest remove-garbage
  (is (= "{i" (reduce str (map
                           #(str (% 0)) 
                           (map 
                            #(remove-garbage-default %) 
                            no-cancelled-garbage-list))))))

(def group-list
  ["{}" 
   "{{{}}}" 
   "{{},{}}" 
   "{{{},{},{{}}}}" 
   "{<a>,<a>,<a>,<a>}" 
   "{{<a>},{<a>},{<a>},{<a>}}" 
   "{{<!!>},{<!!>},{<!!>},{<!!>}}"
   "{{<!>},{<!>},{<!>},{<a>}}"])

(def score-group-default (partial day9/score-group "{" "," "}"))
(def cleaned-groups (into [] (map #(remove-garbage-default (delete-cancelled-default %)) group-list)))

(deftest score-group
  (is (= 1 ((score-group-default (cleaned-groups 0)) 0)))
  (is (= 6 ((score-group-default (cleaned-groups 1)) 0)))
  (is (= 5 ((score-group-default (cleaned-groups 2)) 0)))
  (is (= 16 ((score-group-default (cleaned-groups 3)) 0)))
  (is (= 1 ((score-group-default (cleaned-groups 4)) 0)))
  (is (= 9 ((score-group-default (cleaned-groups 5)) 0)))
  (is (= 9 ((score-group-default (cleaned-groups 6)) 0)))
  (is (= 3 ((score-group-default (cleaned-groups 7)) 0))))

