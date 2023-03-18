/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.htmlunit.cyberneko.xerces.xni;

/**
 * This class is used as a structure to pass text contained in the underlying
 * character buffer of the scanner. The offset and length fields allow the
 * buffer to be re-used without creating new character arrays.
 * <p>
 * <strong>Note:</strong> Methods that are passed an XMLString structure should
 * consider the contents read-only and not make any modifications to the
 * contents of the buffer. The method receiving this structure should also not
 * modify the offset and length if this structure (or the values of this
 * structure) are passed to another method.
 * <p>
 * <strong>Note:</strong> Methods that are passed an XMLString structure are
 * required to copy the information out of the buffer if it is to be saved for
 * use beyond the scope of the method. The contents of the structure are
 * volatile and the contents of the character buffer cannot be assured once the
 * method that is passed this structure returns. Therefore, methods passed this
 * structure should not save any reference to the structure or the character
 * array contained in the structure.
 *
 * @author Eric Ye, IBM
 * @author Andy Clark, IBM
 */
public class XMLString {

    /** The character array. */
    public char[] ch;

    /** The offset into the character array. */
    public int offset;

    /** The length of characters from the offset. */
    public int length;

    /** Default constructor. */
    public XMLString() {
    }

    /**
     * Constructs an XMLString structure preset with the specified values.
     *
     * @param ch     The character array.
     * @param offset The offset into the character array.
     * @param length The length of characters from the offset.
     */
    public XMLString(char[] ch, int offset, int length) {
        setValues(ch, offset, length);
    }

    /**
     * Initializes the contents of the XMLString structure with the specified
     * values.
     *
     * @param ch     The character array.
     * @param offset The offset into the character array.
     * @param length The length of characters from the offset.
     */
    public void setValues(char[] ch, int offset, int length) {
        this.ch = ch;
        this.offset = offset;
        this.length = length;
    }

    /**
     * Initializes the contents of the XMLString structure with copies of the given
     * string structure.
     * <p>
     * <strong>Note:</strong> This does not copy the character array; only the
     * reference to the array is copied.
     *
     * @param s the xml string value
     */
    public void setValues(XMLString s) {
        setValues(s.ch, s.offset, s.length);
    }

    /** Resets all of the values to their defaults. */
    public void clear() {
        this.ch = null;
        this.offset = 0;
        this.length = -1;
    }

    /**
     * @return true if the contents of this XMLString structure and the specified
     *         array are equal.
     *
     * @param ch     The character array.
     * @param offset The offset into the character array.
     * @param length The length of characters from the offset.
     */
    public boolean equals(char[] ch, int offset, int length) {
        if ((ch == null) || (this.length != length)) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (this.ch[this.offset + i] != ch[offset + i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return true if the contents of this XMLString structure and the specified
     *         string are equal.
     *
     * @param s The string to compare.
     */
    public boolean equals(String s) {
        if ((s == null) || (length != s.length())) {
            return false;
        }

        // is this faster than call s.toCharArray first and compare the
        // two arrays directly, which will possibly involve creating a
        // new char array object.
        for (int i = 0; i < length; i++) {
            if (ch[offset + i] != s.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return length > 0 ? new String(ch, offset, length) : "";
    }
}