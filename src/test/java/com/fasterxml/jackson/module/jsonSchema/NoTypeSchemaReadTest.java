package com.fasterxml.jackson.module.jsonSchema;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.module.jsonSchema.types.ArraySchema;

public class NoTypeSchemaReadTest extends SchemaTestBase {
    public void testNoTypeSchema() throws Exception {
        String input = "{}";

        ObjectMapper mapper = new ObjectMapper();
        JsonSchema schema = mapper.readValue(input, JsonSchema.class);

        assertEquals(JsonFormatTypes.ANY, schema.getType());
    }

    public void testNoTypeSingleItems() throws Exception {
        String input = "{ \"type\": \"array\", \"items\": {} }";

        ObjectMapper mapper = new ObjectMapper();
        JsonSchema schema = mapper.readValue(input, JsonSchema.class);

        assertTrue(schema instanceof ArraySchema);

        ArraySchema.Items items = ((ArraySchema) schema).getItems();
        assertNotNull(items);
        assertTrue(items.isSingleItems());

        JsonSchema itemsSchema = items.asSingleItems().getSchema();
        assertNotNull(itemsSchema);
        assertEquals(JsonFormatTypes.ANY, itemsSchema.getType());
    }

    public void testNoTypeArrayItems() throws Exception {
        String input = "{ \"type\": \"array\", \"items\": [{}] }";

        ObjectMapper mapper = new ObjectMapper();
        JsonSchema schema = mapper.readValue(input, JsonSchema.class);

        assertTrue(schema instanceof ArraySchema);

        ArraySchema.Items items = ((ArraySchema) schema).getItems();
        assertNotNull(items);
        assertTrue(items.isArrayItems());

        JsonSchema[] itemsSchemas = items.asArrayItems().getJsonSchemas();
        assertNotNull(itemsSchemas);
        assertEquals(itemsSchemas.length, 1);
        assertNotNull(itemsSchemas[0]);
        assertEquals(JsonFormatTypes.ANY, itemsSchemas[0].getType());
    }
}
