package com.fasterxml.jackson.module.jsonSchema.failing;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.fasterxml.jackson.module.jsonSchema.SchemaTestBase;

public class TestUnwrapping extends SchemaTestBase
{
    static class UnwrappingRoot
    {
        public int age;

        @JsonUnwrapped
        public Name name;
    }

    static class Name {
        @JsonUnwrapped(prefix="name.")
        public String first, last;
    }

    /*
    /**********************************************************
    /* Unit tests, success
    /**********************************************************
     */
    
    private final ObjectMapper MAPPER = objectMapper();

    public void testUnwrapping()  throws Exception
    {
        JsonSchemaGenerator generator = new JsonSchemaGenerator(MAPPER);
        JsonSchema schema = generator.generateSchema(UnwrappingRoot.class);

        String json = MAPPER.writeValueAsString(schema).replace('"', '\'');
        
//System.err.println("JSON -> "+MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(schema));
        String EXP = "{'type':'object','properties':{"
        +"'name.last':{'type':'string'},'name.first':{'type':'string'},"
        +"'age':{'type':'number','type':'integer'}}}";

System.err.println("EXP: "+EXP);
System.err.println("ACT: "+json);
        
        assertEquals(EXP, json);
    }
}
