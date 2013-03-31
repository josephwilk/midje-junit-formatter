# midje-junit-formatter

##Install

.midje.clj
```clojure
(change-defaults :visible-deprecation false
                 :visible-future false
                 :emitter 'midje-junit-formatter.core
                 :print-level :print-facts)
```