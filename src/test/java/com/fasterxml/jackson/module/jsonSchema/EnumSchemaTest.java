package com.fasterxml.jackson.module.jsonSchema;

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
}
