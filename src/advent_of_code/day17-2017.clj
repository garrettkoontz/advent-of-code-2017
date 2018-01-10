(ns advent-of-code.day17-2017
  (:require [clojure.string :as string]
            [clojure.data.finger-tree :as ft])
  (:gen-class))

(def input-file "resources/day17-2017.txt")

(def input-steps (Integer/parseInt (string/trim (slurp input-file))))

(def default-max 2017)

(defn build-circular-buffer
  [steps mx]
  (loop [v 0
         p 0
         b [0]]
    (if (>= v mx)
      b
      (let [new-v (inc v)
            new-p (+ 1 (mod (+ p steps) new-v))
            new-b (into [] 
                        (flatten 
                         (conj
                          (subvec b 0 new-p) 
                          [new-v] 
                          (subvec b new-p))))]
        (recur new-v new-p new-b)))))

(def circle-buffer (build-circular-buffer input-steps default-max))

(defn value-after
  [buffer val]
  (first 
   (drop 1 
         (drop-while 
          #(not= val %) 
          buffer)))) 

(def part-1 (value-after circle-buffer default-max))

(def part-2-max 50000000)

;; value after 0 is actually just the most recent value inserted after 0, which is always at the beginning (because inserting before 0 would require an index to be -1, which never happens); don't need to build the structure, just determine the last number for which (mod (+ p steps) <index>) == 0



(def part-2 
  (first 
   (filter 
    #(= 1 (:p %)) 
    (reduce 
     #(let [v (inc %2)
            p (+ 1 (mod (+ (:p (first %1)) input-steps) v))]
        (cons {:p p :v v}
              (if (= 1 (:p (first %1)))
                %1
                (rest %1)))) 
     '({:p 0 :v 0})
     (range 0 part-2-max)))))

