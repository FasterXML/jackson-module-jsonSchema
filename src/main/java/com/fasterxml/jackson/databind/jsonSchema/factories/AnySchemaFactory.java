package com.fasterxml.jackson.databind.jsonSchema.factories;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.fasterxml.jackson.databind.jsonSchema.types.AnySchema;

public class AnySchemaFactory implements JsonAnyFormatVisitor {

	protected SchemaFactory parent;
	protected AnySchema anySchema;
	
	public AnySchemaFactory(SchemaFactory parent, AnySchema schema) {
		this.parent = parent;
		this.anySchema = schema; 
	}

	public SchemaFactory getParent() {
		return parent;
	}

	public void setParent(SchemaFactory parent) {
		this.parent = parent;
	}

	public AnySchema getAnySchema() {
		return anySchema;
	}

	public void setAnySchema(AnySchema anySchema) {
		this.anySchema = anySchema;
	}


	

}
