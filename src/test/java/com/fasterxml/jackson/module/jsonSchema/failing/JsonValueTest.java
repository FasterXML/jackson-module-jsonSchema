package com.fasterxml.jackson.module.jsonSchema.failing;

import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.TestBase;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;
import com.fasterxml.jackson.module.jsonSchema.types.StringSchema;

public class JsonValueTest extends TestBase {

    @Test
    public void testPassing() throws Exception {
        ObjectMapper m = new ObjectMapper();
        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        m.acceptJsonFormatVisitor(m.constructType(TheMap.class), visitor);
        JsonSchema schema = visitor.finalSchema();
        assertType(schema, ObjectSchema.class);
        ObjectSchema objectSchema = (ObjectSchema) schema;
        assertTrue(objectSchema.getProperties().containsKey("map"));
        assertType(objectSchema.getProperties().get("map"), ObjectSchema.class);
        objectSchema = (ObjectSchema) objectSchema.getProperties().get("map");
        assertType(objectSchema.getAdditionalProperties(), ObjectSchema.SchemaAdditionalProperties.class);
        ObjectSchema.SchemaAdditionalProperties additionalProperties =
                (ObjectSchema.SchemaAdditionalProperties) objectSchema.getAdditionalProperties();
        assertType(additionalProperties.getJsonSchema(), StringSchema.class);
    }

    @Test
    public void testFailing() throws Exception {
        ObjectMapper m = new ObjectMapper();
        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        m.acceptJsonFormatVisitor(m.constructType(TheAnnotatedMap.class), visitor);
        JsonSchema schema = visitor.finalSchema();
        assertType(schema, ObjectSchema.class);
        ObjectSchema objectSchema = (ObjectSchema) schema;
        assertType(objectSchema.getAdditionalProperties(), ObjectSchema.SchemaAdditionalProperties.class);
        ObjectSchema.SchemaAdditionalProperties additionalProperties =
                (ObjectSchema.SchemaAdditionalProperties) objectSchema.getAdditionalProperties();
        assertType(additionalProperties.getJsonSchema(), StringSchema.class);
    }

    private static class TheMap {
        public Map<String, String> getMap() {
            return null;
        }
    }

    private static class TheAnnotatedMap {
        @JsonValue
        public Map<String, String> getMap() {
            return null;
        }
    }
}
