Open JavaDoc Check
------------------

Project where I plan to write an Open Source DocCheck alternative.

![](http://github.com/egonw/ojdcheck/raw/master/ojdcheckXHTML.png)

Example output:

* http://egonw.github.com/ojdcheck/com.github.ojdcheck.html
* http://egonw.github.com/ojdcheck/org.openscience.cdk.html
* http://egonw.github.com/ojdcheck/signatures.html

Why?
----

I keep not finding an Open alternative for DocCheck. And how hard can it be
to write one myself?

Copyright
---------

    2009-2010  Egon Willighagen <egon.willighagen@gmail.com>
         2009  Rajarshi Guha <rajarshi.guha@gmail.com>
         2010  Charles Shelton <charles.shelton@gmail.com>

License
-------

This project license it source code as:

New BSD (see http://www.opensource.org/licenses/bsd-license.php).

Third party Licenses
--------------------

* OpenJDK JavaDoc 1.5 API

The jar binaries in the com.github.ojdcheck/jar folder are copied from the
Ubuntu openjdk package, license GPL v2. This library does not depend on
openjdk to run, but on the JavaDoc 1.5 APIs it defines instead, which are
used upon compile time.

* Jazzy

Jazzy is an LGPL spell checker, see http://jazzy.sf.net/.

Howto
-----

1. Open the com.github.ojdcheck project in Eclipse and build.
2. cd com.github.ojdcheck

In that directory you can give the code a test run:

    javadoc -doclet com.github.ojdcheck.OpenJavaDocCheck \
      -docletpath bin -sourcepath src \
      com.github.ojdcheck

The Doclet has several options. One is used to set the location of the output file,
a second to give a comma-separated list of custom tests (implementing
ICheckDocTest). For example:

    javadoc -file report.xml \
      -tests com.github.ojdcheck.test.FooMethodTest \
      -doclet com.github.ojdcheck.OpenJavaDocCheck \
      -docletpath bin -sourcepath src \
      com.github.ojdcheck

The third option is to control the output type, and you can use -xml and -xhtml.
A fourth option, -customonly, will only use the tests passed with the -tests
command.

Creating Help Pages
-------------------

It is possible for each test to define a URL to point a HTML page with further
explanation around the unit test. Future versions may provide an API to provide
examples that cause the test to fail.

There is a helper program to create such a help page:

    CLASSPATH=com.github.ojdcheck/jar/openjdk.javadoc.jar:com.github.ojdcheck/bin:com.github.ojdcheck.help/bin \\
    java com.github.ojdcheck.help.CreateHelpPage ojdcheck.tests.html

Jazzy
-----

Jazzy is an LGPL spell checker. The OpenJavaDocCheck test developed for it is LGPL too,
and found in the com.github.ojdcheck.jazzy project.

1. Open the com.github.ojdcheck.jazzy project in Eclipse and build.

Taking advantage of the in the How-To explained example javadoc command lines, you can
use this test with:

    javadoc -doclet com.github.ojdcheck.OpenJavaDocCheck \
      -docletpath com.github.ojdcheck.jazzy/jar/jazzy-core.jar \
      -docletpath com.github.ojdcheck/bin \
      -docletpath com.github.ojdcheck.jazzy/bin \
      -customonly \
      -tests com.github.ojdcheck.jazzy.SpellCheckerTest \
      -sourcepath com.github.ojdcheck/src \
      com.github.ojdcheck

Currently, it uses the dictionaries from WinEdt as distributed with Jazzy (see english.txt):

"The dictionary files that accompany this file were adapted from the WinEdt 
English_US and English_UK dictionaries by Patrick Daly. The original two dictionaries 
were compiled by Aleksander Simonic (author of WinEdt) from public domain dictionaries
packaged with the amSpell spellchecker (by Erik Frambach. e-mail: e.h.m.frambach@eco.rug.nl).

The dictionaries are included with Jazzy with permission from Patrick and Aleksander."

Additional dictionaries can be added using the -ojdcparam option of the OpenJavaDocCheck
doclet. The parameter to set is jazzy.dict, and the dictionary needs to be available on 
the classpath. The following example uses the demo dictionary chemistry.dict in the
dicts/ folder in this repository:

    javadoc -doclet com.github.ojdcheck.OpenJavaDocCheck \
      -docletpath com.github.ojdcheck.jazzy/jar/jazzy-core.jar \
      -docletpath com.github.ojdcheck/bin \
      -docletpath com.github.ojdcheck.jazzy/bin \
      -docletpath dicts/ \
      -ojdcparam jazzy.dict=chemistry.dict \
      -customonly \
      -tests com.github.ojdcheck.jazzy.SpellCheckerTest \
      -sourcepath com.github.ojdcheck/src \
      com.github.ojdcheck

The jazzy.dict parameter takes a ':' separated list of dictionaries.

Building with Ant
-----------------

1. ant clean jar
2. ant -f build-jazzy.xml clean jar

Then you can run the Doclet with:

    export CLASSPATH=ojdcheck.jar:ojdcheck-jazzy.jar
    javadoc -doclet com.github.ojdcheck.OpenJavaDocCheck \
      -customonly \
      -tests com.github.ojdcheck.jazzy.SpellCheckerTest \
      -sourcepath com.github.ojdcheck/src \
      com.github.ojdcheck

