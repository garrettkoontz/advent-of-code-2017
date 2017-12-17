(ns advent-of-code.day10-2017
  (:require [clojure.string :as string])
  (:gen-class))

(def input-file "resources/day10-2017.txt")
(def in-list (into [] (range 256)))
(def default-end [17 31 73 47 23])
(def start-pos 0)
(def start-skip-size 0)
(def partition-size 16)
(def rounds 64)

(def lengths-1 (map #(Integer/parseInt %) (string/split (slurp input-file) #"[,\n]")))

(defrecord KnotState [list position skip-size])

(def start-knot-state (KnotState. in-list start-pos start-skip-size))

(defn make-move
  [k-state length]
  (let [{start-state :list
         start-position :position
         start-skip-size :skip-size} k-state
         list-length (count start-state)
         next-index (+ start-position length)
         idx-end (mod next-index list-length)
         circled? (= 1 (quot next-index list-length))
         selection (if circled? 
                     (into [] 
                           (concat (subvec start-state start-position list-length) 
                                   (subvec start-state 0 idx-end)))
                     (subvec start-state start-position idx-end))
         rev-selection (into [] (reverse selection))
         new-state (if circled?
                     (into [] 
                           (concat (drop (- list-length start-position) rev-selection) 
                                   (subvec start-state idx-end start-position) 
                                   (take (- list-length start-position)
                                         rev-selection)))
                     (into [] 
                           (concat (subvec start-state 0 start-position)
                                   rev-selection
                                   (subvec start-state idx-end list-length))))
         new-start (if circled? 
                     (mod (+ idx-end start-skip-size) list-length)
                     (mod (+ start-position length start-skip-size) list-length))
         ]
    (KnotState. new-state new-start (inc start-skip-size))
    ))

(def part-1-list (reduce #(make-move %1 %2) start-knot-state lengths-1))
(def part-1 (apply * (take 2 (:list part-1-list))))

(defn pad-input-list
  [input-list]
  (concat input-list default-end))

(defn convert-to-ascii-char-code
  [strng]
  (map int strng))

(defn run-hash-transform
  [lengths start-state part-size]
  (reduce #(make-move %1 %2)
          start-state
          (apply concat (repeat part-size lengths))))

(defn dense-hash
  [list part-size]
  (let [split-list (partition part-size list)]
    (map #(apply bit-xor %) split-list)))

(defn tohex
  [ints]
  (apply str (map #(let [res (Integer/toString % 16)]
                     (if (= 1 (count res))
                       (str 0 res)
                       res)) ints)))

(defn knot-hash
  ([strng]
   (knot-hash (-> strng 
                  string/trim
                  convert-to-ascii-char-code 
                  pad-input-list) 
              start-knot-state 
              rounds
              partition-size))
  ([lengths start-state rounds part-size]
   (tohex 
    (dense-hash 
     (:list 
      (run-hash-transform lengths start-state rounds)) 
     part-size))))

(def part-2 (knot-hash (slurp input-file)))
