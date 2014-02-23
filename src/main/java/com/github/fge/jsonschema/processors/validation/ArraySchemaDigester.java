/*
 * Copyright (c) 2014, Francis Galiegue (fgaliegue@gmail.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of both licenses is available under the src/resources/ directory of
 * this project (under the names LGPL-3.0.txt and ASL-2.0.txt respectively).
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package com.github.fge.jsonschema.processors.validation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.keyword.digest.AbstractDigester;
import com.github.fge.jsonschema.keyword.digest.Digester;

/**
 * JSON Schema digester for an {@link ArraySchemaSelector}
 */
public final class ArraySchemaDigester
    extends AbstractDigester
{
    private static final Digester INSTANCE = new ArraySchemaDigester();

    public static Digester getInstance()
    {
        return INSTANCE;
    }

    private ArraySchemaDigester()
    {
        super("", NodeType.ARRAY);
    }

    @Override
    public JsonNode digest(final JsonNode schema)
    {
        final ObjectNode ret = FACTORY.objectNode();
        ret.put("itemsSize", 0);
        ret.put("itemsIsArray", false);

        final JsonNode itemsNode = schema.path("items");
        final JsonNode additionalNode = schema.path("additionalItems");

        final boolean hasItems = !itemsNode.isMissingNode();
        final boolean hasAdditional = additionalNode.isObject();

        ret.put("hasItems", hasItems);
        ret.put("hasAdditional", hasAdditional);

        if (itemsNode.isArray()) {
            ret.put("itemsIsArray", true);
            ret.put("itemsSize", itemsNode.size());
        }

        return ret;
    }
}
