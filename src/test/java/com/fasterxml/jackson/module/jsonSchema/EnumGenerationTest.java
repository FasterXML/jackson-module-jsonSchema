package com.fasterxml.jackson.module.jsonSchema;

import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class EnumGenerationTest extends SchemaTestBase
{
    public enum Enumerated {
        A, B, C;

        // add this; should NOT matter but...
        @Override
        public String toString() {
            return "ToString:"+name();
        }
    }
    public static class LetterBean {

        public Enumerated letter;
    }

    /*
    /**********************************************************
    /* Test methods
    /**********************************************************
     */

    private final ObjectMapper MAPPER = new ObjectMapper();

    public void testEnumDefault() throws Exception
    {
        JsonSchemaGenerator generator = new JsonSchemaGenerator(MAPPER);
        JsonSchema jsonSchema = generator.generateSchema(LetterBean.class);
        @SuppressWarnings("unchecked")
        Map<String, Object> result = (Map<String, Object>) MAPPER.convertValue(jsonSchema,  Map.class);
        assertNotNull(result);
        assertTrue(jsonSchema.isObjectSchema());
        assertEquals(expectedAsMap(false), result);
    }

    // Comment out to get 2.5.3 released
    /*

    public void testEnumWithToString() throws Exception
    {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);

        JsonSchemaGenerator generator = new JsonSchemaGenerator(mapper);
        JsonSchema jsonSchema = generator.generateSchema(LetterBean.class);
        @SuppressWarnings("unchecked")
        Map<String, Object> result = (Map<String, Object>) mapper.convertValue(jsonSchema,  Map.class);
        assertNotNull(result);
        assertTrue(jsonSchema.isObjectSchema());
        assertEquals(expectedAsMap(true), result);
    }
    */

    @SuppressWarnings("serial")
    private Map<String,Object> expectedAsMap(final boolean useToString)
    {
        return new LinkedHashMap<String, Object>() {
            {
                put("type", "object");
                put("id", "urn:jsonschema:com:fasterxml:jackson:module:jsonSchema:EnumGenerationTest:LetterBean");
                put("properties",
                        new LinkedHashMap<String, Object>() {
                    {
                        put("letter",
                                new LinkedHashMap<String, Object>() {
                            {
                                put("type", "string");
                                put("enum", new ArrayList<String>() {
                                    {
                                        add(useToString ? "ToString:A" : "A");
                                        add(useToString ? "ToString:B" : "B");
                                        add(useToString ? "ToString:C" : "C");
                                    }
                                });
                            }
                        });
                    }
                });
            }
        };
    }
}
