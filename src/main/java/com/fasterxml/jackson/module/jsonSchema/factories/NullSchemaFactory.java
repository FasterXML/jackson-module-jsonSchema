package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonNullFormatVisitor;
import com.fasterxml.jackson.module.jsonSchema.types.NullSchema;

public class NullSchemaFactory implements JsonNullFormatVisitor, SchemaProducer {

	protected SchemaFactory parent;
	protected NullSchema schema;
	
	public NullSchemaFactory(SchemaFactory parent, NullSchema schema) {
		this.parent = parent;
		this.schema = schema;
	}

	public SchemaFactory getParent() {
		return parent;
	}

	public NullSchema getSchema() {
		return schema;
	}

	public void setParent(SchemaFactory parent) {
		this.parent = parent;
	}

	public void setSchema(NullSchema schema) {
		this.schema = schema;
	}


}
