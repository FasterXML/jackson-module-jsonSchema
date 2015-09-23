package com.fasterxml.jackson.module.jsonSchema.failing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.SchemaTestBase;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

/**
 * Trivial test to ensure that Schema instances can be also deserialized
 */
public class TestReadJsonSchema
    extends SchemaTestBase
{
    enum SchemaEnum {
        YES, NO;
    }

    static class SchemabeIteratorOverStringArray {
        public Iterator<String[]> extra3;
    }

    static class SchemableEnumSet {
        public EnumSet<SchemaEnum> testEnums;
    }
    static class SchemableEnumMap {
        public EnumMap<SchemaEnum, List<String>> whatever;
    }

    /*
    /**********************************************************
    /* Unit tests, success
    /**********************************************************
     */

    private final ObjectMapper MAPPER = new ObjectMapper();

    public void testStructuredEnumTypes() throws Exception {
        _testSimple(SchemableEnumSet.class);
        _testSimple(SchemableEnumMap.class);
    }
    
    public void _testSimple(Class<?> type) throws Exception
    {
        // Create a schema
        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        MAPPER.acceptJsonFormatVisitor(MAPPER.constructType(type), visitor);
        JsonSchema jsonSchema = visitor.finalSchema();
        assertNotNull(jsonSchema);

        // Write the schema as a string
        String schemaStr = MAPPER.writeValueAsString(jsonSchema);
        assertNotNull(schemaStr);

        // Read the schema back into a JsonSchema
        JsonSchema result = MAPPER.readValue(schemaStr, JsonSchema.class);

        assertEquals("Schemas for "+type.getSimpleName()+" differ", jsonSchema, result);
    }
}
