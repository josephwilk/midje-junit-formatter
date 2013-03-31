# midje-junit-formatter

##Install

Add midje-junit-formatter to project.clj

https://clojars.org/midje-junit-formatter

.midje.clj
```clojure
(change-defaults :visible-deprecation false
                 :visible-future false
                 :emitter 'midje-junit-formatter.core
                 :print-level :print-facts)
```