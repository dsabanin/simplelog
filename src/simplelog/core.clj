(ns simplelog.core
  (:import
    [java.util Date]))

(defn timestamp
  []
  (.toGMTString (Date.)))

(defn thread-id
  []
  (.getId (Thread/currentThread)))

(def enabled-levels (atom #{}))
(def output (atom println))

(defn append
  [f & args]
  (binding [*flush-on-newline* true]
    (apply f args)
    f))

(defn define-level
  [level]
  `(defn ~level
     [& anything#]
     (when (@enabled-levels '~level)
       (let [signature# (format "[%s] [thread=%d] [%S]"
                                (simplelog.core/timestamp)
                                (simplelog.core/thread-id)
                                '~level)]
         (apply swap!
                simplelog.core/output
                simplelog.core/append
                signature# anything#)
         true))))

(defmacro log-levels
  [& levels]
  `(do
     ~@(map simplelog.core/define-level levels)
     (reset! simplelog.core/enabled-levels (into #{} '~levels))))

(defn silence!
  [& levels]
  (doseq [level levels]
    (swap! enabled-levels disj (-> level name symbol))))

(defn enable!
  [& levels]
  (doseq [level levels]
    (swap! enabled-levels conj (-> level name symbol))))
