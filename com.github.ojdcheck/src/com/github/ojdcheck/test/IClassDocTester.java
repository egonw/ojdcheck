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

import java.util.List;

import com.sun.javadoc.ClassDoc;

/**
 * Interface for testers that validate a certain aspect of the JavaDoc.
 */
public interface IClassDocTester {

    public enum Priority {
        ERROR,
        WARNING
    }

    /**
     * Tests the given {@link ClassDoc} for a certain JavaDoc error.
     *
     * @param classDoc the {@link ClassDoc} to test.
     * @return         a {@link List} with zero or more failures
     */
    public List<ITestReport> test(ClassDoc classDoc);

    /**
     * Returns the name of the type of fail.
     *
     * @return a {@link String} naming this type of fail. 
     */
    public String getName();

    /**
     * Returns a description on why the test failed.
     *
     * @return a {@link String} describing why the test failed. 
     */
    public String getDescription();

    /**
     * Returns the priority it the fail.
     *
     * @return a Priority of the fail. 
     */
    public Priority getPriority();
}
