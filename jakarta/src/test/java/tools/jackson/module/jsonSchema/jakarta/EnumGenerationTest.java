package tools.jackson.module.jsonSchema.jakarta;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.cfg.EnumFeature;
import tools.jackson.databind.json.JsonMapper;

public class EnumGenerationTest extends SchemaTestBase
{
    public enum Enumerated {
        A, B, C;

        // add this; should NOT matter but...
        @Override
        public String toString() {
            return "ToString:"+name();
        }
    }

    public static class LetterBean {

        public Enumerated letter;
    }

    // for [jsonSchema#57]
    public enum EnumViaJsonValue {
        A, B, C;

        @JsonValue
        public String asJson() { return name().toLowerCase(); }
    }

    /*
    /**********************************************************
    /* Test methods
    /**********************************************************
     */


    public void testEnumDefault() throws Exception
    {
        ObjectMapper mapper = JsonMapper.builder().disable(EnumFeature.WRITE_ENUMS_USING_TO_STRING).build();
        JsonSchema jsonSchema = new JsonSchemaGenerator(mapper).generateSchema(LetterBean.class);
        @SuppressWarnings("unchecked")
        Map<String, Object> result = (Map<String, Object>) mapper.convertValue(jsonSchema,  Map.class);
        assertNotNull(result);
        assertTrue(jsonSchema.isObjectSchema());
        assertEquals(expectedAsMap(false), result);
    }

    public void testEnumWithToString() throws Exception
    {
        final ObjectMapper mapper = JsonMapper.builder()
                .enable(EnumFeature.WRITE_ENUMS_USING_TO_STRING).build();

        JsonSchemaGenerator generator = new JsonSchemaGenerator(mapper);
        JsonSchema jsonSchema = generator.generateSchema(LetterBean.class);
        @SuppressWarnings("unchecked")
        Map<String, Object> result = (Map<String, Object>) mapper.convertValue(jsonSchema,  Map.class);
        assertNotNull(result);
        assertTrue(jsonSchema.isObjectSchema());
        Map<String,Object> exp = expectedAsMap(true);
        if (!exp.equals(result)) {
            String expJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(exp);
            String actJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
            fail("Different JSON: expected:\n"+expJson+"\ngot:\n"+actJson);
        }
    }

    private Map<String,Object> expectedAsMap(final boolean useToString)
    {
        return new LinkedHashMap<String, Object>() {
            {
                put("type", "object");
                put("id", "urn:jsonschema:tools:jackson:module:jsonSchema:jakarta:EnumGenerationTest:LetterBean");
                put("properties",
                        new LinkedHashMap<String, Object>() {
                    {
                        put("letter",
                                new LinkedHashMap<String, Object>() {
                            {
                                put("type", "string");
                                put("enum", new ArrayList<String>() {
                                    {
                                        add(useToString ? "ToString:A" : "A");
                                        add(useToString ? "ToString:B" : "B");
                                        add(useToString ? "ToString:C" : "C");
                                    }
                                });
                            }
                        });
                    }
                });
            }
        };
    }

    // for [jsonSchema#57]
    @SuppressWarnings("unchecked")
    public void testEnumWithJsonValue() throws Exception
    {
        ObjectMapper mapper = JsonMapper.builder().disable(EnumFeature.WRITE_ENUMS_USING_TO_STRING).build();
        JsonSchema schema = new JsonSchemaGenerator(mapper).generateSchema(EnumViaJsonValue.class);

        Map<String, Object> result = (Map<String, Object>) mapper.convertValue(schema,  Map.class);
        assertEquals("string", result.get("type"));

        Object values = result.get("enum");
        if (values == null) {
            fail("Expected 'enum' entry, not found; schema: "+ mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        }
        assertNotNull(values);
        assertTrue(values instanceof List<?>);
        List<?> enumValues = (List<?>) values;
        assertEquals(3, enumValues.size());
        assertEquals("a", enumValues.get(0));
        assertEquals("b", enumValues.get(1));
        assertEquals("c", enumValues.get(2));
    }    
}
