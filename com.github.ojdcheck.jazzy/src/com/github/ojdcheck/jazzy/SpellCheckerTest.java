/* Copyright (c) 2010  Egon Willighagen <egonw@users.sf.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package com.github.ojdcheck.jazzy;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.ojdcheck.test.IClassDocTester;
import com.github.ojdcheck.test.ITestReport;
import com.github.ojdcheck.test.TestReport;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.SpellCheckEvent;
import com.swabunga.spell.event.SpellCheckListener;
import com.swabunga.spell.event.SpellChecker;
import com.swabunga.spell.event.StringWordTokenizer;

/**
 * Doclet that uses Jazzy to spell check the JavaDoc.
 */
public class SpellCheckerTest implements IClassDocTester {

    private SpellChecker spellChecker;

    String[] dictionaries = {
        // dictionaries that come with Jazzy
        "com/github/ojdcheck/jazzy/dict/eng_com.dic",
        "com/github/ojdcheck/jazzy/dict/center.dic",
        "com/github/ojdcheck/jazzy/dict/color.dic",
        "com/github/ojdcheck/jazzy/dict/ize.dic",
        "com/github/ojdcheck/jazzy/dict/labeled.dic",
        "com/github/ojdcheck/jazzy/dict/yze.dic",
        // custom dictionaries
        "com/github/ojdcheck/jazzy/dict/java.dic"
    };
    
    public SpellCheckerTest() throws Exception {
        spellChecker = new SpellChecker();
        for (String dictionaryResource : dictionaries) {
            SpellDictionaryHashMap dictionary = new SpellDictionaryHashMap(
                new InputStreamReader(
                    this.getClass().getClassLoader().getResourceAsStream(dictionaryResource)
                )
            );
            spellChecker.addDictionary(dictionary);
        }
    }
    
    @Override
    public String getDescription() {
        return "Spell checks the JavaDoc.";
    }

    @Override
    public String getName() {
        return "SpellChecker";
    }

    @Override
    public Priority getPriority() {
        return Priority.MINOR_ERROR;
    }

    @Override
    public List<ITestReport> test(ClassDoc classDoc) {
        List<ITestReport> reports = new ArrayList<ITestReport>();

        // set up the spell checker
        SuggestionListener listener = new SuggestionListener(this, classDoc);
        spellChecker.addSpellCheckListener(listener);
        
        // spell check the JavaDoc
        spellChecker.checkSpelling(new StringWordTokenizer(classDoc.commentText()));
        MethodDoc[] methods = classDoc.methods();
        for (MethodDoc method : methods) {
            String methodDoc = method.commentText();
            if (methodDoc == null || methodDoc.length() == 0) {
                listener.setMethodDoc(method);
                spellChecker.checkSpelling(new StringWordTokenizer(methodDoc));
            }
        }
        reports.addAll(listener.getReports());

        return reports;
    }

    /**
     * Spell checks part of the JavaDoc. If the methodDoc field is null, it assumed it is the class
     * documentation.
     *
     * @author egonw
     */
    public static class SuggestionListener implements SpellCheckListener {

        private List<ITestReport> reports;
        private IClassDocTester test;
        private ClassDoc classDoc;
        private MethodDoc methodDoc;

        public SuggestionListener(IClassDocTester test, ClassDoc classDoc) {
            this.test = test;
            this.classDoc = classDoc;
            this.methodDoc = null;
            reports  = new ArrayList<ITestReport>();
        }

        public List<ITestReport> getReports() {
            return reports;
        }

        public void setMethodDoc(MethodDoc methodDoc) {
            this.methodDoc = methodDoc;
        }
        
        public void spellingError(SpellCheckEvent event) {
            // skip single character words: 'a' etc
            if (event.getInvalidWord().length() == 1) return;

            List suggestions = event.getSuggestions();
            if (suggestions.isEmpty()) {
                reports.add(
                    new TestReport(
                        test, classDoc,
                        "Incorrect spelling: " + event.getInvalidWord(),
                        (methodDoc == null) ?
                           classDoc.position().line() :
                           methodDoc.position().line(),
                        null
                    )
                );
            } else {
                StringBuffer suggestionsString = new StringBuffer();
                for (Iterator i = suggestions.iterator(); i.hasNext();) {
                    suggestionsString.append(i.next());
                    if (i.hasNext()) suggestionsString.append(", ");
                }
                reports.add(
                    new TestReport(
                        test, classDoc,
                        "Incorrect spelling: " + event.getInvalidWord() + ". Did you mean one of: " +
                        suggestionsString + "?",
                        (methodDoc == null) ?
                          classDoc.position().line() :
                          methodDoc.position().line(),
                        null
                    )
                );
            }
        }

      }

}
