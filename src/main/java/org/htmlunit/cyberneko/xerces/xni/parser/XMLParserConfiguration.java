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
package org.htmlunit.cyberneko.xerces.xni.parser;

import java.io.IOException;

import org.htmlunit.cyberneko.xerces.xni.XMLDocumentHandler;
import org.htmlunit.cyberneko.xerces.xni.XNIException;

/**
 * Represents a parser configuration. The parser configuration maintains a table
 * of recognized features and properties, assembles components for the parsing
 * pipeline, and is responsible for initiating parsing of an XML document.
 * <p>
 * By separating the configuration of a parser from the specific parser
 * instance, applications can create new configurations and re-use the existing
 * parser components and external API generators (e.g. the DOMParser and
 * SAXParser).
 * <p>
 * The internals of any specific parser configuration instance are hidden.
 * Therefore, each configuration may implement the parsing mechanism any way
 * necessary. However, the parser configuration should follow these guidelines:
 * <ul>
 * <li>Call the <code>reset</code> method on each component before parsing. This
 * is only required if the configuration is re-using existing components that
 * conform to the <code>XMLComponent</code> interface. If the configuration uses
 * all custom parts, then it is free to implement everything as it sees fit as
 * long as it follows the other guidelines.</li>
 * <li>Call the <code>setFeature</code> and <code>setProperty</code> method on
 * each component during parsing to propagate features and properties that have
 * changed. This is only required if the configuration is re-using existing
 * components that conform to the <code>XMLComponent</code> interface. If the
 * configuration uses all custom parts, then it is free to implement everything
 * as it sees fit as long as it follows the other guidelines.</li>
 * <li>Pass the same unique String references for all symbols that are
 * propagated to the registered handlers. Symbols include, but may not be
 * limited to, the names of elements and attributes (including their uri,
 * prefix, and localpart). This is suggested but not an absolute must. However,
 * the standard parser components may require access to the same symbol table
 * for creation of unique symbol references to be propagated in the XNI
 * pipeline.</li>
 * </ul>
 *
 * @author Arnaud Le Hors, IBM
 * @author Andy Clark, IBM
 */
public interface XMLParserConfiguration extends XMLComponentManager {

    /**
     * Parse an XML document.
     * <p>
     * The parser can use this method to instruct this configuration to begin
     * parsing an XML document from any valid input source (a character stream, a
     * byte stream, or a URI).
     * <p>
     * Parsers may not invoke this method while a parse is in progress. Once a parse
     * is complete, the parser may then parse another XML document.
     * <p>
     * This method is synchronous: it will not return until parsing has ended. If a
     * client application wants to terminate parsing early, it should throw an
     * exception.
     * <p>
     * When this method returns, all characters streams and byte streams opened by
     * the parser are closed.
     *
     * @param inputSource The input source for the top-level of the XML document.
     *
     * @exception XNIException Any XNI exception, possibly wrapping another
     *                         exception.
     * @exception IOException  An IO exception from the parser, possibly from a byte
     *                         stream or character stream supplied by the parser.
     */
    void parse(XMLInputSource inputSource) throws XNIException, IOException;

    /**
     * Allows a parser to add parser specific features to be recognized and managed
     * by the parser configuration.
     *
     * @param featureIds An array of the additional feature identifiers to be
     *                   recognized.
     */
    void addRecognizedFeatures(String[] featureIds);

    /**
     * Sets the state of a feature. This method is called by the parser and gets
     * propagated to components in this parser configuration.
     *
     * @param featureId The feature identifier.
     * @param state     The state of the feature.
     *
     * @throws XMLConfigurationException Thrown if there is a configuration error.
     */
    void setFeature(String featureId, boolean state) throws XMLConfigurationException;

    /**
     * @param featureId The feature identifier.
     * @return the state of a feature.
     *
     * @throws XMLConfigurationException Thrown if there is a configuration error.
     */
    @Override
    boolean getFeature(String featureId) throws XMLConfigurationException;

    /**
     * Allows a parser to add parser specific properties to be recognized and
     * managed by the parser configuration.
     *
     * @param propertyIds An array of the additional property identifiers to be
     *                    recognized.
     */
    void addRecognizedProperties(String[] propertyIds);

    /**
     * Sets the value of a property. This method is called by the parser and gets
     * propagated to components in this parser configuration.
     *
     * @param propertyId The property identifier.
     * @param value      The value of the property.
     *
     * @throws XMLConfigurationException Thrown if there is a configuration error.
     */
    void setProperty(String propertyId, Object value) throws XMLConfigurationException;

    /**
     * @param propertyId The property identifier.
     * @return the value of a property.
     *
     * @throws XMLConfigurationException Thrown if there is a configuration error.
     */
    @Override
    Object getProperty(String propertyId) throws XMLConfigurationException;

    // handlers

    /**
     * Sets the error handler.
     *
     * @param errorHandler The error resolver.
     */
    void setErrorHandler(XMLErrorHandler errorHandler);

    /**
     * @return the registered error handler.
     */
    XMLErrorHandler getErrorHandler();

    /**
     * Sets the document handler to receive information about the document.
     *
     * @param documentHandler The document handler.
     */
    void setDocumentHandler(XMLDocumentHandler documentHandler);

    /**
     * @return the registered document handler.
     */
    XMLDocumentHandler getDocumentHandler();

    /**
     * Sets the input source for the document to parse.
     *
     * @param inputSource The document's input source.
     *
     * @exception XMLConfigurationException Thrown if there is a configuration error
     *                                      when initializing the parser.
     * @exception IOException               Thrown on I/O error.
     *
     * @see #parse(boolean)
     */
    void setInputSource(XMLInputSource inputSource) throws XMLConfigurationException, IOException;

    /**
     * Parses the document in a pull parsing fashion.
     *
     * @param complete True if the pull parser should parse the remaining document
     *                 completely.
     *
     * @return True if there is more document to parse.
     *
     * @exception XNIException Any XNI exception, possibly wrapping another
     *                         exception.
     * @exception IOException  An IO exception from the parser, possibly from a byte
     *                         stream or character stream supplied by the parser.
     *
     * @see #setInputSource
     */
    boolean parse(boolean complete) throws XNIException, IOException;

    /**
     * If the application decides to terminate parsing before the xml document is
     * fully parsed, the application should call this method to free any resource
     * allocated during parsing. For example, close all opened streams.
     */
    void cleanup();
}
