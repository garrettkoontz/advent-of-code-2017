(ns advent-of-code.day9-2017
  (require [clojure.string :as string])
  (:gen-class))

(def input-file "resources/day9-2017.txt")

(def input-string (first (string/split (slurp input-file) #"\n")))

(def push cons)

(def cancel-char-exclm "!")

(def garbage-start-lt "<")

(def garbage-end-gt ">")

(defn delete-cancelled
  [cancel-char strng]
  (loop [in-string strng
         result ""]
    (let [cancel-idx (string/index-of in-string cancel-char)]
      (if (nil? cancel-idx)
        (str result in-string)
        (recur (subs in-string (+ cancel-idx 2)) 
               (str result (subs in-string 0 cancel-idx))))
      )
    )
  )

(defn remove-garbage
  [g-s g-e strng]
  (loop [in-string strng
         result ""
         garbage ""]
    (let [garbage-start (string/index-of in-string g-s)]
      (if (nil? garbage-start)
        [(str result in-string) garbage]
        (let [garbage-end (string/index-of in-string g-e)
              pre-garbage (subs in-string (+ 1 garbage-end))
              post-garbage (str result (subs in-string 0 garbage-start))
              garbage-string (subs in-string (+ 1 garbage-start) garbage-end)]
          (recur post-garbage 
                 (str result pre-garbage)
                 (str garbage garbage-string))
          )
        )
      ))
  )

(defn clean-input
  [strng]
  (remove-garbage "<" ">" (delete-cancelled "!" strng))
)

(defn score-group
  [gr-s gr-s gr-e strng]
  ;; don't need the separator in this instance
  (let [in-string (string/replace strng gr-s "")]
    (reduce 
     (fn [[acum level] st]
       (case (str st)
          "{" [acum (inc level)]
          "}" [(+ acum level) (dec level)]
          (vector acum level))) 
     [0 0] in-string)))

(def part-1 
  (score-group "{" "," "}" (clean-input input-string)))
(def part-2 
  (count ((clean-input input-string) 1)))
