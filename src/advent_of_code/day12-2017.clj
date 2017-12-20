(ns advent-of-code.day12-2017
  (:require [clojure.string :as string]
            [clojure.set :as set])
  (:import [com.google.common.graph Graph GraphBuilder Graphs])
  (:gen-class))

(def input-file "resources/day12-2017.txt")

(def input-strings (string/split (slurp input-file) #"\n"))

(def base-graph (.build (.allowsSelfLoops (GraphBuilder/undirected) true)))

(defn parse-input
  [str grph]
  (let [[ky-str vls-str] (string/split str #"<->")
        key (string/trim ky-str)
        vals (map string/trim (string/split (string/trim vls-str) #","))]
    (doseq [v vals] 
      (.putEdge grph key v))
    grph
    ))

(defn create-communication-graph
  [grph in-strings]
  (reduce #(parse-input %2 %1) grph in-strings))

(def input-graph (create-communication-graph base-graph input-strings))

(defn count-connected-nodes
  [grph nd]
  (count (Graphs/reachableNodes grph nd)))

(def part-1 (count (Graphs/reachableNodes input-graph "0")))


(defn num-groups
  [grph]
  (loop [nds (into #{} (.nodes grph))
         cnt 0]
    (if (empty? nds)
      cnt
      (recur (set/difference nds 
                             (into #{} 
                                   (Graphs/reachableNodes grph 
                                                          (first nds))))
             (inc cnt)))))

(def part-2 (num-groups input-graph))
