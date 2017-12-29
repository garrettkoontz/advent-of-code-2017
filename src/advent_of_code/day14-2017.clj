(ns advent-of-code.day14-2017
  (:require [clojure.string :as string]
            [advent-of-code.day10-2017 :as knot]
            [advent-of-code.day12-2017 :as graphs]
            [clojure.set :as set])
  (:import [com.google.common.graph Graph GraphBuilder Graphs])
  (:gen-class))

(def input-file "resources/day14-2017.txt")

(def input-string (first (string/split (slurp input-file) #"\n")))

(def max-size 128)

(defn generate-hash-inputs
  [in-string]
  (map #(str in-string "-" %) (range max-size)))

(defn hex-to-bits
  [hx]
  (string/replace (format "%4s" (Integer/toBinaryString (Integer/parseInt hx 16))) " " "0"))

(defn knot-hashes
  [input-strng]
  (into [] (map #(knot/knot-hash %) (generate-hash-inputs input-strng))))

(defn get-binary-string-from-hex-string
  [hex-str]
  (apply str (map #(hex-to-bits (str %)) hex-str)))

(defn create-grid
  [kt-hashes]
  (map #(get-binary-string-from-hex-string %) kt-hashes))

(defn count-used
  [strng]
  (count (string/replace (apply str (create-grid (knot-hashes strng))) "0" "")))

(def part-1 (count-used input-string))

(defn get-val
  [grid [x y]]
  ((grid y) x))

(def connection-graph 
  (.build (.allowsSelfLoops (GraphBuilder/undirected) true)))

(defn get-neighbors-maxes
  [x-max y-max [x y]]
  (let [x-minus (- x 1)
        x-plus (+ x 1)
        y-minus (- y 1)
        y-plus (+ y 1)]
    (filter 
     #(and 
       (< -1 (% 0 )) 
       (> x-max (% 0)) 
       (< -1 (% 1)) 
       (> y-max (% 1))) 
     [[x-minus y] [x-plus y] [x y-minus] [x y-plus]])))

(def get-neighbors (partial get-neighbors-maxes max-size max-size))

(defn convert-strings-to-vecs
  [strs]
  (into [] 
        (map 
         #(into []  
                (map 
                 (fn [x] (Character/getNumericValue x)) 
                 %)) strs)))

(defn get-valid-neighbors
  [grd vc]
   (filter 
    #(= 1 (get-val grd %)) 
    (get-neighbors vc)))

(defn get-connected-pairs
  [grd x-size y-size]
  (for [x (range x-size) 
        y (range y-size)
        :let [vc [x y]
              vl (get-val grd vc)]
        :when (= 1 vl)
        :let [valid-neighbors (get-valid-neighbors grd vc)]
        v (conj valid-neighbors vc)]
    [vc v] 
    ))

(defn add-edge
  [grph [from to]]
  (.putEdge grph from to))

(defn partition-regions
  [grd grph x-size y-size] 
  (do 
    (doall 
     (map #(add-edge grph %) (get-connected-pairs grd x-size y-size))
     )
    grph
    )
  )

(defn num-groups
  [grph]
  (loop [nds (into #{} (.nodes grph))
         cnt 0]
    (if (empty? nds)
      cnt
      (recur 
       (set/difference 
        nds 
        (into #{} 
              (Graphs/reachableNodes 
               grph 
               (first nds))))
       (inc cnt)))))

(defn create-partitions
  [input-strng grph x-size y-size]
  (partition-regions
   (convert-strings-to-vecs 
    (create-grid 
     (knot-hashes input-strng)
     )
    )
   grph
   x-size
   y-size
   )
  )

(def partitioned-graph 
  (create-partitions 
   input-string 
   connection-graph
   max-size
   max-size))

(def part-2 (num-groups connection-graph))


