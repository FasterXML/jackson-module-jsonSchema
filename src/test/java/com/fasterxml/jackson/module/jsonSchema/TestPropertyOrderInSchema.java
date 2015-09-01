package com.fasterxml.jackson.module.jsonSchema;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestPropertyOrderInSchema extends SchemaTestBase {

    @JsonPropertyOrder({"a", "b", "c"})
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

    /*
     /**********************************************************
     /* Unit tests
     /**********************************************************
     */
    final ObjectMapper MAPPER = objectMapper();

    public void testCorrectOrder() throws Exception {
        JsonSchemaGenerator generator = new JsonSchemaGenerator(MAPPER);
        JsonSchema jsonSchema = generator.generateSchema(Bean.class);
        System.out.println(jsonSchema.asObjectSchema().getProperties().keySet());
        assertEquals(jsonSchema.asObjectSchema().getProperties().keySet().toString(), "[a, b, c]");
    }

}
