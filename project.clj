(defproject midje-junit-formatter "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/core.incubator "0.1.1"]
                 [midje "1.5.1"]
                 [clj-time "0.5.0"]]
  :profiles {:dev {:plugins [[lein-midje "3.0.0"]]}})
