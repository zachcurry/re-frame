(ns todomvc.dbg
  (:require
            [re-frame.trace :as trace :include-macros true]
            ))


;;

(defmacro trace-exec
  "Evaluates exprs in a context in which *print-fn* is bound to .append
  on a fresh StringBuffer.  Returns the string created by any nested
  printing calls."
  ;; From with-out-str
  [& body]
  `(let [sb# (js/goog.string.StringBuffer.)]
     (try
       (binding [cljs.core/*print-newline* true
                 cljs.core/*print-fn*      (fn [x#] (.append sb# x#))]
         ~@body)
       (finally
         (trace/merge-trace! {:tags {:code (cljs.core/str sb#)}})))))
