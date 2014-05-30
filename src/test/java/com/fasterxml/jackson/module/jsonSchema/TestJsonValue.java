package com.fasterxml.jackson.module.jsonSchema;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.factories.CyclicProtector;

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
        
        // We need to have a starting point, and it is usually in JsonSchemaGenerator.generateSchema(...), but during internal unit tests we need this step 
        CyclicProtector.reset();
        visitor = new SchemaFactoryWrapper();
        mapper.acceptJsonFormatVisitor(mapper.constructType(ContainerWithAsValue.class), visitor);
        JsonSchema schemaAct = visitor.finalSchema();
        assertNotNull(schemaAct);

        // these are minimal checks:
        assertEquals(schemaExp.getType(), schemaAct.getType());
        assertEquals(schemaExp, schemaAct);

        // but let's require bit fuller match:

        String expStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schemaExp);
        String actStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schemaAct);

        assertEquals(expStr, actStr);
    }
}
