/* Copyright (c) 2009-2010  Egon Willighagen <egonw@users.sf.net>
 *                    2010  Charles Shelton <charles.shelton@gmail.com>
 *
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
package com.github.ojdcheck.test.missing;

import java.util.ArrayList;
import java.util.List;

import com.github.ojdcheck.test.AbstractOJDCheckTest;
import com.github.ojdcheck.test.IClassDocTester;
import com.github.ojdcheck.test.ITestReport;
import com.github.ojdcheck.test.TestReport;
import com.github.ojdcheck.util.JavaDocHelper;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Tag;
import com.sun.javadoc.Type;

/**
 * Test that verifies that when a method does not return void, a <code>return</code>
 * tag is given.
 */
public class MissingReturnTagTest extends AbstractOJDCheckTest implements IClassDocTester {

    public String getDescription() {
        return "Checks whether a class that does not return void has a " +
        		"@return tag.";
    }

    public String getName() {
        return "Missing Return Tag";
    }

    public List<ITestReport> test(ClassDoc classDoc) {
        List<ITestReport> reports = new ArrayList<ITestReport>();
        MethodDoc[] methodDocs = classDoc.methods();
        for (MethodDoc methodDoc : methodDocs) {
         // do not fail if there is no JavaDoc
            if (!JavaDocHelper.hasJavaDoc(methodDoc)) continue;
            // do not check if we are inheriting JavaDoc
            if (JavaDocHelper.hasInheritedDoc(methodDoc)) continue;

            if (classDoc.isEnum() &&
                ("values".equals(methodDoc.name()) ||
                 "valueOf".equals(methodDoc.name()))
                ) {
                // screen out the valueOf()
                // method from Enum types since they are not overridden and
                // do not need javadoc comments.
                continue;
            }

            Type returnType = methodDoc.returnType();
            if (!returnType.typeName().equals("void")) {
                // method return type != void, so require a return tag
                Tag[] tags = methodDoc.tags();
                boolean returnFound = false;
                for (Tag tag : tags) {
                    if (tag.name().equals("@return")) returnFound = true;
                }
                if (!returnFound) {
                    reports.add(
                        new TestReport(
                            this, classDoc,
                            "Missing @return for the method " +
                            methodDoc.name() + ".",
                            methodDoc.position().line(),
                            null
                        )
                    );
                }
            }
        }
        return reports;
    }

    public Priority getPriority() {
        return Priority.ERROR;
    }

}