(ns advent-of-code.day19-2017
  (:require [clojure.string :as str])
  (:gen-class))

(def input-file "resources/day19-2017.txt")

(def valid-lines
  #{\| \+ \-})

(defn parse-input
  [strng]
  (let [lines (str/split strng #"\n")
        vecs (mapv #(vec (char-array %)) lines)]
    vecs))

(def input-vecs (parse-input (slurp input-file)))

(def dirs ;; D L U R
  [[0 1]
   [-1 0]
   [0 -1]
   [1 0]])

(defn move-dir
  [[x y] dir]
  (mapv + dir [x y]))

(defn new-dir
  [diag [[x y] dir]]
  (let [idx (.indexOf dirs dir)
        new-dirs [(dirs (mod (+ idx 1) 4)) (dirs (mod (- idx 1) 4))]
        new-dir (filter #(let [[a b] (move-dir [x y] %)
                               new-val (get-in diag [b a])]
                           (and (not (nil? new-val)) (not= \space new-val))) new-dirs)]
    (first new-dir)
    )
  )

(defn run-op
  [diag [[x y] dir]]
  (let [ch (get-in diag [y x])
        valid-char (contains? valid-lines ch)
        pos (move-dir [x y] dir)]
    (cond (= \space ch) [nil nil nil]
          (not valid-char) [pos dir ch]
          (or (= \| ch) (= \- ch)) [pos dir nil]
          :else (let [dir' (new-dir diag [[x y] dir])
                      vl (move-dir [x y] dir')]
                  [vl dir' nil]
                  )
        
      )
    )
  )

(defn navigate-diag
  [diag]
  (let [start-x (count (first (split-with (partial = \space) (diag 0))))]
    (loop [pos [start-x 0]
           dir [0 1]
           letters []
           cnt 0]
      (let [[pos' dir' ltr] (run-op diag [pos dir])
            letters' (if (nil? ltr) letters (conj letters ltr))
            [x' y'] pos']
        (if
          (nil? pos') 
          [letters cnt]
          (recur pos' dir' letters'
                (inc cnt) ))))
    ))

(def part-1 (apply str ((navigate-diag input-vecs) 0)))

(def part-2 ((navigate-diag input-vecs) 1))
