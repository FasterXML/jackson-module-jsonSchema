package com.fasterxml.jackson.module.jsonSchema.factories;

import java.util.Set;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.module.jsonSchema.types.IntegerSchema;

public class IntegerSchemaFactory implements JsonIntegerFormatVisitor, SchemaProducer {

	protected ValueTypeSchemaFactory parent;
	protected IntegerSchema schema;
	
	public IntegerSchemaFactory(ValueTypeSchemaFactory parent, IntegerSchema schema) {
		this.parent = parent;
		this.schema = schema;
	}

	public void enumTypes(Set<String> enums) {
		parent.enumTypes(enums);
	}

	public void format(JsonValueFormat format) {
		parent.format(format);
	}

	public IntegerSchema getSchema() {
		return schema;
	}

	public ValueTypeSchemaFactory getParent() {
		return parent;
	}

	public void setSchema(IntegerSchema integerSchema) {
		this.schema = integerSchema;
	}

	public void setParent(ValueTypeSchemaFactory parent) {
		this.parent = parent;
	}



}
