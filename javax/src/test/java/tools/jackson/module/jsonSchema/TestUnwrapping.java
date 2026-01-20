package tools.jackson.module.jsonSchema;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

public class TestUnwrapping extends SchemaTestBase
{
    static class UnwrappingRoot
    {
        public int age;

        @JsonUnwrapped(prefix="name.")
        public Name name;
    }

    static class Name {
        public String first, last;
    }

    /*
    /**********************************************************
    /* Unit tests, success
    /**********************************************************
     */

    public void testUnwrapping()  throws Exception
    {
        ObjectMapper mapper = JsonMapper.builder().configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true).build();
        JsonSchemaGenerator generator = new JsonSchemaGenerator(mapper);
        JsonSchema schema = generator.generateSchema(UnwrappingRoot.class);

        String json = mapper.writeValueAsString(schema).replace('"', '\'');
        
        String EXP = "{'type':'object'," +
                     "'id':'urn:jsonschema:tools:jackson:module:jsonSchema:TestUnwrapping:UnwrappingRoot'," +
                     "'properties':{'age':{'type':'integer'},'name.first':{'type':'string'},'name.last':{'type':'string'}}}";
        assertEquals(EXP, json);
    }
}
