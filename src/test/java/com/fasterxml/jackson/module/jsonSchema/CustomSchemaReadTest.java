package com.fasterxml.jackson.module.jsonSchema;

import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.fasterxml.jackson.module.jsonSchema.factories.WrapperFactory.JsonSchemaVersion;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;

public class CustomSchemaReadTest extends SchemaTestBase
{
    @JsonTypeIdResolver(MyResolver.class)
    public static class MySchema extends ObjectSchema {
        protected MySchema() {
            super();
        }

        public MySchema(JsonSchemaVersion version) {
            super(version);
        }
    }

    static class MyResolver extends JsonSchemaIdResolver
    {
        @Override
        public String idFromValue(Object value) {
            if (value instanceof MySchema) {
                return "CUSTOM";
            }
            return super.idFromValue(value);
        }

        @Override
        public String idFromValueAndType(Object value, Class<?> suggestedType) {
            if (value instanceof MySchema) {
                return "CUSTOM";
            }
            return super.idFromValueAndType(value, suggestedType);
        }

        @Override
        public JavaType typeFromId(DatabindContext ctxt, String id) {
            if ("CUSTOM".equals(id)) {
                return ctxt.constructType(MySchema.class);
            }
            return super.typeFromId(ctxt, id);
        }
    }

    // [module-jsonSchema#67]
    public void testSchema() throws Exception
    {
         String input = "{\n" + 
             "   \"type\":\"CUSTOM\",\n" + 
             "   \"id\":\"7a2e8538-196b-423e-b714-13515848ec0c\",\n" + 
            "    \"$schema\": \"http://json-schema.org/draft-04/schema#\",\n" +
             "   \"description\":\"My Schema\",\n" + 
             "   \"title\":\"my-json-schema\",\n" + 
             "   \"properties\":{\n" + 
             "      \"myarray\":{\n" + 
             "         \"type\":\"array\",\n" + 
             "         \"title\":\"my property #2\",\n" + 
             "         \"items\":{\n" + 
             "            \"type\":\"string\"\n" + 
             "         },\n" + 
             "         \"maxItems\":5\n" + 
             "      },\n" + 
             "      \"mystring\":{\n" + 
             "         \"type\":\"string\",\n" + 
             "         \"title\":\"my property #1\",\n" + 
             "         \"format\":\"regex\",\n" + 
             "         \"pattern\":\"w+\"\n" + 
             "      },\n" + 
             "      \"myobject\":{\n" + 
             "         \"type\":\"object\",\n" + 
             "         \"title\":\"my property #3\",\n" + 
             "         \"properties\":{\n" + 
             "            \"subprop\":{\n" + 
             "               \"type\":\"string\",\n" + 
             "               \"title\":\"sub property #1\",\n" + 
             "               \"format\":\"regex\",\n" + 
             "               \"pattern\":\"w{3}\"\n" + 
             "            }\n" + 
             "         },\n" + 
             "         \"required\":[\"subprop\"]" +
             "      }\n" + 
             "   },\n" + 
             "   \"required\":[\"myarray\", \"mystring\", \"myobject\"]" +
             "}";

         ObjectMapper mapper = new ObjectMapper();

         // ObjectSchema schema = mapper.readValue(json, ObjectSchema.class); // works
         MySchema schema = mapper.readValue(input, MySchema.class); // fails

         String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema);
        System.err.println(json);
         assertNotNull(json);
    }
}
