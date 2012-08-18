package com.fasterxml.jackson.databind.jsonSchema.factories;

import java.util.Set;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.databind.jsonSchema.types.StringSchema;

public class StringSchemaFactory implements JsonStringFormatVisitor, SchemaProducer{

	protected ValueTypeSchemaFactory parent;
	protected StringSchema schema;
	
	public StringSchemaFactory(ValueTypeSchemaFactory parent, StringSchema schema) {
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

	public StringSchema getSchema() {
		return schema;
	}

	public void setParent(ValueTypeSchemaFactory parent) {
		this.parent = parent;
	}

	public void setSchema(StringSchema schema) {
		this.schema = schema;
	}


}
