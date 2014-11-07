package com.fasterxml.jackson.module.jsonSchema;

import java.math.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJDKTypes extends SchemaTestBase
{
    private final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Test simple generation for simple/primitive numeric types
     */
    public void testSimpleNumbers() throws Exception
    {
        JsonSchemaGenerator generator = new JsonSchemaGenerator(MAPPER);
        JsonSchema schema;

        schema = generator.generateSchema(Long.class);
        assertEquals("{\"type\":\"integer\"}", MAPPER.writeValueAsString(schema));

        /* 07-Nov-2014, tatu: Won't work correctly before 2.5, due to various things; will work
         *    with 2.5. Uncomment then.
         */
        /*
        schema = generator.generateSchema(BigInteger.class);
        assertEquals("{\"type\":\"integer\"}", MAPPER.writeValueAsString(schema));
        */

        schema = generator.generateSchema(Double.class);
        assertEquals("{\"type\":\"number\"}", MAPPER.writeValueAsString(schema));

        schema = generator.generateSchema(BigDecimal.class);
        assertEquals("{\"type\":\"number\"}", MAPPER.writeValueAsString(schema));
        
    }
}
