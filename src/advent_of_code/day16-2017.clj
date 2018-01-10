(ns advent-of-code.day16-2017
  (:require [clojure.string :as string])
  (:gen-class))

(def input-file "resources/day16-2017.txt")

(def input-list (string/split (slurp input-file) #"[,\n]"))

(defn op-parse-map
  [chars?]
  (if chars?
    {:x (fn [s] 
          (let [[a b] (string/split s #"/")] 
            [(Integer/parseInt a) (Integer/parseInt b)]))
     :p (fn [s] 
          (let [[a b] (string/split s #"/")] 
            [(.charAt a 0) (.charAt b 0)]))
     :s (fn [s] (Integer/parseInt s))}
    {:x (fn [s] 
          (let [[a b] (string/split s #"/")] 
            [(Integer/parseInt a) (Integer/parseInt b)]))
     :p (fn [s] 
          (let [[a b] (string/split s #"/")] 
            [a b]))
     :s (fn [s] (Integer/parseInt s))}))

;;probably better to drop to java, but out of principle...
(defn spin-array
  [x ^chars array]
  (let [length (alength array)
        idx (- length x)
        bdx (into [] (range 0 idx))
        adx (into [] (range idx length))
        idxs (into adx bdx)]
    (amap 
     ^chars array 
     i 
     ret
     (aget ^chars array (idxs i))
     )))

(defn exchange-array
  [posa posb ^chars array]
  (let [vala (char (aget array posa))
        valb (char (aget array posb))
        _ (aset array posb vala)
        _ (aset array posa valb)
        ]
    array)
  )

(defn array-index-of
  [itm ^chars array]
  (let [lgnth (alength array)]
    (loop [i 0]   
      (if (>= i lgnth) 
        nil  
        (if (= (char itm) (aget array i))
          i
          (recur (inc i))))
      ))
  )

(defn partner-array
  [parta partb ^chars array]
  (let [idxa (array-index-of parta array)
        idxb (array-index-of partb array)]
    (exchange-array idxa idxb array)))

(defn spin
  [x strng]
  (let [[b a] (map vec (split-at (- (count strng) x) strng))]
    (apply str (flatten [a b]))))

(defn exchange
  [posa posb strng]
  (let [a (min posa posb)
        b (max posa posb)]
    (str (subs strng 0 a) 
         (.charAt strng b) 
         (subs strng (+ 1 a) b) 
         (.charAt strng a)
         (subs strng (+ 1 b)))))

(defn partner
  [parta partb strng]
  (let [posa (.indexOf strng parta)
        posb (.indexOf strng partb)]
    (exchange posa posb strng)))

(defn op
  [key-op arg1 & [arg2]]
  (key-op
   {:s (partial spin arg1)
    :x (partial exchange arg1 arg2)
    :p (partial partner arg1 arg2)}))

(defn op-array
  [key-op arg1 & [arg2]]
  (key-op
   {:s (partial spin-array arg1)
    :x (partial exchange-array arg1 arg2)
    :p (partial partner-array arg1 arg2)}))

(def op-memo (memoize op))

(def op-array-memo (memoize op-array))

(defn parse-input-ops 
  [input-list chars?]
  (map 
   #(let [op (keyword (str (first %))) 
          args ((op (op-parse-map chars?)) (apply str (rest %)))]
      (flatten [op args])
      ) 
   input-list))

(def input-ops-array (parse-input-ops input-list true))

(def input-ops (parse-input-ops input-list false))

(defn apply-ops
  [ops-list op-memo in-str]
  (reduce 
   #((apply op-memo %2) %1) 
   in-str 
   ops-list))

(def start-str "abcdefghijklmnop")

(comment def part-1 (apply-ops start-str input-ops op-memo))

(def times 1000000000)

(def apply-ops-memo (memoize apply-ops))

(defn apply-array-ops-n-times
  [in-str ops-list times]
  (loop [i times
         i-s (char-array in-str)]
    (when (= 0 (mod i 1000))
      (println i (apply str (seq i-s)))
      )
    (if (<= i 0) 
      (apply str (seq i-s))
      (recur (dec i) (apply-ops-memo ops-list op-array-memo i-s))
      )
    )
  )

(def part-2 (apply-array-ops-n-times start-str input-ops-array times))

;; output is circular...
