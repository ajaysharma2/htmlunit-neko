/*
 * Copyright 2002-2009 Andy Clark, Marc Guillemot
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
package org.htmlunit.cyberneko.html.dom;

import org.w3c.dom.html.HTMLHtmlElement;

/**
 * @author <a href="mailto:arkin@exoffice.com">Assaf Arkin</a>
 * @see org.w3c.dom.html.HTMLHtmlElement
 * @see org.htmlunit.cyberneko.xerces.dom.ElementImpl
 */
public class HTMLHtmlElementImpl extends HTMLElementImpl implements HTMLHtmlElement {

    @Override
    public String getVersion() {
        return capitalize(getAttribute("version"));
    }

    @Override
    public void setVersion(final String version) {
        setAttribute("version", version);
    }

    /**
     * Constructor requires owner document.
     *
     * @param owner The owner HTML document
     */
    public HTMLHtmlElementImpl(final HTMLDocumentImpl owner, final String name) {
        super(owner, name);
    }
}
