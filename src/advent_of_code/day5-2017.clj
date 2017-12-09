(ns advent-of-code.day5-2017
  (:require [clojure.string :as str]
            [clojure.pprint :as pp])
  (:gen-class))

(def input-file "resources/day5-2017.txt")

(def input-instructions (into [] (map #(Integer/parseInt %) (str/split (slurp input-file) #"\n"))) )

(defn jmp
  [[ins-vec idx]]
  (let [val (ins-vec idx)
        new-idx (+ val idx)
        ]
    [(assoc ins-vec idx (inc val)) new-idx]))

(defn jmp!
  [mod-val-f ins-vec idx]
  (let [val (ins-vec idx)
        new-idx (+ val idx)
        ]
    (assoc! ins-vec idx (mod-val-f val))))

(defn get-next-index
  [ins-vec idx]
  (let [inst-count (count ins-vec) 
        val-at-index (ins-vec idx)] 
    (+ val-at-index idx)) )

(defn next-inst-will-break?
  [ins-vec idx]
  (let [inst-count (count ins-vec) 
        next-index (get-next-index ins-vec idx)] 
    (<= inst-count next-index)))

(defn execute-jmps-until-escape
  [tst-f jmp-f jmp-inst idx]
  (take-while #(tst-f %) (iterate jmp-f [jmp-inst idx])))

(def part-1-list (execute-jmps-until-escape next-inst-will-break? jmp input-instructions 0))

(defn mutate-until-break
  [mod-val-f jmp-inst-vec start-idx]
  (loop [idx start-idx v (transient jmp-inst-vec) cnt 1]
    (if (next-inst-will-break? v idx)
      [(persistent! (jmp! mod-val-f v idx)) cnt] 
            (recur (get-next-index v idx) (jmp! mod-val-f v idx) (inc cnt)))))

(def part-1 (mutate-until-break inc input-instructions 0))

(defn dec-if-ge-3
  [n]
  (if (>= n 3)
    (dec n)
    (inc n)))

(def part-2 (mutate-until-break dec-if-ge-3 input-instructions 0))

