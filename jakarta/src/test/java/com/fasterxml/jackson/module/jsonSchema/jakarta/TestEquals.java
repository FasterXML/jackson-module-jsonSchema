package com.fasterxml.jackson.module.jsonSchema.jakarta;

import com.fasterxml.jackson.module.jsonSchema.jakarta.types.NullSchema;
import com.fasterxml.jackson.module.jsonSchema.jakarta.types.ObjectSchema;

/**
 * @author Yoann Rodière
 */
public class TestEquals extends SchemaTestBase {
	
	public void testEquals() throws Exception {
		ObjectSchema schema1 = new ObjectSchema();
		ObjectSchema schema2 = new ObjectSchema();
		schema2.getProperties().put("property1", new NullSchema());
		
		assertTrue(!schema1.equals(schema2));
	}

}
