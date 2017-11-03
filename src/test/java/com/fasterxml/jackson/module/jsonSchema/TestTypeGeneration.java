package com.fasterxml.jackson.module.jsonSchema;

import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.factories.WrapperFactory.JsonSchemaVersion;

public class TestTypeGeneration extends SchemaTestBase
{
    static class Issue14Bean
    {
        public Date date;
    }

    /*
    /**********************************************************
    /* Unit tests
    /**********************************************************
     */

    final ObjectMapper MAPPER = objectMapper();
    
    // [Issue#14]: multiple type attributes
    public void testCorrectType() throws Exception
    {
        JsonSchemaGenerator generator = new JsonSchemaGenerator(MAPPER, JsonSchemaVersion.DRAFT_V4);
        JsonSchema jsonSchema = generator.generateSchema(Issue14Bean.class);
        String json = MAPPER.writeValueAsString(jsonSchema).replace('"', '\'');
        final String EXP = "{'type':'object'," +
                "'id':'urn:jsonschema:com:fasterxml:jackson:module:jsonSchema:TestTypeGeneration:Issue14Bean'," +
                "'$schema':'" + JsonSchemaVersion.DRAFT_V4.getSchemaString() + "'," +
                "'properties':{'date':{'type':'integer','format':'utc-millisec'}}}";
        assertEquals(EXP, json);
    }

}
