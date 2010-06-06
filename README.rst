Open JavaDoc Check
------------------

Project where I plan to write an Open Source DocCheck alternative.

.. image:: http://github.com/egonw/ojdcheck/raw/master/ojdcheckXHTML.png

Why?
----

I keep not finding an Open alternative for DocCheck. And how hard can it be
to write one myself?

License
-------

New BSD.

License of files in jar/
------------------------

The jar binaries in the com.github.ojdcheck/jar folder are copied from the
Ubuntu gjdoc package, license GPL v2. See:

http://changelogs.ubuntu.com/changelogs/pool/main/g/gjdoc/gjdoc_0.7.9-1/gjdoc.copyright

This library does not depend on gjdoc, but on the JavaDoc APIs instead upon compile
time, as well as runtime.

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
    -sourcepath com.github.ojdcheck/src
    com.github.ojdcheck

Currently, it uses the dictionaries from WinEdt as distributed with Jazzy (see english.txt):

"The dictionary files that accompany this file were adapted from the WinEdt 
English_US and English_UK dictionaries by Patrick Daly. The original two dictionaries 
were compiled by Aleksander Simonic (author of WinEdt) from public domain dictionaries
packaged with the amSpell spellchecker (by Erik Frambach. e-mail: e.h.m.frambach@eco.rug.nl).

The dictionaries are included with Jazzy with permission from Patrick and Aleksander."


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

