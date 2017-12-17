(ns advent-of-code.day10-2017
  (:require [clojure.string :as string])
  (:gen-class))

(def input-file "resources/day10-2017.txt")

(def in-list (into [] (range 256)))

(def start-pos 0)

(def start-skip-size 0)

(def lengths (map #(Integer/parseInt %) (string/split (slurp input-file) #"[,\n]")))

(defrecord KnotState [list position skip-size])

(defn make-move
  [k-state length]
  (let [{start-state :list
         start-position :position
         start-skip-size :skip-size} k-state
         list-length (count start-state)
         next-index (+ start-position length)
         idx-end (mod next-index list-length)
         
         selection (subvec )]
    ))
