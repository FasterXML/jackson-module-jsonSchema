package com.fasterxml.jackson.module.jsonSchema;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.factories.WrapperFactory.JsonSchemaVersion;

public class TestJDKTypes extends SchemaTestBase
{
    private final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Test simple generation for simple/primitive numeric types
     */
    public void testSimpleNumbers() throws Exception
    {
        JsonSchemaGenerator generator = new JsonSchemaGenerator(MAPPER, JsonSchemaVersion.DRAFT_V4);
        JsonSchema schema;

        schema = generator.generateSchema(Long.class);
        assertEquals("{\"type\":\"integer\"}", MAPPER.writeValueAsString(schema));

        schema = generator.generateSchema(BigInteger.class);
        assertEquals("{\"type\":\"integer\"}", MAPPER.writeValueAsString(schema));

        schema = generator.generateSchema(Double.class);
        assertEquals("{\"type\":\"number\"}", MAPPER.writeValueAsString(schema));

        schema = generator.generateSchema(BigDecimal.class);
        assertEquals("{\"type\":\"number\"}", MAPPER.writeValueAsString(schema));
        
    }
}
