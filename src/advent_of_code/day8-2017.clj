(ns advent-of-code.day8-2017
  (:require [clojure.string :as string])
  (:gen-class))

(def input-file "resources/day8-2017.txt")

(defn parse-input
  [strng]
  (map #(string/split % #" ") (string/split strng #"\n")))

(def input-string (parse-input (slurp input-file)))

(defprotocol Conditional 
  "Determines whether the condition in this is true about the passed map"
  (eval-condition [this mp]))
(defrecord Instruction [register op amt condition])
(defrecord Condition [register comparison amt]
  Conditional 
  (eval-condition [this mp]
    ((:comparison this) ((:register this) mp) (:amt this)))
)

(def op-map
  {:inc +
   :dec -})

(def comp-map { :> > 
               :< < 
               :>= >= 
               :<= <=
               :== =
               :!= not=
})

(defn make-instruction-vec
  [inp-string]
  (into [] (map #(let [[reg op amt _ cond-reg comp lmt] %
                       register (keyword reg)
                       cond-register (keyword cond-reg)
                       amount (Integer/parseInt amt)
                       limit (Integer/parseInt lmt)
                       operation ((keyword op) op-map)
                       comparison ((keyword comp) comp-map)]
                   (Instruction. register operation amount (Condition. cond-register comparison limit))
                   ) inp-string)))

(def input-instructions (make-instruction-vec input-string))

(defn get-registers
  [instruction-vec]
  (reduce #(assoc %1 (:register %2) 0 (:register (:condition %2)) 0) {} instruction-vec))

(def input-registers (get-registers input-instructions))

(defn execute-instruction
  [registers instruction]
  (let [reg (:register instruction)
          cur-amt (reg registers)
          amt ((:op instruction) cur-amt (:amt instruction))
        output (assoc registers reg amt)]
    output))

(defn operate
  [registers instruction]
  (if (eval-condition (:condition instruction) registers)
    (execute-instruction registers instruction)
    registers)
  )

(defn run-instructions
  [registers instructions]
  (reduce 
   #(let [new-regs (operate (%1 0) %2)
          new-max  (max (%1 1) (apply max (vals new-regs)))]
      [new-regs new-max]) 
   [registers 0] 
   instructions))

(def final-instructions (run-instructions input-registers input-instructions))

(def part-1 (apply max (vals (final-instructions 0))))

(def part-2 (final-instructions 1))
