package com.fasterxml.jackson.module.jsonSchema;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.types.ArraySchema;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;

import java.io.IOException;
import java.util.*;

/**
 * Trivial test to ensure {@link JsonSchema} can be also deserialized
 */
public class TestReadJsonSchema
    extends SchemaTestBase
{
    enum SchemaEnum {
        YES, NO;
    }

    @JsonPropertyOrder(alphabetic=true)
    static class SchemableBasic
    {
        public String name;
        public JsonSerializable someSerializable;
    }

    @JsonPropertyOrder(alphabetic=true)
    static class CompoundProperty {
        public String a, b;
    }

    static class SchemableArrays
    {
        public char[] nameBuffer;
        // We'll include tons of stuff, just to force generation of schema
        public boolean[] states;
        public byte[] binaryData;
        public short[] shorts;
        public int[] ints;
        public long[] longs;
        public float[] floats;
        public double[] doubles;
        public Object[] objects;
        public CompoundProperty[] basics;
    }

    static class SchemabeLists
    {
        public ArrayList<String> extra2;
        public List<String> extra;
        public List<CompoundProperty> basics;
        // TODO this generates a $ref which can't be read
//        public ArrayList<CompoundProperty> basics2;
    }

    static class GeneratesRef {
        public CompoundProperty[] a;
        // this property serializes as $ref to previous definition of CompoundProperty
        public CompoundProperty[] b;
    }

    static class SchemabeIterableOverObjects {
        public Iterable<Object> iterableOhYeahBaby;
    }

    static class SchemableMaps {
        public Map<String, Map<String, Double>> mapSizes;
    }

    /*
    /**********************************************************
    /* Unit tests, success
    /**********************************************************
     */

    private final ObjectMapper MAPPER = new ObjectMapper();
    {
        MAPPER.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
    }

    /**
     * Verifies that a simple schema that is serialized can be deserialized back
     * to equal schema instance
     */
    public void testReadSimpleTypes() throws Exception {
        _testSimple(SchemableBasic.class);
    }

    public void testReadArrayTypes() throws Exception {
        _testSimple(SchemableArrays.class);
    }

    public void testReadListTypes() throws Exception {
        _testSimple(SchemabeLists.class);
    }

    public void testReadArray$ref() throws Exception {
        _testSimple(GeneratesRef.class);
    }

    public void testReadIterables() throws Exception {
        _testSimple(SchemabeIterableOverObjects.class);
    }
    
    public void testMapTypes() throws Exception {
        _testSimple(SchemableMaps.class);
    }
    
    public void testAdditionalItems() throws Exception {
        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        MAPPER.acceptJsonFormatVisitor(MAPPER.constructType(SchemableArrays.class), visitor);
        JsonSchema jsonSchema = visitor.finalSchema();
        assertNotNull(jsonSchema);
        
        assertTrue(jsonSchema.isObjectSchema());
        for (JsonSchema propertySchema : jsonSchema.asObjectSchema().getProperties().values())
        {
            assertTrue(propertySchema.isArraySchema());
            propertySchema.asArraySchema().setAdditionalItems(new ArraySchema.NoAdditionalItems());
        }
        
        _testSimple("SchemableArrays - additionalItems", jsonSchema);
    }

    static class SchemableEnumSet {
        public EnumSet<SchemaEnum> testEnums;
    }
    static class SchemableEnumMap {
        public EnumMap<SchemaEnum, List<String>> whatever;
    }

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

        _testSimple(type.getSimpleName(), jsonSchema);
    }

    private void _testSimple(String name, JsonSchema jsonSchema) throws java.io.IOException {
        // Need to do this twice so that $ref schemas
        JsonSchema reread = writeAndRead(jsonSchema);
        JsonSchema rereadAgain = writeAndRead(reread);

        assertEquals("Schemas for " + name + " differ", reread, rereadAgain);
    }

    private JsonSchema writeAndRead(JsonSchema jsonSchema) throws IOException {
        String asString = MAPPER.writeValueAsString(jsonSchema);

        assertNotNull(asString);

        return MAPPER.readValue(asString, JsonSchema.class);
    }

    /**
     * Verifies that false-valued and object-valued additional properties are
     * deserialized properly
     */
    public void testDeserializeFalseAndObjectAdditionalProperties() throws Exception
    {
        String schemaStr = "{\"type\":\"object\",\"properties\":{\"mapSizes\":{\"type\":\"object\",\"additionalProperties\":{\"type\":\"number\"}}},\"additionalProperties\":false}";
        JsonSchema schema = MAPPER.readValue(schemaStr, JsonSchema.class);
        String newSchemaStr = MAPPER.writeValueAsString(schema);
        assertEquals(schemaStr.replaceAll("\\s", "").length(), newSchemaStr.replaceAll("\\s", "").length());
        
        JsonNode node = MAPPER.readTree(schemaStr);
        JsonNode finalNode = MAPPER.readTree(newSchemaStr);
        assertEquals(node, finalNode);
    }

    /**
     * Verifies that a true-valued additional property is deserialized properly
     */
    public void testDeserializeTrueAdditionalProperties() throws Exception
    {
        String schemaStr = "{\"type\":\"object\",\"additionalProperties\":true}";
        ObjectSchema schema = MAPPER.readValue(schemaStr, ObjectSchema.class);
        assertNull(schema.getAdditionalProperties());
    }

    /**
     * Verifies that a true-valued additional property is deserialized properly
     */
    public void testOneOf() throws Exception
    {
        String schemaStr = "{\n" +
                "    \"id\": \"http://some.site.somewhere/entry-schema#\",\n" +
                "    \"$schema\": \"http://json-schema.org/draft-04/schema#\",\n" +
                "    \"description\": \"schema for an fstab entry\",\n" +
                "    \"type\": \"object\",\n" +
                //"    \"required\": [ \"storage\" ],\n" +
                "    \"properties\": {\n" +
                "        \"storage\": {\n" +
                "            \"type\": \"object\",\n" +
                "            \"oneOf\": [\n" +
                "                { \"$ref\": \"#/definitions/diskDevice\" },\n" +
                "                { \"$ref\": \"#/definitions/diskUUID\" },\n" +
                "                { \"$ref\": \"#/definitions/nfs\" },\n" +
                "                { \"$ref\": \"#/definitions/tmpfs\" }\n" +
                "            ]\n" +
                "        },\n" +
                "        \"fstype\": {\n" +
                "            \"type\": \"object\",\n" +
                "            \"enum\": [ \"ext3\", \"ext4\", \"btrfs\" ]\n" +
                "        },\n" +
                "        \"options\": {\n" +
                "            \"type\": \"array\",\n" +
                "            \"minItems\": 1,\n" +
                "            \"items\": { \"type\": \"string\" },\n" +
                "            \"uniqueItems\": true\n" +
                "        },\n" +
                "        \"readonly\": { \"type\": \"boolean\" }\n" +
                "    }\n" +
//                "    \"definitions\": {\n" +
//                "        \"diskDevice\": {},\n" +
//                "        \"diskUUID\": {},\n" +
//                "        \"nfs\": {},\n" +
//                "        \"tmpfs\": {}\n" +
//                "    }\n" +
                "}";
        ObjectSchema schema = MAPPER.readValue(schemaStr, ObjectSchema.class);
        assertNotNull(schema.getProperties().get("storage").asObjectSchema().getOneOf());
        assertEquals(4,schema.getProperties().get("storage").asObjectSchema().getOneOf().size());
    }
}
