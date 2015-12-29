package com.fasterxml.jackson.module.jsonSchema;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestPropertyOrderInSchema extends SchemaTestBase {

    @JsonPropertyOrder({"c", "b", "a"})
    static class Bean {

        private int b;
        private String c;
        private String a;

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

        public String getC() {
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }
    }
    
     @JsonPropertyOrder(alphabetic = true)
    static class Bean2 {

        private int b;
        private String c;
        private String a;

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

        public String getC() {
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }
    }

    /*
     /**********************************************************
     /* Unit tests
     /**********************************************************
     */
    

    public void testAnnotationOrder() throws Exception {
        ObjectMapper MAPPER = objectMapper();
        JsonSchemaGenerator generator = new JsonSchemaGenerator(MAPPER);
        JsonSchema jsonSchema = generator.generateSchema(Bean.class);
        assertEquals(jsonSchema.asObjectSchema().getProperties().keySet().toString(), "[c, b, a]");
    }
    
    public void testAlphabeticOrder() throws Exception {
        final ObjectMapper MAPPER = objectMapper();
        JsonSchemaGenerator generator = new JsonSchemaGenerator(MAPPER);
        JsonSchema jsonSchema = generator.generateSchema(Bean2.class);
        assertEquals(jsonSchema.asObjectSchema().getProperties().keySet().toString(), "[a, b, c]");
    }

}
