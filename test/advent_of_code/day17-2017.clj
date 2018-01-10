(ns advent-of-code.test.day17-2017
  (:require [advent-of-code.day17-2017 :as day17])
  (:use [clojure.test]))

(deftest build-circular-buffer-test
  (is (= [0] (day17/build-circular-buffer 3 0)))
  (is (= [0 1] (day17/build-circular-buffer 3 1)))
  (is (= [0 2 1] (day17/build-circular-buffer 3 2)))
  (is (= [0 2 3 1] (day17/build-circular-buffer 3 3)))
  (is (= [0 2 4 3 1] (day17/build-circular-buffer 3 4)))
  (is (= [0 5 2 4 3 1] (day17/build-circular-buffer 3 5)))
  (is (= [0 5 2 4 3 6 1] (day17/build-circular-buffer 3 6)))
  (is (= [0 5 7 2 4 3 6 1] (day17/build-circular-buffer 3 7)))
  (is (= [0 5 7 2 4 3 8 6 1](day17/build-circular-buffer 3 8)))
  (is (= [0 9 5 7 2 4 3 8 6 1] (day17/build-circular-buffer 3 9))))

(deftest value-after-test
  (is (= 3 (day17/value-after [0 1 2 3 4] 2)))
  (is (= 638 (day17/value-after (day17/build-circular-buffer 3 2017) 2017))))
