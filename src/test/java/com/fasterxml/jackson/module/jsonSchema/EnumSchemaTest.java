package com.fasterxml.jackson.module.jsonSchema;

import java.util.Iterator;
import java.util.Set;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.types.ArraySchema;
import com.fasterxml.jackson.module.jsonSchema.types.ValueTypeSchema;

// for [module-jsonSchema#77]; problems (de)serializing enum values
public class EnumSchemaTest extends TestBase
{
    static enum MyEnum { FOO, BAR }
 /*
    static class MyClass {
        public MyEnum[] myEnumField;
    }
    */

    public void testEnumArrayDeserialization() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        mapper.acceptJsonFormatVisitor(mapper.constructType(MyEnum[].class), visitor);
        JsonSchema schema = visitor.finalSchema();

        // serialize schema:
        String serializedSchema = mapper.writeValueAsString(schema);
//        System.out.println("Schema1: " + serializedSchema);
        assertTrue(serializedSchema.contains("FOO"));

        // deserialize the schema
        JsonSchema schema2 = mapper.readValue(serializedSchema, JsonSchema.class);

        // verify
        assertTrue(schema2.isArraySchema());
        JsonSchema itemSchema = ((ArraySchema)
                schema2).getItems().asSingleItems().getSchema();
        assertTrue(itemSchema.isStringSchema());
        Set<String> enums = ((ValueTypeSchema)itemSchema).getEnums();

        /*
        System.out.println("Schema2: " + mapper.writeValueAsString(schema2));
        System.out.println("Deserialized enums: " + enums);
        */

        assertTrue(enums.contains("FOO"));
    }

    public void testEnumArrayDeserializationOrdering() throws Exception {
        final String jsonSchema = "{\n" +
                "    \"type\": \"object\",\n" +
                "    \"id\": \"https://foo.bar/wibble\",\n" +
                "    \"$schema\": \"http://json-schema.org/draft-03/schema#\",\n" +
                "    \"properties\": {\n" +
                "        \"testOptions\": {\n" +
                "            \"type\": \"array\",\n" +
                "            \"id\": \"testOptions\",\n" +
                "            \"required\":true,\n" +
                "            \"items\": {\n" +
                "                \"type\": \"string\",\n" +
                "                \"enum\": [\n" +
                "                    \"Section 1 'Macaroni and Cheese'\",\n" +
                "                    \"Section 2 'Spaghetti and Meatballs'\",\n" +
                "                    \"Section 3 'Fish and Chips'\",\n" +
                "                    \"Section 4 'Sausage and Mash'\"\n" +
                "                ]\n" +
                "            },\n" +
                "            \"minItems\": 1\n" +
                "        }\n" +
                "    }\n" +
                "}";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonSchema);
        JsonSchema deserialized = mapper.convertValue(jsonNode, JsonSchema.class);

        ArraySchema testOptionsSchema = deserialized.asObjectSchema().getProperties().get("testOptions").asArraySchema();
        ValueTypeSchema testOptionItemsSchema = testOptionsSchema.getItems().asSingleItems().getSchema().asValueTypeSchema();
        Iterator<String> enumSet = testOptionItemsSchema.getEnums().iterator();
        assertEquals("Expect enum options in order", "Section 1 'Macaroni and Cheese'", enumSet.next());
        assertEquals("Expect enum options in order", "Section 2 'Spaghetti and Meatballs'", enumSet.next());
        assertEquals("Expect enum options in order", "Section 3 'Fish and Chips'", enumSet.next());
        assertEquals("Expect enum options in order", "Section 4 'Sausage and Mash'", enumSet.next());
    }
}
