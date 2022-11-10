/*
 * Copyright © 2019 FRINX s.r.o. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.restconf.nb.rfc8040.jersey.providers.errors;

import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.opendaylight.restconf.nb.rfc8040.handlers.SchemaContextHandler;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.restconf.rev170126.Errors;
import org.opendaylight.yangtools.yang.common.XMLNamespace;
import org.opendaylight.yangtools.yang.data.api.YangInstanceIdentifier.NodeIdentifier;
import org.opendaylight.yangtools.yang.data.api.schema.stream.NormalizedNodeStreamWriter;
import org.opendaylight.yangtools.yang.data.codec.gson.JSONCodecFactorySupplier;
import org.opendaylight.yangtools.yang.data.codec.gson.JSONNormalizedNodeStreamWriter;
import org.opendaylight.yangtools.yang.data.codec.gson.JsonWriterFactory;

/**
 * JSON stream-writer with disabled leaf-type validation for specified QName.
 */
final class JsonStreamWriterWithDisabledValidation extends StreamWriterWithDisabledValidation {
    private static final int DEFAULT_INDENT_SPACES_NUM = 2;
    private static final XMLNamespace IETF_RESTCONF_URI = Errors.QNAME.getModule().getNamespace();

    private final JsonWriter jsonWriter;
    private final NormalizedNodeStreamWriter jsonNodeStreamWriter;

    /**
     * Creation of the custom JSON stream-writer.
     *
     * @param schemaContextHandler Handler that holds actual schema context.
     * @param outputWriter         Output stream that is used for creation of JSON writers.
     */
    JsonStreamWriterWithDisabledValidation(final SchemaContextHandler schemaContextHandler,
            final OutputStreamWriter outputWriter) {
        jsonWriter = JsonWriterFactory.createJsonWriter(outputWriter, DEFAULT_INDENT_SPACES_NUM);
        final var inference = errorsContainerInference(schemaContextHandler);
        jsonNodeStreamWriter = JSONNormalizedNodeStreamWriter.createExclusiveWriter(
            JSONCodecFactorySupplier.RFC7951.getShared(inference.getEffectiveModelContext()),
            inference, IETF_RESTCONF_URI, jsonWriter);
    }

    @Override
    protected NormalizedNodeStreamWriter delegate() {
        return jsonNodeStreamWriter;
    }

    @Override
    void startLeafNodeWithDisabledValidation(final NodeIdentifier nodeIdentifier) throws IOException {
        jsonWriter.name(nodeIdentifier.getNodeType().getLocalName());
    }

    @Override
    void scalarValueWithDisabledValidation(final Object value) throws IOException {
        jsonWriter.value(value.toString());
    }

    @Override
    void endNodeWithDisabledValidation() {
        // nope
    }
}