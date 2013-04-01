(ns midje-junit-formatter.t-core
  (:require 
    [midje.sweet :refer :all]
    [midje.util :refer :all]
    [midje-junit-formatter.test-util :refer :all]
    [midje-junit-formatter.core :as plugin]
    [midje.config :as config]
    [midje.emission.plugins.default-failure-lines :as failure-lines]))

(defn innocuously [key & args]
  (config/with-augmented-config {:emitter 'midje-junit-formatter.core
                                 :print-level :print-facts}
    (captured-output (apply (key plugin/emission-map) args))))

(def test-fact
  (with-meta (fn[]) {:midje/name "named" :midje/description "desc" :midje/namespace "blah"}))

(def test-failure-map
 {:type :some-prerequisites-were-called-the-wrong-number-of-times, 
   :namespace "midje.emission.plugins.t-junit"})

(fact "pass produces a <testcase> tag"
  (plugin/def-fact-cache)
  (plugin/starting-to-check-fact test-fact)

  (innocuously :pass) => "<testcase classname=\"blah\" name=\"named\"></testcase>\n"
  (provided 
    (plugin/log-fn) => #(println %)))

(fact "failure produces a <testcase> containing a <failure> tag"
  (plugin/def-fact-cache)
  (plugin/starting-to-check-fact test-fact)

  (innocuously :fail test-failure-map) => (contains "<testcase classname=\"blah\" name=\"named\">\n<failure type=\":some-prerequisites-were-called-the-wrong-number-of-times\">")
  (provided 
    (plugin/log-fn) => #(println %)))
