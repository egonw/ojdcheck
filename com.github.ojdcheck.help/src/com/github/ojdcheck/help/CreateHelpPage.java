/* Copyright (c) 2010  Egon Willighagen <egonw@users.sf.net>
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
package com.github.ojdcheck.help;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.github.ojdcheck.test.IClassDocTester;
import com.github.ojdcheck.test.content.MissingExceptionDescriptionTest;
import com.github.ojdcheck.test.content.MissingPeriodInFirstSentenceTest;
import com.github.ojdcheck.test.missing.MissingDescriptionTest;
import com.github.ojdcheck.test.missing.MissingParamTagTest;
import com.github.ojdcheck.test.missing.MissingReturnTagTest;
import com.github.ojdcheck.test.mistake.MultipleVersionTagsTest;
import com.github.ojdcheck.test.mistake.ReturnsTypoTest;
import com.github.ojdcheck.test.template.ExceptionTemplateTest;
import com.github.ojdcheck.test.template.ParameterTemplateTest;

/**
 * Helper application that creates an XHTML page summarizing the OpenJavaDocCheck
 * tests.
 */
public class CreateHelpPage {
    
    public void createPage(String filename) throws IOException {
        PrintWriter printer = new PrintWriter(
            new FileWriter(filename)
        );
        printer.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"");
        printer.println("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
        printer.println("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">");
        printer.println("<head>");
        printer.println("  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>");
        printer.println("  <title>OpenJavaDocCheck Tests</title>");
        printer.println("  <style type=\"text/css\">");
        printer.println("    body {");
        printer.println("      margin-top: 1.0em;");
        printer.println("      background-color: #ffffff;");
        printer.println("      font-family: \"Helvetica,Arial,FreeSans\";");
        printer.println("      color: #000000;");
        printer.println("    }");
        printer.println("    #container {");
        printer.println("      margin: 0 auto;");
        printer.println("      width: 700px;");
        printer.println("    }");
        printer.println("    h1 { font-size: 3.8em; color: #3426ad; margin-bottom: 3px; }");
        printer.println("    h1 .small { font-size: 0.4em; }");
        printer.println("    h1 a { text-decoration: none }");
        printer.println("    h2 { font-size: 1.5em; color: #3426ad; }");
        printer.println("    h3 { text-align: center; color: #3426ad; }");
        printer.println("    a { color: #3426ad; }");
        printer.println("    .description { font-size: 1.2em; margin-bottom: 30px; margin-top: 30px; font-style: italic;}");
        printer.println("    .download { float: right; }");
        printer.println("    pre { background: #000; color: #fff; padding: 15px;}");
        printer.println("    hr { border: 0; width: 80%; border-bottom: 1px solid #aaa}");
        printer.println("    .footer { text-align:center; padding-top:30px; font-style: italic; }");
        printer.println("  </style>");
        printer.println("</head>");
        printer.println("<body>");
        printer.println("  <a href=\"http://github.com/egonw/ojdcheck\"><img style=\"position: absolute; top: 0; right: 0; border: 0;\" src=\"http://s3.amazonaws.com/github/ribbons/forkme_right_darkblue_121621.png\" alt=\"Fork me on GitHub\" /></a>");
        printer.println("  <div id=\"container\">");
        printer.println("  <h1><a href=\"http://github.com/egonw/ojdcheck\">OpenJavaDocCheck</a></h1>");
        
        List<IClassDocTester> docTests = new ArrayList<IClassDocTester>();
        docTests.add(new MissingDescriptionTest());
        docTests.add(new ExceptionTemplateTest());
        docTests.add(new ParameterTemplateTest());
        docTests.add(new MissingExceptionDescriptionTest());
        docTests.add(new MissingReturnTagTest());
        docTests.add(new MissingParamTagTest());
        docTests.add(new MissingPeriodInFirstSentenceTest());
        docTests.add(new ReturnsTypoTest());
        docTests.add(new MultipleVersionTagsTest());
        
        for (IClassDocTester test : docTests) {
            String className = test.getClass().getName();
            String testName = className.substring(className.lastIndexOf('.')+1);
            printer.println("  <h2><a name=\"" + testName + "\">" + test.getName()+ "</a></h2>");
            printer.println("  <p>Priority: ");
            printer.println(test.getPriority());
            printer.println("  </p>");
            printer.println("  <p>");
            printer.println(test.getDescription());
            printer.println("  </p>");
//            printer.println("  <p>");
//            printer.println("    <code>");
//            printer.println("      /**");
//            printer.println("       * Minimal JavaDoc.");
//            printer.println("       *");
//            printer.println("       * @exception An exception is thrown if ...");
//            printer.println("       */");
//            printer.println("      public void method() throws Exception {}");
//            printer.println("    </code>");
//            printer.println("  </p>");
        }

        printer.println("  <div class=\"footer\">");
        printer.println("    get the source code on GitHub : <a href=\"http://github.com/egonw/ojdcheck\">egonw/ojdcheck</a>");
        printer.println("  </div>");
        printer.println("  </div>");
        printer.println("</body>");
        printer.println("</html>");
        printer.close();
    }
    
    public static void main(String[] args) throws IOException {
        String filename = args[0];
        CreateHelpPage helper = new CreateHelpPage();
        helper.createPage(filename);
    }

}
