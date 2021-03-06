/*
 * Copyright (c) 2012, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.eel.kitchen.jsonschema.schema;

import com.fasterxml.jackson.databind.JsonNode;

import java.net.URI;

/**
 * Schema addressing mode
 *
 * <p>JSON Schema defines the {@code id} keyword for schema identification
 * purposes. This keyword can be used both at the schema root <i>and</i> in
 * subschemas. For instance:</p>
 *
 * <pre>
 *     {
 *         "id": "some://where/schema.json",
 *         "sub": {
 *             "id": "other.json"
 *         }
 *     }
 * </pre>
 *
 * <p>What can happen here is that an implementation walks the schema and
 * determines that the URI of the root schema is {@code
 * some://where/schema.json}, which is pretty much normal. It <i>can</i> also
 * see the other {@code id} in subschema {@code /sub} and resolve the value of
 * this subschema against the root URI: this gives {@code
 * some://where/other.json}. This is called by this implementation {@link
 * #INLINE} addressing mode.</p>
 *
 * <p>By default, however, and for security reasons, the addressing mode is
 * {@link #CANONICAL}.</p>
 */
public enum AddressingMode
{
    CANONICAL
        {
            @Override
            public SchemaContainer forSchema(final URI uri,
                final JsonNode schema)
            {
                return new CanonicalSchemaContainer(uri, schema);
            }
        },
    INLINE
        {
            @Override
            public SchemaContainer forSchema(final URI uri,
                final JsonNode schema)
            {
                return new InlineSchemaContainer(schema);
            }
        };

    private static final URI EMPTY = URI.create("#");

    public abstract SchemaContainer forSchema(final URI uri,
        final JsonNode schema);

    public final SchemaContainer forSchema(final JsonNode schema)
    {
        return forSchema(EMPTY, schema);
    }
}
