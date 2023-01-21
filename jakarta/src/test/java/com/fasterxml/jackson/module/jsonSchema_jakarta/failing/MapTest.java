package com.fasterxml.jackson.module.jsonSchema_jakarta.failing;

import com.fasterxml.jackson.module.jsonSchema_jakarta.TestBase;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.module.jsonSchema_jakarta.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema_jakarta.JsonSchemaGenerator;

// for [module-jsonSchema#89]
public class MapTest extends TestBase
{
    static class MapBean {
        private Map<String, Integer> counts;

        public void setCounts(Map<String, Integer> counts) {
            this.counts = counts;
        }

        public Map<String, Integer> getCounts() {
            return counts;
        }
    }

    /*
    /**********************************************************
    /* Tests methods
    /**********************************************************
    */

    private final ObjectMapper MAPPER = new ObjectMapper();

    public void testSimpleMapKeyType89() throws Exception
    {
        JsonSchemaGenerator generator = new JsonSchemaGenerator(MAPPER);
        JsonSchema schema;

        schema = generator.generateSchema(MapBean.class);
        String json = MAPPER
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(schema);
//        System.out.println(json);

        // !!! TODO: actually verify key type is retained
        fail("Key type not preserved, contents: "+json);
        
//        assertEquals("{\"type\":\"integer\"}", json);
    }
}
