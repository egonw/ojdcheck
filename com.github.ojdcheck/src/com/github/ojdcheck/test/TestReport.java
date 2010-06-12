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
package com.github.ojdcheck.test;

import com.github.ojdcheck.test.IClassDocTester.Priority;
import com.sun.javadoc.Type;

public class TestReport implements ITestReport {

    private Integer startLine = null;
    private Integer endLine = null;
    private IClassDocTester test = null;
    private String failMessage = "";
    private Type testedClass;

    public TestReport(IClassDocTester test, Type testedClass,
                      String failMessage, Integer startLine, Integer endLine){
        this.test = test;
        this.testedClass = testedClass;
        this.failMessage = failMessage;
        this.startLine = startLine;
        this.endLine = endLine;
    }

    public int getStartLine() {
        return startLine == null ? -1 : startLine;
    }

    public int getEndLine() {
        return endLine == null ? -1 : endLine;
    }

    public String getName() {
        return test.getName();
    }

    public String getDescription() {
        return test.getDescription();
    }

    public String getFailMessage() {
        return this.failMessage;
    }

    public Type getTestedClass() {
        return this.testedClass;
    }

    public Priority getPriority() {
        return test.getPriority();
    }

}
