package com.fasterxml.jackson.module.jsonSchema.factories;

import java.util.Set;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonNumberFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.module.jsonSchema.types.NumberSchema;

public class NumberSchemaFactory implements JsonNumberFormatVisitor, SchemaProducer {

	protected ValueTypeSchemaFactory parent;
	protected NumberSchema schema;
	
	public NumberSchemaFactory(ValueTypeSchemaFactory parent, NumberSchema schema) {
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

	public NumberSchema getSchema() {
		return schema;
	}

	public void setParent(ValueTypeSchemaFactory parent) {
		this.parent = parent;
	}

	public void setSchema(NumberSchema schema) {
		this.schema = schema;
	}	

}
