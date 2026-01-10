package tools.jackson.module.jsonSchema;

import tools.jackson.databind.DatabindContext;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.annotation.JsonTypeIdResolver;
import tools.jackson.module.jsonSchema.types.ObjectSchema;

public class CustomSchemaReadTest extends SchemaTestBase
{
    @JsonTypeIdResolver(MyResolver.class)
    public static class MySchema extends ObjectSchema {
    }

    static class MyResolver extends JsonSchemaIdResolver
    {
        @Override
        public String idFromValue(DatabindContext context, Object value) {
            if (value instanceof MySchema) {
                return "CUSTOM";
            }
            return super.idFromValue(context, value);
        }

        @Override
        public String idFromValueAndType(DatabindContext context, Object value, Class<?> suggestedType) {
            if (value instanceof MySchema) {
                return "CUSTOM";
            }
            return super.idFromValueAndType(context, value, suggestedType);
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
         String input = "{ \"type\" : \"CUSTOM\" , \"id\" : \"7a2e8538-196b-423e-b714-13515848ec0c\" , \"description\" : \"My Schema\" , \"title\" : \"my-json-schema\" , \"properties\" : { \"myarray\" : { \"type\" : \"array\" , \"required\" : true , \"title\" : \"my property #2\" , \"items\" : { \"type\" : \"string\"} , \"maxItems\" : 5} , \"mystring\" : { \"type\" : \"string\" , \"required\" : true , \"title\" : \"my property #1\" , \"format\" : \"regex\" , \"pattern\" : \"\\\\w+\"} , \"myobject\" : { \"type\" : \"object\" , \"required\" : true , \"title\" : \"my property #3\" , \"properties\" : { \"subprop\" : { \"type\" : \"string\" , \"required\" : true , \"title\" : \"sub property #1\" , \"format\" : \"regex\" , \"pattern\" : \"\\\\w{3}\"}}}}}";

         ObjectMapper mapper = new ObjectMapper();

         // ObjectSchema schema = mapper.readValue(json, ObjectSchema.class); // works
         MySchema schema = mapper.readValue(input, MySchema.class); // fails

         String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema);
         assertNotNull(json);
    }
}
