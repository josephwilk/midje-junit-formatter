(ns ^{:doc "JUnit formatter for Midje output"}
  midje-junit-formatter.core
  (:use midje.emission.util)
  (:require [midje.data.fact :as fact]
            [midje.emission.state :as state]
            [midje.emission.plugins.util :as util]
            [midje.emission.plugins.silence :as silence]
            [midje.emission.plugins.default-failure-lines :as lines]
            [clojure.string :as str]))

(def report-file "report.xml")

(defn log-fn [& [a-fn]]
  (if a-fn
    a-fn
    (fn [text] (spit report-file text :append true))))

(defn- log [string]
  (let [log-fn (log-fn)]
    (log-fn string)))

(defn- reset-log []
  (spit report-file ""))

(defn def-fact-cache []
 (defonce last-fact-as-a-str (atom "")))

(defn- fact-name [fact]
  (or (fact/name fact)
         (fact/description fact)
           (str (fact/file fact) ":" (fact/line fact))))

(defn pass []
  (log (str @last-fact-as-a-str "</testcase>")))

(defn fail [failure-map]
  (dorun
    (map log
    [@last-fact-as-a-str
    (str "<failure type=\"" (:type failure-map) "\">")
    "<![CDATA["
    (apply str (lines/summarize failure-map))
    "]]>"
    (str "</failure></testcase>")])))

(defn starting-to-check-fact [fact]
  (reset! last-fact-as-a-str (str "<testcase classname=\""
                                  (fact/namespace fact)
                                  "\" name=\""
                                  (fact-name fact)
                                  "\">")))

(defn starting-fact-stream []
  (def-fact-cache)
  (reset-log)
  (log "<testsuite>"))

(defn finishing-fact-stream [midje-counters clojure-test-map]
  (log "</testsuite>"))

(defn make-map [& keys]
  (zipmap keys
          (map #(ns-resolve *ns* (symbol (name %))) keys)))

(def emission-map (merge silence/emission-map
                         (make-map :fail
                                   :pass
                                   :starting-fact-stream
                                   :finishing-fact-stream
                                   :starting-to-check-fact)))

(state/install-emission-map emission-map)