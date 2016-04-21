package com.fasterxml.jackson.module.jsonSchema;

import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;
import com.fasterxml.jackson.module.jsonSchema.types.StringSchema;

public class TestJsonValue extends SchemaTestBase
{
    static class ContainerWithAsValue
    {
        private Leaf value;
        
        @JsonValue
        public Leaf getValue() { return value; }
    }

    static class Leaf {
        public int value;
    }

    static class Issue34Bean {
        @JsonValue
        public Collection<String> getNames() { return null; }
    }

    // For [module-jsonSchema#100]
    static class MapViaJsonValue {
        @JsonValue
        public Map<String, String> getMap() {
            return null;
        }
    }

    /*
    /**********************************************************
    /* Unit tests, success
    /**********************************************************
     */

    public void testJsonValueAnnotation() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
 
        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        mapper.acceptJsonFormatVisitor(mapper.constructType(Leaf.class), visitor);
        JsonSchema schemaExp = visitor.finalSchema();
        assertNotNull(schemaExp);
        
        visitor = new SchemaFactoryWrapper();
        mapper.acceptJsonFormatVisitor(mapper.constructType(ContainerWithAsValue.class), visitor);
        JsonSchema schemaAct = visitor.finalSchema();
        assertNotNull(schemaAct);

        // these are minimal checks:
        assertEquals(schemaExp.getType(), schemaAct.getType());
        assertEquals(schemaExp, schemaAct);

        // but let's require bit fuller match:

        // construction from bean results in an 'id' being set, whereas from @AsValue it doesn't.
        schemaExp.setId(null);

        String expStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schemaExp);
        String actStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schemaAct);
        
        assertEquals(expStr, actStr);
    }

    // For [module-jsonSchema#34]
    public void testJsonValueForCollection() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
 
        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        mapper.acceptJsonFormatVisitor(mapper.constructType(Issue34Bean.class), visitor);
        JsonSchema schema = visitor.finalSchema();
        assertNotNull(schema);
        
        String schemaStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema);
        assertNotNull(schemaStr);
    }

    // For [module-jsonSchema#100]
    public void testMapViaJsonValue() throws Exception {
        ObjectMapper m = new ObjectMapper();
        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        m.acceptJsonFormatVisitor(m.constructType(MapViaJsonValue.class), visitor);
        JsonSchema schema = visitor.finalSchema();
        assertType(schema, ObjectSchema.class);
        ObjectSchema objectSchema = (ObjectSchema) schema;

        assertType(objectSchema.getAdditionalProperties(), ObjectSchema.SchemaAdditionalProperties.class);
        ObjectSchema.SchemaAdditionalProperties additionalProperties =
                (ObjectSchema.SchemaAdditionalProperties) objectSchema.getAdditionalProperties();
        assertType(additionalProperties.getJsonSchema(), StringSchema.class);
    }
}
