package com.fasterxml.jackson.module.jsonSchema;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.factories.WrapperFactory.JsonSchemaVersion;

public class TestUnwrapping extends SchemaTestBase
{
    static class UnwrappingRoot
    {
        public int age;

        @JsonUnwrapped(prefix="name.")
        public Name name;
    }

    static class Name {
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
        JsonSchemaGenerator generator = new JsonSchemaGenerator(MAPPER, JsonSchemaVersion.DRAFT_V4);
        JsonSchema schema = generator.generateSchema(UnwrappingRoot.class);

        String json = MAPPER.writeValueAsString(schema).replace('"', '\'');
        
//System.err.println("JSON -> "+MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(schema));
        String EXP = "{'type':'object'," +
                     "'id':'urn:jsonschema:com:fasterxml:jackson:module:jsonSchema:TestUnwrapping:UnwrappingRoot'," +
                     "'$schema':'" + JsonSchemaVersion.DRAFT_V4.getSchemaString() + "'," +
                     "'properties':{'age':{'type':'integer'},'name.first':{'type':'string'},'name.last':{'type':'string'}}}";
        assertEquals(EXP, json);
    }
}
