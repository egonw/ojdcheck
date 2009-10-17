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

/**
 * Interface for a report of a failing {@link IClassDocTester} test.
 */
public interface ITestReport {

    /**
     * Returns the {@link Class} for which the test failed.
     *
     * @return the {@link Class} with the failing test 
     */
    public Class<?> getTestedClass();

    /**
     * Returns the line at which the fail starts.
     *
     * @return an <code>int</code> indicating the start line 
     */
    public int getStartLine();

    /**
     * Returns the line at which the fail ends.
     *
     * @return an <code>int</code> indicating the end line 
     */
    public int getEndLine();

    /**
     * Returns the name of the type of fail.
     *
     * @return a {@link String} naming this type of fail. 
     */
    public String getName();

    /**
     * Returns a description for this test.
     *
     * @return a {@link String} describing this test. 
     */
    public String getDescription();

    /**
     * Returns a message about why the test failed.
     *
     * @return a {@link String} describing why reason of fail. 
     */
    public String getFailMessage();

}
