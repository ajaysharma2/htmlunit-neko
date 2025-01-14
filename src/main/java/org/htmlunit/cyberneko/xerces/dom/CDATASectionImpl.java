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

import org.w3c.dom.CDATASection;
import org.w3c.dom.Node;

/**
 * XML provides the CDATA markup to allow a region of text in which most of the
 * XML delimiter recognition does not take place. This is intended to ease the
 * task of quoting XML fragments and other programmatic information in a
 * document's text without needing to escape these special characters. It's
 * primarily a convenience feature for those who are hand-editing XML.
 * <P>
 * CDATASection is an Extended DOM feature, and is not used in HTML contexts.
 * <P>
 * Within the DOM, CDATASections are treated essentially as Text blocks. Their
 * distinct type is retained in order to allow us to properly recreate the XML
 * syntax when we write them out.
 * <P>
 * Reminder: CDATA IS NOT A COMPLETELY GENERAL SOLUTION; it can't quote its own
 * end-of-block marking. If you need to write out a CDATA that contains the
 * ]]&gt; sequence, it's your responsibility to split that string over two
 * successive CDATAs at that time.
 * <P>
 * CDATA does not participate in Element.normalize() processing.
 */
public class CDATASectionImpl extends TextImpl implements CDATASection {

    /**
     * Factory constructor for creating a CDATA section.
     *
     * @param ownerDoc the owner document
     * @param data     the data
     */
    public CDATASectionImpl(final CoreDocumentImpl ownerDoc, final String data) {
        super(ownerDoc, data);
    }

    /**
     * {@inheritDoc}
     *
     * A short integer indicating what type of node this is. The named constants for
     * this value are defined in the org.w3c.dom.Node interface.
     */
    @Override
    public short getNodeType() {
        return Node.CDATA_SECTION_NODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNodeName() {
        return "#cdata-section";
    }
}
