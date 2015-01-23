package com.fasterxml.jackson.module.jsonSchema.failing;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.fasterxml.jackson.module.jsonSchema.SchemaTestBase;

public class TestEnumWithJsonValue57 extends SchemaTestBase
{
    // for [jsonSchema#57]
    public enum EnumViaJsonValue {
        A, B, C;

        @JsonValue
        public String asJson() { return name().toLowerCase(); }
    }
    
    /*
    /**********************************************************
    /* Test methods
    /**********************************************************
     */

    private final ObjectMapper MAPPER = new ObjectMapper();
    JsonSchemaGenerator SCHEMA_GEN = new JsonSchemaGenerator(MAPPER);


    // for [jsonSchema#57]
    @SuppressWarnings("unchecked")
    public void testEnumWithJsonValue() throws Exception
    {
        JsonSchema schema = SCHEMA_GEN.generateSchema(EnumViaJsonValue.class);

        Map<String, Object> result = (Map<String, Object>) MAPPER.convertValue(schema,  Map.class);        
        assertEquals("string", result.get("type"));

        Object values = result.get("enum");
        if (values == null) {
            fail("Expected 'enum' entry, not found; schema: "+ MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        }
        assertNotNull(values);
        assertTrue(values instanceof List<?>);
        List<?> enumValues = (List<?>) values;
        assertEquals(3, enumValues.size());
        assertEquals("a", enumValues.get(0));
        assertEquals("b", enumValues.get(1));
        assertEquals("c", enumValues.get(2));
    }
}
