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

package net.sourceforge.htmlunit.xerces.parsers;

import net.sourceforge.htmlunit.xerces.impl.XMLDocumentScannerImpl;
import net.sourceforge.htmlunit.xerces.impl.XMLNSDocumentScannerImpl;
import net.sourceforge.htmlunit.xerces.impl.dtd.XMLDTDValidator;
import net.sourceforge.htmlunit.xerces.impl.dtd.XMLNSDTDValidator;
import net.sourceforge.htmlunit.xerces.util.SymbolTable;
import net.sourceforge.htmlunit.xerces.xni.grammars.XMLGrammarPool;
import net.sourceforge.htmlunit.xerces.xni.parser.XMLComponentManager;
import net.sourceforge.htmlunit.xerces.xni.parser.XMLDocumentScanner;

/**
 * This is configuration uses a scanner that integrates both scanning of the document
 * and binding namespaces.
 *
 * If namespace feature is turned on, the pipeline is constructured with the
 * following components:
 * XMLNSDocumentScannerImpl -> XMLNSDTDValidator -> (optional) XMLSchemaValidator
 *
 * If the namespace feature is turned off the default document scanner implementation
 * is used (XMLDocumentScannerImpl).
 * <p>
 * In addition to the features and properties recognized by the base
 * parser configuration, this class recognizes these additional
 * features and properties:
 * <ul>
 * <li>Features
 *  <ul>
 *  <li>http://apache.org/xml/features/validation/schema</li>
 *  <li>http://apache.org/xml/features/validation/schema-full-checking</li>
 *  <li>http://apache.org/xml/features/validation/schema/normalized-value</li>
 *  <li>http://apache.org/xml/features/validation/schema/element-default</li>
 *  </ul>
 * <li>Properties
 *  <ul>
 *   <li>http://apache.org/xml/properties/internal/error-reporter</li>
 *   <li>http://apache.org/xml/properties/internal/entity-manager</li>
 *   <li>http://apache.org/xml/properties/internal/document-scanner</li>
 *   <li>http://apache.org/xml/properties/internal/dtd-scanner</li>
 *   <li>http://apache.org/xml/properties/internal/grammar-pool</li>
 *   <li>http://apache.org/xml/properties/internal/validator/dtd</li>
 *   <li>http://apache.org/xml/properties/internal/datatype-validator-factory</li>
 *  </ul>
 * </ul>
 *
 * @author Elena Litani, IBM
 *
 * @version $Id$
 */
public class IntegratedParserConfiguration
extends StandardParserConfiguration {


    //
    // REVISIT: should this configuration depend on the others
    //          like DTD/Standard one?
    //

    /** Document scanner that does namespace binding. */
    protected XMLNSDocumentScannerImpl fNamespaceScanner;

    /** Default Xerces implementation of scanner */
    protected final XMLDocumentScannerImpl fNonNSScanner;

    /** DTD Validator that does not bind namespaces */
    protected final XMLDTDValidator fNonNSDTDValidator;

    //
    // Constructors
    //

    /** Default constructor. */
    public IntegratedParserConfiguration() {
        this(null, null, null);
    } // <init>()

    /**
     * Constructs a parser configuration using the specified symbol table.
     *
     * @param symbolTable The symbol table to use.
     */
    public IntegratedParserConfiguration(SymbolTable symbolTable) {
        this(symbolTable, null, null);
    } // <init>(SymbolTable)

    /**
     * Constructs a parser configuration using the specified symbol table and
     * grammar pool.
     * <p>
     * <strong>REVISIT:</strong>
     * Grammar pool will be updated when the new validation engine is
     * implemented.
     *
     * @param symbolTable The symbol table to use.
     * @param grammarPool The grammar pool to use.
     */
    public IntegratedParserConfiguration(SymbolTable symbolTable,
                                         XMLGrammarPool grammarPool) {
        this(symbolTable, grammarPool, null);
    } // <init>(SymbolTable,XMLGrammarPool)

    /**
     * Constructs a parser configuration using the specified symbol table,
     * grammar pool, and parent settings.
     * <p>
     * <strong>REVISIT:</strong>
     * Grammar pool will be updated when the new validation engine is
     * implemented.
     *
     * @param symbolTable    The symbol table to use.
     * @param grammarPool    The grammar pool to use.
     * @param parentSettings The parent settings.
     */
    public IntegratedParserConfiguration(SymbolTable symbolTable,
                                         XMLGrammarPool grammarPool,
                                         XMLComponentManager parentSettings) {
        super(symbolTable, grammarPool, parentSettings);

        // create components
        fNonNSScanner = new XMLDocumentScannerImpl();
        fNonNSDTDValidator = new XMLDTDValidator();

        // add components
        addComponent(fNonNSScanner);
        addComponent(fNonNSDTDValidator);

    } // <init>(SymbolTable,XMLGrammarPool)


    /** Configures the pipeline. */
    @Override
    protected void configurePipeline() {

        // use XML 1.0 datatype library
        setProperty(DATATYPE_VALIDATOR_FACTORY, fDatatypeValidatorFactory);

        // setup DTD pipeline
        configureDTDPipeline();

        // setup document pipeline
        if (fFeatures.get(NAMESPACES) == Boolean.TRUE) {
            fProperties.put(NAMESPACE_BINDER, fNamespaceBinder);
            fScanner = fNamespaceScanner;
            fProperties.put(DOCUMENT_SCANNER, fNamespaceScanner);
            if (fDTDValidator != null) {
                fProperties.put(DTD_VALIDATOR, fDTDValidator);
                fNamespaceScanner.setDTDValidator(fDTDValidator);
                fNamespaceScanner.setDocumentHandler(fDTDValidator);
                fDTDValidator.setDocumentSource(fNamespaceScanner);
                fDTDValidator.setDocumentHandler(fDocumentHandler);
                if (fDocumentHandler != null) {
                    fDocumentHandler.setDocumentSource(fDTDValidator);
                }
                fLastComponent = fDTDValidator;
            }
            else {
                fNamespaceScanner.setDocumentHandler(fDocumentHandler);
                fNamespaceScanner.setDTDValidator(null);
                if (fDocumentHandler != null) {
                    fDocumentHandler.setDocumentSource(fNamespaceScanner);
                }
                fLastComponent = fNamespaceScanner;
            }
        }
        else {
            fScanner = fNonNSScanner;
            fProperties.put(DOCUMENT_SCANNER, fNonNSScanner);
            if (fNonNSDTDValidator != null) {
                fProperties.put(DTD_VALIDATOR, fNonNSDTDValidator);
                fNonNSScanner.setDocumentHandler(fNonNSDTDValidator);
                fNonNSDTDValidator.setDocumentSource(fNonNSScanner);
                fNonNSDTDValidator.setDocumentHandler(fDocumentHandler);
                if (fDocumentHandler != null) {
                    fDocumentHandler.setDocumentSource(fNonNSDTDValidator);
                }
                fLastComponent = fNonNSDTDValidator;
            }
            else {
                fScanner.setDocumentHandler(fDocumentHandler);
                if (fDocumentHandler != null) {
                    fDocumentHandler.setDocumentSource(fScanner);
                }
                fLastComponent = fScanner;
            }
        }

        // setup document pipeline
    } // configurePipeline()



    /** Create a document scanner: this scanner performs namespace binding
      */
    @Override
    protected XMLDocumentScanner createDocumentScanner() {
        fNamespaceScanner = new XMLNSDocumentScannerImpl();
        return fNamespaceScanner;
    } // createDocumentScanner():XMLDocumentScanner


    /** Create a DTD validator: this validator performs namespace binding.
      */
    @Override
    protected XMLDTDValidator createDTDValidator() {
        return new XMLNSDTDValidator();
    } // createDTDValidator():XMLDTDValidator

} // class IntegratedParserConfiguration

