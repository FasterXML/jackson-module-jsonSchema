package com.fasterxml.jackson.databind.jsonSchema.factories;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonNullFormatVisitor;
import com.fasterxml.jackson.databind.jsonSchema.types.NullSchema;

public class NullSchemaFactory implements JsonNullFormatVisitor {

	protected SchemaFactory parent;
	protected NullSchema schema;
	
	public NullSchemaFactory(SchemaFactory parent, NullSchema schema) {
		this.parent = parent;
		this.schema = schema;
	}

	public SchemaFactory getParent() {
		return parent;
	}

	public void setParent(SchemaFactory parent) {
		this.parent = parent;
	}

	public NullSchema getSchema() {
		return schema;
	}

	public void setSchema(NullSchema schema) {
		this.schema = schema;
	}


}
