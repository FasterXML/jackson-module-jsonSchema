package com.fasterxml.jackson.module.jsonSchema;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.fasterxml.jackson.module.jsonSchema.SchemaTestBase;

public class TestCyclic extends SchemaTestBase
{
    // [Issue#4]
    public class Loop {
        public String name;
        public Loop next;
    }

    private final ObjectMapper MAPPER = objectMapper();
    
    // [Issue#4]
    public void testSimpleCyclic() throws Exception
    {
        JsonSchemaGenerator generator = new JsonSchemaGenerator(MAPPER);
        JsonSchema schema = generator.generateSchema(Loop.class);

        String json = MAPPER.writeValueAsString(schema);
        String EXP = "{\"type\":\"object\",\"id\":\"urn:com:fasterxml:jackson:module:jsonSchema:TestCyclic$Loop\"," +
            "\"properties\":{\"next\":{\"type\":\"object\"," +
            "\"$ref\":\"urn:com:fasterxml:jackson:module:jsonSchema:TestCyclic$Loop\"},\"name\":{\"type\":\"string\"}}}";

        assertEquals(aposToQuotes(EXP), json);
    }
}
