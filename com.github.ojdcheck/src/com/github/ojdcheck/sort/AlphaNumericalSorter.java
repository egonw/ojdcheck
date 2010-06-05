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
package com.github.ojdcheck.sort;

import java.util.Comparator;

import com.github.ojdcheck.test.ITestReport;

/**
 * Sorts reports first on class name, then on line number.
 */
public class AlphaNumericalSorter implements Comparator<ITestReport> {

    /**
     * Compares to {@link ITestReport}s and returns a negative number
     * if the first report should be listed earlier, zero if both
     * reports are equal, or positive if the second reports should be
     * listed first.
     *
     * @param  report1 the first report to compare
     * @param  report2 the second report to compare
     * @return         an negative, zero, or positive int
     */
    @Override
    public int compare(ITestReport report1, ITestReport report2) {
        // first, test the class name
        System.out.println("Class 1 name: " + report1.getTestedClass().qualifiedTypeName());
        System.out.println("Class 2 name: " + report2.getTestedClass().qualifiedTypeName());
        int nameComparison =
            report1.getTestedClass().qualifiedTypeName().compareTo(
                report2.getTestedClass().qualifiedTypeName()
            );
        if (nameComparison != 0) return nameComparison;

        // second, test the line number        
        return report1.getStartLine() - report2.getStartLine();
    }

}
