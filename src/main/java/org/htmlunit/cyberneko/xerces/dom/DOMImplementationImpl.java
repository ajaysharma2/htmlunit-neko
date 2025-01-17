/*
 * Copyright 2017-2023 Ronald Brill
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.htmlunit.cyberneko.xerces.dom;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentType;

/**
 * The DOMImplementation class is description of a particular implementation of
 * the Document Object Model. As such its data is static, shared by all
 * instances of this implementation.
 * <P>
 * The DOM API requires that it be a real object rather than static methods.
 * However, there's nothing that says it can't be a singleton, so that's how
 * I've implemented it.
 */
public class DOMImplementationImpl extends CoreDOMImplementationImpl {

    /** Dom implementation singleton. */
    private static final DOMImplementationImpl singleton = new DOMImplementationImpl();

    // NON-DOM: Obtain and return the single shared object
    public static DOMImplementation getDOMImplementation() {
        return singleton;
    }

    /**
     * {@inheritDoc}
     *
     * Test if the DOM implementation supports a specific "feature" -- currently
     * meaning language and level thereof.
     *
     * @param feature The package name of the feature to test. In Level 1, supported
     *                values are "HTML" and "XML" (case-insensitive). At this
     *                writing, org.htmlunit.cyberneko.xerces.dom supports only
     *                XML.
     *
     * @param version The version number of the feature being tested. This is
     *                interpreted as "Version of the DOM API supported for the
     *                specified Feature", and in Level 1 should be "1.0"
     *
     * @return true iff this implementation is compatable with the specified feature
     *         and version.
     */
    @Override
    public boolean hasFeature(String feature, final String version) {

        final boolean result = super.hasFeature(feature, version);
        if (!result) {
            final boolean anyVersion = version == null || version.length() == 0;
            if (feature.startsWith("+")) {
                feature = feature.substring(1);
            }
            return ("Events".equalsIgnoreCase(feature) && (anyVersion || "2.0".equals(version)))
                    || ("MutationEvents".equalsIgnoreCase(feature) && (anyVersion || "2.0".equals(version)))
                    || ("Traversal".equalsIgnoreCase(feature) && (anyVersion || "2.0".equals(version)))
                    || ("Range".equalsIgnoreCase(feature) && (anyVersion || "2.0".equals(version)))
                    || ("MutationEvents".equalsIgnoreCase(feature) && (anyVersion || "2.0".equals(version)));
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CoreDocumentImpl createDocument(final DocumentType doctype) {
        return new DocumentImpl(doctype);
    }

}
