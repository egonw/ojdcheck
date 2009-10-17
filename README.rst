# Open JavaDoc Check
------------------

Project where I plan to write an Open Source DocCheck alternative.

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

Howto
-----

1. Open the com.github.ojdcheck project in Eclipse and build.
2. cd com.github.ojdcheck
3. javadoc -doclet com.github.ojdcheck.OpenJavaDocCheck \
           -docletpath bin -sourcepath src \
           com.github.ojdcheck

The Doclet has two options, one to set the output file, one to give a comma-separated list
of custom tests (implementing ICheckDocTest). For example:

javadoc -file report.xml
        -tests com.github.ojdcheck.test.FooMethodTest
        -doclet com.github.ojdcheck.OpenJavaDocCheck \
        -docletpath bin -sourcepath src \
        com.github.ojdcheck
