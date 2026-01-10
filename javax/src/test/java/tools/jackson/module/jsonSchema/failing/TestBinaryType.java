package tools.jackson.module.jsonSchema.failing;

import tools.jackson.databind.ObjectMapper;

import tools.jackson.module.jsonSchema.JsonSchema;
import tools.jackson.module.jsonSchema.JsonSchemaGenerator;
import tools.jackson.module.jsonSchema.SchemaTestBase;

public class TestBinaryType extends SchemaTestBase
{
    private final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Test simple generation for simple/primitive numeric types
     */
    public void testBinaryType() throws Exception
    {
        JsonSchemaGenerator generator = new JsonSchemaGenerator(MAPPER);
        JsonSchema schema;

        schema = generator.generateSchema(byte[].class);

        // Should be either an array of bytes, or, String with 'format' of "base64"
        String json = MAPPER.writeValueAsString(schema);

        if (!json.equals(aposToQuotes("{'type':'array','items':{'type':'byte'}}"))) {
            String pretty = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(schema);
            fail("Should get 'array of bytes' or 'String as Base64', instead got: "+pretty);
        }
    }
}
