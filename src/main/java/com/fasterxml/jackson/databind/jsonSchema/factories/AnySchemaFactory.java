package com.fasterxml.jackson.databind.jsonSchema.factories;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.fasterxml.jackson.databind.jsonSchema.types.AnySchema;

public class AnySchemaFactory implements JsonAnyFormatVisitor, SchemaProducer {

	protected AnySchema schema;
	protected SchemaFactory parent;
	
	public AnySchemaFactory(SchemaFactory parent, AnySchema schema) {
		this.parent = parent;
		this.schema = schema; 
	}

	public AnySchema getSchema() {
		return schema;
	}

	public SchemaFactory getParent() {
		return parent;
	}

	public void setSchema(AnySchema anySchema) {
		this.schema = anySchema;
	}

	public void setParent(SchemaFactory parent) {
		this.parent = parent;
	}


	

}
