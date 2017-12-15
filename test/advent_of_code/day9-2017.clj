(ns advent-of-code.test.day9-2017
  (:require [advent-of-code.day9-2017 :as day9])
  (:use [clojure.test]))

(def garbage-list
  ["<>" "<random characters>" "<<<<>" "<{!>}>" "<!!>"
   "<!!!>>" "<{o\"i!a,>{i<a>"])

(def group-list
  ["{}" "{{{}}}" "{{},{}}" "{{{},{},{{}}}}" "{<a>,<a>,<a>,<a>}" 
   "{{<a>},{<a>},{<a>},{<a>}}" "{{<!>},{<!>},{<!>},{<a>}}"])
