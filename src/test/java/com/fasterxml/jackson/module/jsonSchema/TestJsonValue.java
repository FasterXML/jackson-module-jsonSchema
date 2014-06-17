package com.fasterxml.jackson.module.jsonSchema;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;

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
}
