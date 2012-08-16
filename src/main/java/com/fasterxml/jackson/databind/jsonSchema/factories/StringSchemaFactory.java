package com.fasterxml.jackson.databind.jsonSchema.factories;

import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.databind.jsonSchema.types.StringSchema;
import com.fasterxml.jackson.databind.jsonSchema.types.ValueTypeSchema;

public class StringSchemaFactory extends ValueTypeSchemaFactory 
	implements JsonStringFormatVisitor{

	protected StringSchema stringSchema;
	
	public StringSchemaFactory(SchemaFactory parent) {
		super(parent);
		stringSchema = new StringSchema();
	}

	/**
	 * @param provider
	 */
	public StringSchemaFactory(SerializerProvider provider) {
		super(provider);
		stringSchema = new StringSchema();
	}

	public ValueTypeSchema getValueSchema() {
		return stringSchema;
	}

}
