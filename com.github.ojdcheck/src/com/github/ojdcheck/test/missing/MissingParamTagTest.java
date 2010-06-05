/* Copyright (c) 2009  Egon Willighagen <egonw@users.sf.net>
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.ojdcheck.test.IClassDocTester;
import com.github.ojdcheck.test.ITestReport;
import com.github.ojdcheck.test.TestReport;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.Tag;

/**
 * Test that verifies that when a methods has parameters, the matching
 * param tag entries are given.
 */
public class MissingParamTagTest implements IClassDocTester {

    @Override
    public String getDescription() {
        return "Checks whether a method has @param tags for all " +
        		"parameters.";
    }

    @Override
    public String getName() {
        return "Missing Param Tag";
    }

    
    @Override
    public List<ITestReport> test(ClassDoc classDoc) {
        List<ITestReport> reports = new ArrayList<ITestReport>();
        MethodDoc[] methodDocs = classDoc.methods();
        for (MethodDoc methodDoc : methodDocs) {
            // do not fail if there is no JavaDoc
            if (!hasJavaDoc(methodDoc)) continue;
            // do not check if we are inheriting JavaDoc
            if (hasInheritedDoc(methodDoc)) continue;

            Parameter[] params = methodDoc.parameters();
            Map<String,ParamTag> foundParams = new HashMap<String,ParamTag>();
            ParamTag[] paramTags = methodDoc.paramTags();
            for (ParamTag tag : paramTags) {
                foundParams.put(tag.parameterName(), tag);
            }
            for (Parameter param : params) {
                ParamTag tag = foundParams.get(param.name());
                if (tag == null) {
                    reports.add(
                        new TestReport(
                            this, classDoc,
                            "Missing @param tag for the " + methodDoc.name() + 
                            " method's " + param.name() + " parameter.",
                            methodDoc.position().line(),
                            null
                        )
                    );
                }
            }
        }
        return reports;
    }

    private boolean hasInheritedDoc(MethodDoc methodDoc) {
        Tag[] tags = methodDoc.inlineTags();
        for (Tag tag : tags) {
            if ("@inheritDoc".equals(tag.name())) return true;
        }
        return false;
    }

    /**
     * Do not fail on @param if there is no JavaDoc given at all. The test
     * {@link MissingDescriptionTest} will fail then.
     */
    private boolean hasJavaDoc(MethodDoc method) {
        String methodDoc = method.commentText();
        return (methodDoc != null && methodDoc.length() != 0);
    }

    @Override
    public Priority getPriority() {
        return Priority.ERROR;
    }

}