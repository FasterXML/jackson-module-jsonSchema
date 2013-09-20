package com.fasterxml.jackson.module.jsonSchema.failing;

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

        String json = MAPPER.writeValueAsString(schema).replace('"', '\'');
        String EXP = "{'type':'object','properties':{"
                +"'name':{'type':'string'}},'next':{'type':'object'}}}";
//        System.err.println("JSON: "+json);
        assertEquals(EXP, json);
    }
}
