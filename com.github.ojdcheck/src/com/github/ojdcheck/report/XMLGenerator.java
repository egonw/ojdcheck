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
package com.github.ojdcheck.report;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.github.ojdcheck.test.ITestReport;

/**
 * Implementation of {@link IReportGenerator} that serializes the
 * reports into an XML format. This allows post-processing in
 * arbitrary other formats with XSLT stylesheets.
 */
public class XMLGenerator implements IReportGenerator {

    private final static String NEWLINE = System.getProperty("line.separator");
    
    private BufferedWriter writer;

    /**
     * Starts the creation of a report.
     */
    public void startReport(OutputStream output) throws IOException {
        // set up a BufferedWriter
        writer = new BufferedWriter(
            new OutputStreamWriter(output)
        );

        writer.write("<report>" + NEWLINE);
    }

    /**
     * Ends the creation of a report.
     */
    public void endReport() throws IOException {
        writer.write("</report>" + NEWLINE);
        writer.flush();
    }

    /**
     * Generates a report item for the given {@link ITestReport}.
     */
    public void report(ITestReport report) throws IOException {
        writer.write(
            "  <test class=\"" + report.getTestedClass().getName() + "\">" +
            NEWLINE
        );
        writer.write(
            "    <fail>" + report.getFailMessage() + "</fail>" + NEWLINE
        );
        writer.write("  </test>" + NEWLINE);
        writer.flush();
    }
}
