(ns advent-of-code.day3-2017
  (:require [clojure.string :as str])
  (:gen-class))

(def input-file "resources/day3-2017.txt")

(def input-number (Integer/parseInt (str/trim (slurp input-file))))

(defn last-odd-square-before
  [n]
  (last (take-while 
         (fn [v] (> n (v 1))) 
         (map #(vector % (* % %)) 
              (range 1 n 2)))))

(defn add-vec
  ([vec1 vec2]
   (vec (map + vec1 vec2)))
  ([vec1 vec2 & vecs]
   (vec (map + (add-vec vec1 vec2) (reduce add-vec vecs)))))


(def move-order {:R [1 0] 
                 :U [0 1] 
                 :L [-1 0] 
                 :D [0 -1] 
                 :UR [1 1] 
                 :DR [1 -1]
                 :UL [-1 1]
                 :DL [-1 -1]})

(defn scale-vec
  [scale vec]
  (into [] (map #(* scale %) vec)))

(defn scale-map-vec-add
  [vec-map scale-map]
  (reduce add-vec (map (fn [[key val]] (scale-vec val (vec-map key))) scale-map)))

(def make-move-from-map (partial scale-map-vec-add move-order))

;; way too much logic here
(defn make-move-vec
  [i r]
  (let [n-val (+ i 1)
        n-r (- r 1)
        i2 (* 2 i)
        i4 (* 4 i)
        i6 (* 6 i)] 
    (cond
     (== r 0) [0 0]
     (== r 1) (make-move-from-map {:R 1})
          (<= r i2) (make-move-from-map {:R 1 
                                    :U n-r})
     (<= r i4) (make-move-from-map {:R 1 
                                    :U (- i2 1)
                                    :L (- r i2)})
     (<= r i6) (make-move-from-map {:R 1
                                    :U (- i2 1)
                                    :L i2
                                    :D (- r i4)}) 
     true (make-move-from-map {:U (- i2 1)
                               :L i2
                               :D i2
                               :R (+ (- r i6) 1)})
     )))

(defn find-x-y
  [n]
  (if (<= n 1) 
    [0 0]
    (let [[i d] (last-odd-square-before n)
          s (/ (+ i 1) 2)
          m (- n d)
          start-pos [(- s 1) (+ (- 0 s) 1)]
          move-vec (make-move-vec s m)
          pos (add-vec start-pos move-vec)]
      pos)
    )
  )

(def part-1 (apply + (find-x-y input-number)))

(defrecord Point [coord val])

(def start-point (Point. [0 0] 1))

(defn get-neighbors
  [vec]
  (map (fn [[_ v]] (add-vec vec v)) move-order))

(defn make-next-val
  [pts]
  (let [last-point (last pts)
        this-point (find-x-y (+ 1 (count pts)))
        neighbors (get-neighbors this-point)
        neighbor-vals (map #(:val %) 
                           (filter (fn [pt] (some #{(:coord pt)} neighbors)) pts))
        new-pt (Point. this-point (reduce + neighbor-vals)) ]
    (conj pts new-pt)))

(defn add-next-val
  [pts]
  (conj pts (make-next-val pts)))

(def pt-struct (iterate make-next-val [start-point]))

(def part-2 (make-next-val (last (take-while #(> input-number(:val (last %))) pt-struct ))))
