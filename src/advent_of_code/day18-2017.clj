(ns advent-of-code.day18-2017
  (:require [clojure.string :as string]
            [clojure.core.async :as async])
  (:gen-class))

(def input-string "resources/day18-2017.txt")


(defn get-val
  [y state]
  (if (keyword? y) 
    (y state)
    y))

(defn inc-pc
  [state]
  (assoc state :pc (inc (:pc state))))

(def op-codes
  {:snd (fn [x _ state] (inc-pc  
                         (assoc state :freq (get-val x state))))
   :set (fn [x y state] (inc-pc 
                         (assoc state x (get-val y state))))
   :add (fn [x y state] (inc-pc 
                         (assoc state x (+ (x state)
                                           (get-val y state)))))
   :mul (fn [x y state] (inc-pc  
                         (assoc state x (* (x state) 
                                           (get-val y state)))))
   :mod (fn [x y state] (inc-pc  
                         (assoc state x (mod (x state)
                                             (get-val y state)))))
   :rcv (fn [x _ state] (inc-pc 
                         (if (not= 0 (x state))
                           (assoc state x (:freq state))
                           state)))
   :jgz (fn [x y state] (if (< 0 (x state))
                          (assoc state :pc (+ (:pc state)
                                              (get-val y state))
                                 )
                          (inc-pc state)))
   })

(defprotocol Executable
  "Defines a record that can be executed"
  (exec [this state op-codes]))
(defrecord Instruction [ins reg val]
  Executable
  (exec [this state op-codes]
    (((:ins this) op-codes) (:reg this) (:val this) state)))

(defn int-or-keyword
  [s]
  (if (nil? s) nil
      (try (Integer/parseInt s)
           (catch java.lang.NumberFormatException e
             (keyword s)))))

(defn parse-input
  [input-str]
  (let [strs (string/split input-str #"\n")]
    (map #(let [[i r v] (string/split % #" ")]
            (Instruction. (keyword i) (int-or-keyword r) (int-or-keyword v))) strs)))

(def input-instructions (into [] (parse-input (slurp input-string))))

(def start-state
  (into {:pc 0} (map #(vector (:reg %) 0) input-instructions)))

(defn run-op
  [instruc state op-codes]
  (exec instruc state op-codes))

(defn run-op-from-ops-vec
  [instruct-vec state idx op-codes]
  (run-op (instruct-vec idx) state op-codes)
  )

(defn part-1-fn 
  [input-instructs condition]
  (println start-state)
  (loop [idx 0 
         state start-state]
    (let [next-instruct (input-instructs idx)
          next-state (run-op next-instruct state op-codes)]
      (if (and (>= (:pc next-state) (count input-instructs)) (<= (:pc next-state) 0)) 
        next-state
        (if
            (condition state next-instruct next-state)
          ((:reg next-instruct) next-state)
          (recur (:pc next-state) next-state))
        ))))

(def part-1 (part-1-fn 
             input-instructions 
             (fn [state next-instruct next-state] 
               (and 
                (= :rcv (:ins next-instruct)) 
                (not= state next-state)))))

(defn op-codes-2
  [idx vars que ins pid]
  (case (:ins ins)
    :snd [(inc idx) vars ((:reg ins) vars) que]
    :rcv 
        (if (empty? que)
      [idx vars nil que]
      [(inc idx) (assoc vars (:reg ins) (first que)) nil (vec (rest que))])
    :set [(inc idx) (assoc vars (:reg ins) (get-val (:val ins) vars)) nil que]
    :jgz (let [x (get-val (:reg ins) vars)
               y (get-val (:val ins) vars)]
           (if (> x 0)
             [(+ idx y) vars nil que]
             [(inc idx) vars nil que]))
    :add (let [x (get-val (:reg ins) vars)
               y (get-val (:val ins) vars)] 
           [(inc idx) (assoc vars (:reg ins) (+ x y)) nil que])
    :mul (let [x (get-val (:reg ins) vars)
               y (get-val (:val ins) vars)] 
           [(inc idx) (assoc vars (:reg ins) (* x y)) nil que])
    :mod (let [x (get-val (:reg ins) vars)
               y (get-val (:val ins) vars)] 
           [(inc idx) (assoc vars (:reg ins) (mod x y)) nil que])))

(defn sending-progs
  [ins vars]
  (loop [ia 0 
         ib 0 
         vsa vars 
         vsb (assoc vars :p 1) 
         qa [] 
         qb [] 
         cnt 0]
    (let [[ia' vsa' sna qa'] (op-codes-2 ia vsa qa (ins ia) :a)
          [ib' vsb' snb qb'] (op-codes-2 ib vsb qb (ins ib) :b)
          qa'' (if (nil? snb) qa' (conj qa' snb))
          qb'' (if (nil? sna) qb' (conj qb' sna))]
      (cond (> cnt 7000) cnt
            (and (= ia ia') (= ib ib'))  cnt
            (not (nil? snb)) (recur ia' ib' vsa' vsb' qa'' qb'' (inc cnt))
            :else (recur ia' ib' vsa' vsb' qa'' qb'' cnt)
            )
      )))

(def part-2 (sending-progs 
             input-instructions
             (into {}         
                   (map #(vector (:reg %) 0) 
                        (filter #(keyword? (:reg %)) input-instructions)))))




