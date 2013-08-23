(ns ^{:doc "JUnit formatter for Midje output"}
  midje-junit-formatter.core
  (:import java.io.File)
  (:use midje.emission.util)
  (:require [midje.data.fact :as fact]
            [midje.config :as config]
            [midje.emission.state :as state]
            [midje.emission.plugins.util :as util]
            [midje.emission.plugins.silence :as silence]
            [midje.emission.plugins.default-failure-lines :as lines]
            [clj-time.core :as time]
            [clojure.string :as str]
            [clojure.core.incubator :refer (dissoc-in)]
            [clojure.xml :as xml :only [emit-element]]))

(defonce report-file (atom nil))
(defonce last-fact (atom {}))
(defonce last-ns (atom nil))

(defn log-fn []
  (fn [text] (spit @report-file text :append true)))

(defn- log [string]
  (let [log-fn (log-fn)]
    (log-fn string)))

(defn escape [s]
  (if s
    (str/escape s {\" "&quot;"
                   \' "&apos;"
                   \< "&lt;"
                   \> "&gt;"
                   \& "&amp;"})
    ""))

(defn- make-report-filename [ns]
  (.mkdir (File. "target/surefire-reports"))
  (str "target/surefire-reports/TEST-" (escape (str ns)) ".xml"))

(defn- close-report []
  (when @report-file
    (log "</testsuite>"))
  (reset! report-file nil))

(defn- open-report [ns]
  (when @report-file
    (close-report))
  (reset! report-file (make-report-filename ns))
  (spit @report-file (str "<testsuite name='" (escape (str ns)) "'>")))

(defn- fact-name [fact]
  (or (fact/name fact)
      (fact/description fact)
      (str (fact/file fact) ":" (fact/line fact))))

(defn process-fact [fact]
  (let [elapsed (/ (time/in-msecs (time/interval (-> fact :attrs :start-time)
                                                 (-> fact :attrs :stop-time)))
                   1000.0)]
    (-> fact
        (dissoc-in [:attrs :start-time])
        (dissoc-in [:attrs :stop-time])
        (assoc-in [:attrs :time] elapsed))))

(defn pass []
  (log
    (with-out-str
      (xml/emit-element (process-fact @last-fact)))))

(defn- testcase-with-failure [failure-map]
  (let [testcase (process-fact @last-fact)
        failure-content (str "<![CDATA[" (apply str (lines/summarize failure-map)) "]]>")
        fail-type (:type failure-map)
        fail-element {:tag :failure
                      :content [failure-content]
                      :attrs {:type fail-type}}
        testcase-with-failure (assoc testcase :content [fail-element])]
    testcase-with-failure))

(defn fail [failure-map]
  (let [testcase (testcase-with-failure failure-map)]
    (log
      (with-out-str
        (xml/emit-element testcase)))))

(defn starting-to-check-fact [fact]
  (let [fact-namespace (str (fact/namespace fact))
        fact-name (fact-name fact)]
    (reset! last-fact {:tag :testcase
                       :attrs {:classname (escape fact-namespace) :name (escape fact-name) :start-time (time/now)}})))

(defn finishing-fact [fact]
  (swap! last-fact assoc-in [:attrs :stop-time] (time/now)))

(defn possible-new-namespace [ns]
  (when-not (= @last-ns ns)
    (open-report ns))
  (reset! last-ns ns))

(defn finishing-fact-stream [midje-counters clojure-test-map]
  (close-report))

(defn make-map [& keys]
  (zipmap keys
          (map #(ns-resolve *ns* (symbol (name %))) keys)))

(def emission-map (merge silence/emission-map
                         (make-map :fail
                                   :pass
                                   :possible-new-namespace
                                   :finishing-fact-stream
                                   :starting-to-check-fact
                                   :finishing-fact)))

(state/install-emission-map emission-map)
