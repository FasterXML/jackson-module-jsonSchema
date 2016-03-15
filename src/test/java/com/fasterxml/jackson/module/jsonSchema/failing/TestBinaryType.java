package com.fasterxml.jackson.module.jsonSchema.failing;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.fasterxml.jackson.module.jsonSchema.SchemaTestBase;

public class TestBinaryType extends SchemaTestBase
{
    private final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Test simple generation for simple/primitive numeric types
     */
    public void testSimpleNumbers() throws Exception
    {
        JsonSchemaGenerator generator = new JsonSchemaGenerator(MAPPER);
        JsonSchema schema;

        schema = generator.generateSchema(byte[].class);

        // Should be either an array of bytes, or, String with 'format' of "base64"
        assertEquals(aposToQuotes("{'type':'array','items':{'type':'byte'}}"),
                MAPPER.writeValueAsString(schema));
    }
}
