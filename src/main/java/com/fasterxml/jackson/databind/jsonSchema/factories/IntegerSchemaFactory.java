package com.fasterxml.jackson.databind.jsonSchema.factories;

import java.util.Set;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.databind.jsonSchema.types.IntegerSchema;

public class IntegerSchemaFactory implements JsonIntegerFormatVisitor {

	protected ValueTypeSchemaFactory parent;
	protected IntegerSchema schema;
	
	public IntegerSchemaFactory(ValueTypeSchemaFactory parent, IntegerSchema schema) {
		this.parent = parent;
		this.schema = schema;
	}

	public ValueTypeSchemaFactory getParent() {
		return parent;
	}

	public void setParent(ValueTypeSchemaFactory parent) {
		this.parent = parent;
	}

	public IntegerSchema getIntegerSchema() {
		return schema;
	}

	public void setIntegerSchema(IntegerSchema integerSchema) {
		this.schema = integerSchema;
	}

	public void format(JsonValueFormat format) {
		parent.format(format);
	}

	public void enumTypes(Set<String> enums) {
		parent.enumTypes(enums);
	}



}
