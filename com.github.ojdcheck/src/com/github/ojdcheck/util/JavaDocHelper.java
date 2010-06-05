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
package com.github.ojdcheck.util;

import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Tag;

/**
 * Some helper methods for analyzing JavaDoc documentation.
 */
public class JavaDocHelper {

    /**
     * Returns true of the {@link MethodDoc} has "@inheritDoc" annotation.
     *
     * @param  method method to check.
     * @return        true if the method
     */
    public static boolean hasInheritedDoc(MethodDoc methodDoc) {
        Tag[] tags = methodDoc.inlineTags();
        for (Tag tag : tags) {
            if ("@inheritDoc".equals(tag.name())) return true;
        }
        return false;
    }

    /**
     * Returns true of the {@link MethodDoc} has JavaDoc documentation.
     *
     * @param  method method to check.
     * @return        true if the JavaDoc is present, false if missing.
     */
    public static boolean hasJavaDoc(MethodDoc method) {
        String methodDoc = method.commentText();
        return (methodDoc != null && methodDoc.length() != 0);
    }

}
