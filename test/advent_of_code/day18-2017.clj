(ns advent-of-code.test.day18-2017
  (:require [advent-of-code.day18-2017 :as day18])
  (:use [clojure.test])
  (:import [advent_of_code.day18_2017 Instruction]))

(def sample-input "set a 1
add a 2
mul a a
mod a 5
snd a
set a 0
rcv a
jgz a -1
set a 1
jgz a -2")

(deftest parse-input-test
  (is (= [(day18/Instruction. :set :a 1)
          (day18/Instruction. :add :a 2)
          (day18/Instruction. :mul :a :a)
          (day18/Instruction. :mod :a 5)
          (day18/Instruction. :snd :a nil)] (take 5 (day18/parse-input sample-input)))))

(deftest op-codes-test
  (is (= {:freq 8 :pc 1} ((:snd day18/op-codes) 8 nil {:freq nil :pc 0})))
  (is (= {:a 1 :b 1 :c 3 :pc 1} ((:set day18/op-codes) :a :b {:a 2 :b 1 :c 3 :pc 0})))
  (is (= {:a 5 :b 1 :c 3 :pc 1} ((:set day18/op-codes) :a 5  {:a 2 :b 1 :c 3 :pc 0})))
  (is (= {:a 3 :b 1 :c 3 :pc 1} ((:add day18/op-codes) :a 1 {:a 2 :b 1 :c 3 :pc 0})))
  (is (= {:a 5 :b 1 :c 3 :pc 1} ((:add day18/op-codes) :a :c {:a 2 :b 1 :c 3 :pc 0})))
  (is (= {:a 6 :b 3 :c 9 :pc 1} ((:mul day18/op-codes) :a :b {:a 2 :b 3 :c 9 :pc 0})))
  (is (= {:a 8 :b 3 :c 9 :pc 1} ((:mul day18/op-codes) :a 4 {:a 2 :b 3 :c 9 :pc 0})))
  (is (= {:a 2 :b 3 :c 0 :pc 1} ((:mod day18/op-codes) :c :b {:a 2 :b 3 :c 9 :pc 0})))
  (is (= {:a 2 :b 3 :c 1 :pc 1} ((:mod day18/op-codes) :c 4 {:a 2 :b 3 :c 9 :pc 0})))
  (is (= {:a 8 :b 3 :c 9 :freq 8 :pc 1} ((:rcv day18/op-codes) :a nil {:a 2 :b 3 :c 9 :freq 8 :pc 0})))
  (is (= {:a 0 :b 3 :c 9 :freq 8 :pc 1} ((:rcv day18/op-codes) :a nil {:a 0 :b 3 :c 9 :freq 8 :pc 0})))
  (is (= {:a 1 :pc 3} ((:jgz day18/op-codes) :a -2 {:a 1 :pc 5})))
  (is (= {:a 0 :pc 6} ((:jgz day18/op-codes) :a -2 {:a 0 :pc 5})))
  (is (= {:a -1 :pc 6} ((:jgz day18/op-codes) :a -2 {:a -1 :pc 5})))
  (is (= {:a 1 :b -3 :pc 2} ((:jgz day18/op-codes) :a :b {:a 1 :b -3 :pc 5})))
  )

(deftest run-op
  (is (= {:a 1 :pc 1} (day18/run-op (day18/Instruction. :add :a 1) {:a 0 :pc 0}))))

(deftest op-codes-2-test
  (is (= )))

(run-op (Instruction. :rcv :b nil) 
(run-op (Instruction. :snd :p nil) 
(run-op (Instruction. :add :p 10) 
        start-state-test 
(op-codes-2 0 1)) 
(op-codes-2 0 1)) 
(op-codes-2 1 0))
