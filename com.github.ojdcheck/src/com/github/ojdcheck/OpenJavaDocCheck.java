/* Copyright (c) 2009  Egon Willighagen <egonw@users.sf.net>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 *   Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 *   The names of its contributors may not be used to endorse or promote
 *   products derived from this software without specific prior written
 *   permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.ojdcheck;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.ojdcheck.report.IReportGenerator;
import com.github.ojdcheck.report.XHTMLGenerator;
import com.github.ojdcheck.report.XMLGenerator;
import com.github.ojdcheck.sort.AlphaNumericalSorter;
import com.github.ojdcheck.test.IClassDocTester;
import com.github.ojdcheck.test.ITestReport;
import com.github.ojdcheck.test.content.MissingExceptionDescriptionTest;
import com.github.ojdcheck.test.content.MissingPeriodInFirstSentenceTest;
import com.github.ojdcheck.test.missing.MissingDescriptionTest;
import com.github.ojdcheck.test.missing.MissingParamTagTest;
import com.github.ojdcheck.test.missing.MissingReturnTagTest;
import com.github.ojdcheck.test.mistake.MultipleVersionTagsTest;
import com.github.ojdcheck.test.mistake.ReturnsTypoTest;
import com.github.ojdcheck.test.template.ExceptionTemplateTest;
import com.github.ojdcheck.test.template.ParameterTemplateTest;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.RootDoc;

/**
 * Doclet that checks the correctness of JavaDoc.
 */
public class OpenJavaDocCheck extends Doclet {

    private static boolean customOnly = false;
    private static String outputFile = null;
    private static IReportGenerator generator = new XMLGenerator();

    private static List<IClassDocTester> docTests =
        new ArrayList<IClassDocTester>();

    private static void addStandardTests() {
        docTests.add(new MissingDescriptionTest());
        docTests.add(new ExceptionTemplateTest());
        docTests.add(new ParameterTemplateTest());
        docTests.add(new MissingExceptionDescriptionTest());
        docTests.add(new MissingReturnTagTest());
        docTests.add(new MissingParamTagTest());
        docTests.add(new MissingPeriodInFirstSentenceTest());
        docTests.add(new ReturnsTypoTest());
        docTests.add(new MultipleVersionTagsTest());
    }

    /**
     * Starts processing of a Java source file with JavaDoc.
     * Method is called by the JavaDoc executable to allow this {@link Doclet}
     * to process the information in the tree.
     *
     * @param root {@link RootDoc} representation of the Java file to process
     * @return true if processing succeeded
     */
    public static boolean start(RootDoc root) {
        ClassDoc[] classes = root.classes();
        readOptions(root.options());
        if (!customOnly) addStandardTests();
        try {
            // aggregate the reports
            List<ITestReport> reports = new ArrayList<ITestReport>();
            for (int i = 0; i < classes.length; ++i) {
                ClassDoc classDoc = classes[i];
                for (IClassDocTester test : docTests) {
                    reports.addAll(test.test(classDoc));
                }
            }
            // sort them
            Collections.sort(reports, new AlphaNumericalSorter());

            // create the report
            OutputStream out = outputFile != null
                ? new FileOutputStream(new File(outputFile))
                : System.out;
            generator.startReport(out);
            for (ITestReport report : reports) {
                generator.report(report);
            }
            generator.endReport();
        } catch (IOException exception) {
            System.out.println(
                "Error while writing output: " + exception.getMessage()
            );
        }
        return true;
    }

    private static void readOptions(String[][] options) {
        for (int i = 0; i < options.length; i++) {
            String[] option = options[i];
            if ("-file".equals(option[0])) {
                outputFile = option[1];
            } else if ("-customonly".equals(option[0])) {
                customOnly = true;
            } else if ("-xhtml".equals(option[0])) {
                generator = new XHTMLGenerator();
            } else if ("-tests".equals(option[0])) {
                String[] tests = option[1].contains(",")
                    ? option[1].split(",")
                    : new String[]{option[1]};
                for (String test : tests) {
                    try {
                        Class<?> clazz = OpenJavaDocCheck.class.getClassLoader().
                            loadClass(test);
                        if (IClassDocTester.class.isAssignableFrom(clazz)) {
                            Object clazzInstance = clazz.newInstance();
                            docTests.add((IClassDocTester)clazzInstance);
                        } else {
                            System.out.println(
                                "Class does not implement IClassDocTester: " +
                                test
                            );
                        }
                    } catch (ClassNotFoundException e) {
                        System.out.println("Could not load class: " + test);
                    } catch (InstantiationException e) {
                        System.out.println("Could not instantiate class: " + test);
                    } catch (IllegalAccessException e) {
                        System.out.println("Could not access class: " + test);
                    }
                }
            }
        }
    }

    /**
     * Returns the length of each {@link Doclet} option. The length includes
     * the option itself: if an option takes a second value, the length is
     * 2; the minimum length for a registered option is 1. It returns 0 for
     * unsupported options.
     *
     * @param option the option for which the length is returned
     * @return the length of the option, or 0 if the option is not supported
     */
    public static int optionLength(String option) {
        if ("-file".equals(option)) {
            return 2;
        } else if ("-tests".equals(option)) {
            return 2;
        } else if ("-xhtml".equals(option)) {
            return 1;
        } else if ("-xml".equals(option)) {
            return 1;
        } else if ("-customonly".equals(option)) {
            return 1;
        }
        return 0;
    }
    
    /**
     * Validates the passed options. Each row in the matrix is one option, and
     * the first column is the option, while the other columns contain option
     * parameters.
     *
     * @param options options to validate
     * @param reporter {@link DocErrorReporter} where errors are reported 
     * @return false if errors were found
     */
    public static boolean validOptions(String options[][], 
            DocErrorReporter reporter) {
        for (int i = 0; i < options.length; i++) {
            String[] option = options[i];
//            System.out.println("Tag found: " + option[0]);
        }
        return true;
    }

}
