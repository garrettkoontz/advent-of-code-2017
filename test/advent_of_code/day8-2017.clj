(ns advent-of-code.test.day8-2017
  (:use [clojure.test])
  (:require [advent-of-code.day8-2017 :as day8])
  (:import [advent_of_code.day8_2017 Instruction Condition Conditional]))

(def sample-string
  "b inc 5 if a > 1
a inc 1 if b < 5
c dec -10 if a >= 1
c inc -20 if c == 10")

(def sample-input-string (day8/parse-input sample-string))

(def inst-vec (day8/make-instruction-vec sample-input-string))

(deftest make-instruction-vec-test
  (let [instruction (inst-vec 2)]
    (is (= :c (:register instruction)))
    (is (= - (:op instruction)))
    (is (= -10 (:amt instruction)))
    (is (= (Condition. :a >= 1) (:condition instruction)))))

(def registers (day8/get-registers inst-vec))

(deftest conditional-test
  (is (= false (day8/eval-condition (Condition. :a (resolve (symbol ">")) 0) registers))))

(deftest get-registers-test
  (is (= {:b 0 :a 0 :c 0} registers)))

(def post-operate (day8/operate 
                   (day8/operate 
                    (day8/operate 
                     registers
                     (Instruction. :a + 10 
                                   (Condition. :b (resolve (symbol "<=")) 3)))
                    (Instruction. :b - 4 
                                  (Condition. :c (resolve (symbol ">")) 50)))
                   (Instruction. :c + 1 
                                 (Condition. :a (resolve (symbol ">")) 5))))

(deftest operate-test
  (is (= {:b 0 :a 10 :c 1} post-operate)))

(deftest run-instructions-test
  (is (= [{:a 1 :b 0 :c -10} 10] (day8/run-instructions registers inst-vec))))

