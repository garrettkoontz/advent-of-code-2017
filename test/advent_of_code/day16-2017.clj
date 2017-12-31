(ns advent-of-code.test.day16-2017
  (:require [advent-of-code.day16-2017 :as day16])
  (:use [clojure.test]))

(deftest op-test
  (is (= "eabcd" ((day16/op :s 1) "abcde")))
  (is (= "eabdc" ((day16/op :x 3 4) "eabcd")))
  (is (= "baedc" ((day16/op :p "e" "b") "eabdc"))))

(deftest spin-test
  (is (= "cdeab" (day16/spin 3 "abcde"))))

(deftest spin-array-test
  (is (= (seq (char-array "cdeab")) (seq (day16/spin-array 3 (char-array "abcde"))))))

(deftest exchange-test
  (is (= "cba" (day16/exchange 0 2 "abc")))
  (is (= "abedc" (day16/exchange 4 2 "abcde"))))

(deftest exchange-array-test
  (is (= (seq (char-array "cba")) (seq (day16/exchange-array 0 2 (char-array "abc")))))
  (is (= (seq (char-array "abedc")) (seq  (day16/exchange-array 4 2 (char-array "abcde"))))))

(deftest array-index-of-test
  (is (= 1 (day16/array-index-of \a (char-array "bacde"))))
  (is (= 1 (day16/array-index-of \a (char-array "bacadafa"))))
  (is (nil? (day16/array-index-of \b (char-array "aaaaaaa")))))

(deftest partner-array-test
  (is (= (seq "abfdec") (seq (day16/partner-array \f \c (char-array "abcdef"))))))

(deftest partner-test
  (is (= "abfdec" (day16/partner "f" "c" "abcdef"))))

(deftest parse-input-ops-test
  (is (= '((:s 4) (:x 11 0) (:p "a" "b")) (day16/parse-input-ops ["s4" "x11/0" "pa/b"] false)))
    (is (= '((:s 4) (:x 11 0) (:p \a \b)) (day16/parse-input-ops ["s4" "x11/0" "pa/b"] true))))

(def sample-ops '((:s 1) (:x 3 4) (:p "e" "b")))
(def sample-ops-chars '((:s 1) (:x 3 4) (:p \e \b)))

(deftest apply-array-ops-test
  (is (= (seq "baedc") (seq (day16/apply-ops (char-array "abcde") sample-ops-chars day16/op-array-memo)))))

(deftest apply-ops
  (is (= "baedc" (day16/apply-ops "abcde" sample-ops day16/op-memo))))

(deftest apply-ops-n-times
  (is (= "ceadb" (day16/apply-array-ops-n-times "abcde" sample-ops-chars 2))))
