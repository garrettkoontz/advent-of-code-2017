(ns advent-of-code.day9-2017
  (require [clojure.string :as string])
  (:gen-class))

(def input-file "resources/day9-2017.txt")

(def input-string (first (string/split (slurp input-file) #"\n")))

(def next-not-cancelled-regex "\w*(?<!!")

(def next-group-start (re-pattern (str next-not-cancelled-regex "{)")))

(def next-group-end (re-pattern (str next-not-cancelled-regex "})")))
(def next-garbage-start (re-pattern (str next-not-cancelled-regex "<)")))
(def next-garbage-end (re-pattern (str next-not-cancelled-regex ">)")))

(def next-valid-key (re-pattern (str next-not-cancelled-regex "[\{\}\<\>])")))

(def push cons)

(defn parse-group 
  [strng]
  ())

(defn parse-garbage
  [strng]
  nil)
