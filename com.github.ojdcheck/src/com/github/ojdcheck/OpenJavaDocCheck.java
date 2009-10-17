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

import java.util.ArrayList;
import java.util.List;

import com.github.ojdcheck.test.ClassDescriptionTest;
import com.github.ojdcheck.test.FooMethodTest;
import com.github.ojdcheck.test.IClassDocTester;
import com.github.ojdcheck.test.ITestReport;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.RootDoc;

/**
 * Doclet that checks the correctness of JavaDoc.
 */
public class OpenJavaDocCheck extends Doclet {

    private static List<IClassDocTester> tests =
        new ArrayList<IClassDocTester>() {
            private static final long serialVersionUID = -4265911211271890560L;
            {
              add(new FooMethodTest());
              add(new ClassDescriptionTest());
            }
        };

    public static boolean start(RootDoc root) {
        ClassDoc[] classes = root.classes();
        System.out.println("<report>");
        for (int i = 0; i < classes.length; ++i) {
            ClassDoc classDoc = classes[i];
            for (IClassDocTester test : tests) {
                List<ITestReport> reports = test.test(classDoc);
                for (ITestReport report : reports) {
                    System.out.println(
                        "  <test class=\"" + classDoc.getClass().getName() +
                        "\">"
                    );
                    System.out.println("    <fail>" + report.getFailMessage() +
                            "</fail>");
                    System.out.println("  <test>");
                }
            }
        }
        System.out.println("</report>");
        return true;
    }

}
