package com.fasterxml.jackson.module.jsonSchema.failing;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.fasterxml.jackson.module.jsonSchema.SchemaTestBase;

public class TestUnwrapping extends SchemaTestBase
{
    static class UnwrappingRoot {
        @JsonUnwrapped(prefix="ignored.")
        public int age;

        private Name _name;
        
        @JsonUnwrapped(prefix="name.")
        public Name getName() { 
            return _name;
        }
    }

    static class Name {
        @JsonUnwrapped(prefix="ignoreNonObject.") // is ignored in serialization
        public String first, last;
        @JsonUnwrapped(prefix="deeper.")
        public More more;
    }

    static class More {
    	public Double deepest;
    }

    /*
    /**********************************************************
    /* Unit tests, success
    /**********************************************************
     */

    private final ObjectMapper MAPPER = objectMapper();
    private final ObjectWriter WRITER = MAPPER.writerWithDefaultPrettyPrinter();

    public void testUnwrapping()  throws Exception
    {
        JsonSchemaGenerator generator = new JsonSchemaGenerator(MAPPER);
        JsonSchema schema = generator.generateSchema(UnwrappingRoot.class);
        //System.out.println(WRITER.writeValueAsString(schema));

        Map<String, JsonSchema> properties = schema.asObjectSchema().getProperties();
        assertEquals(4, properties.keySet().size());
        assertNotNull(properties.get("age").asIntegerSchema());
        assertNotNull(properties.get("name.first").asStringSchema());
        assertNotNull(properties.get("name.last").asStringSchema());
        assertNotNull(properties.get("name.deeper.deepest").asNumberSchema());
        

        UnwrappingRoot example = makeExample();
        String json = WRITER.writeValueAsString(example);
        //System.out.println(json); //helpful debugging output
        Set<String> expectedKeySet = extractKeysFromJson(json);
        //System.out.println(expectedKeySet);
        assertEquals(expectedKeySet, properties.keySet());
    }

	private HashSet<String> extractKeysFromJson(String json) {
		String[] keys = json
				.replaceAll("[{}]","")      //remove begin/end of json object
				.replaceAll("\"","")        //remove quotes
				.replaceAll(":[^,]*,?", "") //get rid of value part 
        		.trim().split("\\s+");      //split by whitespace
        return new HashSet<String>(Arrays.asList(keys));
	}

	private UnwrappingRoot makeExample() {
		UnwrappingRoot value = new UnwrappingRoot();
        value.age = 33;
        value._name = new Name();
        value._name.first = "firstName";
        value._name.last = "lastName";
        value._name.more = new More();
        value._name.more.deepest = 3.14;
		return value;
	}
}
