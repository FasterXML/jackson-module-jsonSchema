package com.fasterxml.jackson.module.jsonSchema;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        JsonSchemaGenerator generator = new JsonSchemaGenerator(mapper);
        JsonSchema schema = generator.generateSchema(UnwrappingRoot.class);

        String json = mapper.writeValueAsString(schema).replace('"', '\'');
        
        String EXP = "{'type':'object'," +
                     "'id':'urn:jsonschema:com:fasterxml:jackson:module:jsonSchema:TestUnwrapping:UnwrappingRoot'," +
                     "'properties':{'age':{'type':'integer'},'name.first':{'type':'string'},'name.last':{'type':'string'}}}";
        assertEquals(EXP, json);
    }
}
