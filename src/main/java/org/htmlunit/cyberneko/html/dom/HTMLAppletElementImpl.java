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

import org.w3c.dom.html.HTMLAppletElement;
/**
 * @author <a href="mailto:arkin@exoffice.com">Assaf Arkin</a>
 * @see org.w3c.dom.html.HTMLAppletElement
 * @see HTMLElementImpl
 */
public class HTMLAppletElementImpl extends HTMLElementImpl implements HTMLAppletElement {
    @Override
    public String getAlign() {
        return getAttribute("align");
    }

    @Override
    public void setAlign(final String align) {
        setAttribute("align", align);
    }

    @Override
    public String getAlt() {
        return getAttribute("alt");
    }

    @Override
    public void setAlt(final String alt) {
        setAttribute("alt", alt);
    }

    @Override
    public String getArchive() {
        return getAttribute("archive");
    }

    @Override
    public void setArchive(final String archive) {
        setAttribute("archive", archive);
    }

    @Override
    public String getCode() {
        return getAttribute("code");
    }

    @Override
    public void setCode(final String code) {
        setAttribute("code", code);
    }

    @Override
    public String getCodeBase() {
        return getAttribute("codebase");
    }

    @Override
    public void setCodeBase(final String codeBase) {
        setAttribute("codebase", codeBase);
    }

    @Override
    public String getHeight() {
        return getAttribute("height");
    }

    @Override
    public void setHeight(final String height) {
        setAttribute("height", height);
    }

    @Override
    public String getHspace() {
        return getAttribute("height");
    }

    @Override
    public void setHspace(final String height) {
        setAttribute("height", height);
    }

    @Override
    public String getName() {
        return getAttribute("name");
    }

    @Override
    public void setName(final String name) {
        setAttribute("name", name);
    }

    @Override
    public String getObject() {
        return getAttribute("object");
    }

    @Override
    public void setObject(final String object) {
        setAttribute("object", object);
    }

    @Override
    public String getVspace() {
        return getAttribute("vspace");
    }

    @Override
    public void setVspace(final String vspace) {
        setAttribute("vspace", vspace);
    }

    @Override
    public String getWidth() {
        return getAttribute("width");
    }

    @Override
    public void setWidth(final String width) {
        setAttribute("width", width);
    }

    /**
     * Constructor requires owner document.
     *
     * @param owner The owner HTML document
     */
    public HTMLAppletElementImpl(final HTMLDocumentImpl owner, final String name) {
        super(owner, name);
    }
}
