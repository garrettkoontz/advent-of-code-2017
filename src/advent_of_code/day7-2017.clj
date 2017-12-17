(ns advent-of-code.day7-2017
  (:require [clojure.string :as str]
            [clojure.set :as set])
  (:import [asyslu/loom])
  (:gen-class))

(def input-file "resources/day7-2017.txt")


(defn parse-input-string
  [string] 
  (into [] (map 
            #(into [] 
                   (re-seq #"[\w\d]+" %)) 
            (str/split string #"\n"))))

(def input-string (parse-input-string (slurp input-file)))

(defrecord Program [name val progs-above])

(defn input-objects 
  [in-string]
  (into [] 
        (map 
         (fn [[name weight & holdings]] 
           (Program. (keyword name) (Integer/parseInt weight) 
                     (map keyword holdings))
           ) in-string))
  )

(defn map-input-objects 
  [inp-obj] 
  (reduce 
   #(assoc %1 (keyword (:name %2)) %2) {}
   inp-obj))

(defn collect-sub-progs
  [progs]
  (into #{}
        (flatten (map #(:progs-above %) progs))))

(defn collect-prog-names
  [progs]
  (into #{}
        (map #(:name %) progs)))

(defn bottom-prog
  [progs]
  (if (vector? progs)
    (let [names (collect-prog-names progs)
          sub-progs (collect-sub-progs progs)]
      (set/difference names sub-progs))
    (if (map? progs)
      (bottom-prog (into [] (vals progs)))
      
      nil))
  )

(def part-1 (bottom-prog (input-objects input-string)))

(def input-maps (map-input-objects (input-objects input-string)))

(defn find-weight-at
  [progs-map node-name]
  (let [node (node-name progs-map)
        above (:progs-above node)]
    (+ (:val node) 
       (if (not (empty? above))
         (reduce + 0 
                 (map #(find-weight-at progs-map %)  
                      above))
         0)
       )))

(def find-weight-at-memo (memoize find-weight-at))

(defn get-child-weights
  [inp-map children]
  (group-by #(% 1)  
            (map 
             #(vector % 
                      (find-weight-at-memo inp-map %)) children )))

(defn are-children-balanced?
  [inp-map node-name]
  (let [node (node-name inp-map)
        sub-nodes (:progs-above node)]
    (== 1 (count (get-child-weights inp-map sub-nodes)))))

(defn find-unbalanced-node
  ([inp-map]
   (let [bottom (first (bottom-prog inp-map))]
     (find-unbalanced-node inp-map bottom)))
  ([inp-map node]
   (let [inp-node (node inp-map)
         sub-nodes (:progs-above inp-node)
         sums-map (get-child-weights inp-map sub-nodes)
         bal? (are-children-balanced? inp-map node)] 
     (if (not bal?)
       (let [unbal-val (first (filter (fn [[k v]] (== 1 (count v)) ) sums-map ))
             unbal-name (((unbal-val 1) 0) 0)]
         (if (reduce 
              #(and %1 %2) 
              true 
              (map #(are-children-balanced? inp-map %) sub-nodes )) 
           (let [bal-val (first (filter (fn [[k v]] (not (== 1 (count v)))) sums-map))] 
             {unbal-name (- (bal-val 0) (unbal-val 0))})
           (find-unbalanced-node inp-map unbal-name)
           )
         )))))

(def part-2 (let [un-bal (find-unbalanced-node input-maps)
                  k (first (keys un-bal))
                  v (k un-bal)]
              (+ v (:val (k input-maps)))
              ))
