package com.fasterxml.jackson.module.jsonSchema.failing;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.SchemaTestBase;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;

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
        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        MAPPER.acceptJsonFormatVisitor(MAPPER.constructType(type), visitor);
        JsonSchema jsonSchema = visitor.finalSchema();
        assertNotNull(jsonSchema);

        String schemaStr = MAPPER.writeValueAsString(jsonSchema);
        assertNotNull(schemaStr);
        JsonSchema result = MAPPER.readValue(schemaStr, JsonSchema.class);
        String resultStr = MAPPER.writeValueAsString(result);
        JsonNode node = MAPPER.readTree(schemaStr);
        JsonNode finalNode = MAPPER.readTree(resultStr);

        String json1 = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(node);
        String json2 = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(finalNode);
        
//        assertEquals(node, finalNode);
        assertEquals("Schemas for "+type.getSimpleName()+" differ",
                json1, json2);
    }
}
