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
import java.util.HashMap;
import java.util.Map;

import com.github.ojdcheck.test.ITestReport;
import com.github.ojdcheck.test.IClassDocTester.Priority;

/**
 * Implementation of {@link IReportGenerator} that serializes the
 * reports into an XHTML format.
 */
public class XHTMLGenerator implements IReportGenerator {

    private final static String NEWLINE = System.getProperty("line.separator");
    
    private BufferedWriter writer;

    private int lineCounter = 0;
    
    private Map<Priority,Integer> counters;
    
    /**
     * Starts the creation of a report.
     */
    public void startReport(OutputStream output) throws IOException {
        // set up a BufferedWriter
        writer = new BufferedWriter(
            new OutputStreamWriter(output)
        );
        lineCounter = 0;
        counters = new HashMap<Priority, Integer>();
        for (Priority prior : Priority.values()) counters.put(prior, 0);

        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + NEWLINE);
        writer.write(
            "<!DOCTYPE html PUBLIC " + NEWLINE +
            "  \"-//W3C//DTD XHTML+RDFa 1.0//EN\"" + NEWLINE +
            "  \"http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd\">" + NEWLINE
        );
        writer.write(
             "<html xmlns=\"http://www.w3.org/1999/xhtml\" " + 
             "version=\"XHTML+RDFa 1.0\"" + NEWLINE +
             "xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"" + NEWLINE +
             "xmlns:ojdc=\"http://ojdcheck.github.com/ojdc/#\"" + NEWLINE +
             "xmlns:java=\"http://ojdcheck.github.com/java/#\"" + NEWLINE +
             "xmlns:dc=\"http://purl.org/dc/elements/1.1/\">" +
             NEWLINE
        );
        writer.write("<head>" + NEWLINE);
        writer.write(" <title>OpenJavaDocCheck Report</title>" + NEWLINE);
        writer.write(" <style type=\"text/css\">" + NEWLINE);
        writer.write("   tr.oddRow {" + NEWLINE);
        writer.write("     background-color: silver" + NEWLINE);
        writer.write("   }" + NEWLINE);
        writer.write("   tr.evenRow {" + NEWLINE);
        writer.write("     background-color: white" + NEWLINE);
        writer.write("   }" + NEWLINE);
        writer.write("   td.error {" + NEWLINE);
        writer.write("     background-color: red" + NEWLINE);
        writer.write("   }" + NEWLINE);
        writer.write("   td.minor_error {" + NEWLINE);
        writer.write("     background-color: orange" + NEWLINE);
        writer.write("   }" + NEWLINE);
        writer.write("   td.warning {" + NEWLINE);
        writer.write("     background-color: green" + NEWLINE);
        writer.write("   }" + NEWLINE);
        writer.write(" </style>" + NEWLINE);
        writer.write("</head>" + NEWLINE);
        writer.write("<body><table>" + NEWLINE);
        writer.write(" <tr>" + NEWLINE);
        writer.write("   <td></td>" + NEWLINE);
        writer.write("   <td><b>Class</b></td>" + NEWLINE);
        writer.write("   <td><b>Line</b></td>" + NEWLINE);
        writer.write("   <td><b>Error</b></td>" + NEWLINE);
        writer.write(" </tr>" + NEWLINE);
    }

    /**
     * Ends the creation of a report.
     */
    public void endReport() throws IOException {
        writer.write("</table>" + NEWLINE);
        writer.write("<p>Summary: " + NEWLINE);
        for (Priority prior : counters.keySet()) {
            writer.write(prior + ": <span class=\"" + prior + "\">" +
                counters.get(prior) + "</span>" + NEWLINE);
        }
        writer.write("</p>" + NEWLINE);
        writer.write("<p>This report was generated with <a href=\"http://github.com/egonw/ojdcheck/\"" +
        	">OpenJavaDocCheck</a>.</p>" + NEWLINE);
        writer.write("</body>" + NEWLINE);
        writer.write("</html>" + NEWLINE);
        writer.flush();
    }

    /**
     * Generates a report item for the given {@link ITestReport}.
     */
    public void report(ITestReport report) throws IOException {
        lineCounter++;
        if (lineCounter > 1) return;
        
        Priority prior = report.getPriority();
        counters.put(prior, counters.get(prior) + 1);
        writer.write("  <tr about=\"#report" + lineCounter + "\" typeof=" +
        	"\"ojdc:Violation\" class=\"violation " + (
            lineCounter % 2 == 0
                  ? "evenRow"
                  : "oddRow") +
            "\">" + NEWLINE);

        // check if we need to add a hyperlink
        String failMessage = "";
        if (report.getURL() != null) {
            failMessage += "<a rel=\"ojdc:type\" " +
            	"property=\"ojdc:hasMessage\" href=\"" + report.getURL() + "\">";
        }
        failMessage += report.getFailMessage();
        if (report.getURL() != null)
            failMessage += "</a>";     

        writer.write("    <td class=\"" + prior.name().toLowerCase() + "\">" +
            (prior.ordinal() + 1) + "</td>" + NEWLINE +
            "    <td rel=\"ojdc:inClass\"><span typeof=\"java:class\" about=\"#" +
            report.getTestedClass().qualifiedTypeName() + "\" property=\"dc:title\">" + 
            report.getTestedClass().qualifiedTypeName() + "</span></td>" +
            NEWLINE + "    <td property=\"ojdc:startLine\">" + report.getStartLine() +
            "</td>" + NEWLINE +
            "    <td>" + failMessage + "</td>" + NEWLINE
        );
        writer.write("  </tr>" + NEWLINE);
        writer.flush();
    }

}
