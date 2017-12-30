(ns advent-of-code.day15-2017
  (:require [clojure.string :as string])
  (:gen-class))

(def input-file "resources/day15-2017.txt")

(def input-map 
  (into {} 
        (map 
         #(let [split-string (string/split % #" ")] 
            [(keyword (split-string 1)) 
             (Integer/parseInt (split-string 4))]) 
         (string/split (slurp input-file) #"\n"))))

(def factors
  {:A 16807 :B 48271})

(def divisor 2147483647)

(defn to-binary-string
  [i]
  (string/replace (format "%32s" (Integer/toBinaryString i)) " " "0"))

(defn get-next-val
  [factor divisor prev-val]
  (mod (* factor prev-val) divisor))

(def funcs {:A (partial get-next-val (:A factors) divisor)
            :B (partial get-next-val (:B factors) divisor)})

(defn get-lowest-n-bits
  [n bit-str]
  (let [st-idx (- (count bit-str) n)]
    (if (> 0 st-idx)
      bit-str
      (subs bit-str st-idx))))

(def get-lowest-16-bits
  (partial get-lowest-n-bits 16))

(def limit 40000000)

(defn get-lowest-16-from-int
  [i]
  (get-lowest-16-bits (to-binary-string i)))

(def part-1 
  (count 
   (filter 
    identity 
    (map #(= %1 %2) 
         (map #(get-lowest-16-from-int %) 
              (take limit 
                    (iterate (:A funcs) (:A input-map))))
         (map #(get-lowest-16-from-int %) 
              (take limit
                    (iterate (:B funcs) (:B input-map))))))))
(def part-2-limit
  5000000)

(def part-2
  (count
   (filter 
    identity
    (pmap #(= %1 %2)
         (map #(get-lowest-16-from-int %)
              (take part-2-limit 
                    (filter
                     #(= 0 (mod % 4))
                     (iterate (:A funcs) 
                              (:A input-map)
                              ;;65
                              ))))
         (map #(get-lowest-16-from-int %)
              (take part-2-limit 
                    (filter
                     #(= 0 (mod % 8))
                     (iterate (:B funcs) 
                              (:B input-map)
                              ;;8921
                              ))))))))
