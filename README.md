# midje-junit-formatter

##Install

Add midje-junit-formatter to project.clj

https://clojars.org/midje-junit-formatter

In the root of your project create a .midje.clj file.

.midje.clj
```clojure
(change-defaults :visible-deprecation false
                 :visible-future false
                 :emitter 'midje-junit-formatter.core
                 :print-level :print-facts)
```

Junit Formatter Report is generated in the project root in the report.xml file.
