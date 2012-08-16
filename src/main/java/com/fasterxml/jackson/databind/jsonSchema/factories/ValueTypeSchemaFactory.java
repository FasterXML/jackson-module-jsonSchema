package com.fasterxml.jackson.databind.jsonSchema.factories;

import java.util.Set;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormatVisitor;
import com.fasterxml.jackson.databind.jsonSchema.types.ValueTypeSchema;

public class ValueTypeSchemaFactory implements JsonValueFormatVisitor {

	protected SchemaFactory parent; 
	protected ValueTypeSchema schema;
	
	protected ValueTypeSchemaFactory(SchemaFactory parent, ValueTypeSchema schema) {
		this.parent = parent;
		this.schema = schema;
	}

	public SchemaFactory getParent() {
		return parent;
	}

	public void setParent(SchemaFactory parent) {
		this.parent = parent;
	}

	public ValueTypeSchema getSchema() {
		return schema;
	}

	public void setSchema(ValueTypeSchema schema) {
		this.schema = schema;
	}

	public void format(JsonValueFormat format) {
		getSchema().setFormat(format);

	}

	public void enumTypes(Set<String> enums) {
		getSchema().setEnums(enums);

	}

}
