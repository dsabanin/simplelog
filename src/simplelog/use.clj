(ns simplelog.use
  (:require 
    [simplelog.core :as core]
    [clj-statsd :as stats]
    [clj-stacktrace.repl :as stack]))

(def print-exception stack/pst+)
(core/log-levels debug info warn error bench trace fatal spy)

(defmacro benchmark
  "Evaluates expr and prints the time it took.  Returns the value of
 expr."
  [name & body]
  `(let [start# (. System (nanoTime))
         ret# (do ~@body)
         time# (/ (double (- (. System (nanoTime)) start#)) 1000000.0)]
     (bench (str "[" ~name "] Elapsed time: " time# " msecs"))
     (stats/timing ~name (int time#))
     ret#))