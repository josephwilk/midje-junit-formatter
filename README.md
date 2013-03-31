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

## Jenkins

Add report.xml to post hooks in your project config:

![report](http://s24.postimg.org/75vnnbtvp/Screen_Shot_2013_03_31_at_19_48_23.png)

You can now get Jenkins to understand your test results:

![report](http://s9.postimg.org/4edjlacwf/Screen_Shot_2013_03_31_at_19_46_08.png)

![report](http://s21.postimg.org/kwhan78br/Screen_Shot_2013_03_31_at_19_46_28.png)
