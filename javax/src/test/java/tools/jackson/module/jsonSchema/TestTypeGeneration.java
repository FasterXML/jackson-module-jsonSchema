package tools.jackson.module.jsonSchema;

import java.util.Date;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.cfg.DateTimeFeature;
import tools.jackson.databind.json.JsonMapper;

public class TestTypeGeneration extends SchemaTestBase
{
    static class Issue14Bean
    {
        public Date date;
    }

    /*
    /**********************************************************
    /* Unit tests
    /**********************************************************
     */


    // [Issue#14]: multiple type attributes
    public void testCorrectType() throws Exception
    {
        ObjectMapper mapper = JsonMapper.builder().enable(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS).build();
        JsonSchemaGenerator generator = new JsonSchemaGenerator(mapper);
        JsonSchema jsonSchema = generator.generateSchema(Issue14Bean.class);
        String json = mapper.writeValueAsString(jsonSchema).replace('"', '\'');
        final String EXP = "{'type':'object'," +
                "'id':'urn:jsonschema:tools:jackson:module:jsonSchema:TestTypeGeneration:Issue14Bean'," +
                "'properties':{'date':{'type':'integer','format':'utc-millisec'}}}";
        assertEquals(EXP, json);
    }

}
