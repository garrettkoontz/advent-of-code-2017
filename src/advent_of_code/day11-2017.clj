(ns advent-of-code.day11-2017
  (:require [clojure.string :as string])
  (:gen-class))

(def input-file "resources/day11-2017.txt")

(def move-map {:nw [-1 0]
               :n [0 -1]
               :ne [1 -1]
               :se [1 0]
               :s [0 1]
               :sw [-1 1]
               })

(def start-state [0 0])

(def input-string (string/split (slurp input-file) #"[,\n]"))

(def input-keywords (doall (into [] (map keyword input-string))))

(defn add-vec
  [vec1 vec2]
  (map + vec1 vec2))

(defn make-move
  [state mv]
  (doall (add-vec 
          state 
          (mv move-map))))

(defn make-moves
  [in-state mvs]
  (loop [state in-state
         i 0
         max-pos 0]
    (if (= i (count mvs))
      [state max-pos]
      (let [new-state (make-move state (mvs i))]
        (recur new-state 
               (inc i) 
               (max max-pos (num-steps new-state))))
      )))

(defn abs
  [n]
  (max n (- n)))

(defn distance
  [a b]
  (let [[aq ar] a
        [bq br] b]
    (/ (+ (abs (- aq 
                  bq)) 
          (abs (- (+ aq ar) (+ bq br))) 
          (abs (- ar br))) 2)))

(defn num-steps
  [v]
  (distance 
   [0 0] 
   v))

(def final-pos (make-moves start-state input-keywords))

(def part-1 (num-steps (final-pos  0)))
(def part-2 (final-pos 1))

