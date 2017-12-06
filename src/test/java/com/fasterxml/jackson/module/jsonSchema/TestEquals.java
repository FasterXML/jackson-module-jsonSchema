package com.fasterxml.jackson.module.jsonSchema;

import com.fasterxml.jackson.module.jsonSchema.factories.WrapperFactory.JsonSchemaVersion;
import com.fasterxml.jackson.module.jsonSchema.types.NullSchema;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;

/**
 * @author Yoann Rodière
 */
public class TestEquals extends SchemaTestBase {
	
    public void testEquals() throws Exception {
        ObjectSchema schema1 = new ObjectSchema(JsonSchemaVersion.DRAFT_V4, true);
        ObjectSchema schema2 = new ObjectSchema(JsonSchemaVersion.DRAFT_V4, true);

        assertTrue(schema1.equals(schema2));
    }

    public void testNotEquals_ExtraProperty() throws Exception {
        ObjectSchema schema1 = new ObjectSchema(JsonSchemaVersion.DRAFT_V4, true);
        ObjectSchema schema2 = new ObjectSchema(JsonSchemaVersion.DRAFT_V4, true);
        schema2.getProperties().put("property1", new NullSchema(JsonSchemaVersion.DRAFT_V4));
		
        assertFalse(schema1.equals(schema2));
    }

    public void testNotEquals_DifferentVersions() throws Exception {
        ObjectSchema schema1 = new ObjectSchema(JsonSchemaVersion.DRAFT_V3, true);
        ObjectSchema schema2 = new ObjectSchema(JsonSchemaVersion.DRAFT_V4, true);

        assertFalse(schema1.equals(schema2));
	}

}
