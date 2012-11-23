package com.fasterxml.jackson.module.jsonSchema.factories;

import java.util.Set;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonBooleanFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.module.jsonSchema.types.BooleanSchema;

public class BooleanSchemaFactory implements JsonBooleanFormatVisitor, SchemaProducer {

	protected ValueTypeSchemaFactory parent;
	protected BooleanSchema schema;
	
	public BooleanSchemaFactory(ValueTypeSchemaFactory parent, BooleanSchema schema) {
		this.parent = parent;
		this.schema = schema;
	}

	public void enumTypes(Set<String> enums) {
		parent.enumTypes(enums);
	}

	public void format(JsonValueFormat format) {
		parent.format(format);
	}

	public ValueTypeSchemaFactory getParent() {
		return parent;
	}

	public BooleanSchema getSchema() {
		return schema;
	}

	public void setParent(ValueTypeSchemaFactory parent) {
		this.parent = parent;
	}

	public void setSchema(BooleanSchema schema) {
		this.schema = schema;
	}
	

}
