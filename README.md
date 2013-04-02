# midje-junit-formatter

[![Build Status](https://travis-ci.org/josephwilk/midje-junit-formatter.png?branch=master)](https://travis-ci.org/josephwilk/midje-junit-formatter)

##Install

Add midje-junit-formatter to project.clj

https://clojars.org/midje-junit-formatter

In the root of your project create a .midje.clj file.

.midje.clj
```clojure
(change-defaults :emitter 'midje-junit-formatter.core
                 :print-level :print-facts)
```

Turn off ANSI colours when you run midje as part of your build:


```
MIDJE_COLORIZE=NONE lein midje
```

The Junit Formatter Report is generated "report.xml" in the project root.

## Jenkins

Add report.xml to post hooks in your project config:

![report](http://s24.postimg.org/75vnnbtvp/Screen_Shot_2013_03_31_at_19_48_23.png)

You can now get Jenkins to understand your test results:

![report](http://s9.postimg.org/4edjlacwf/Screen_Shot_2013_03_31_at_19_46_08.png)

![report](http://s21.postimg.org/kwhan78br/Screen_Shot_2013_03_31_at_19_46_28.png)

##License
(The MIT License)

Copyright (c) 2013 Joseph Wilk

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the 'Software'), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
