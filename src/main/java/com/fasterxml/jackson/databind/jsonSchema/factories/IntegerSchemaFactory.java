package com.fasterxml.jackson.databind.jsonSchema.factories;

import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import com.fasterxml.jackson.databind.jsonSchema.types.IntegerSchema;
import com.fasterxml.jackson.databind.jsonSchema.types.ValueTypeSchema;

public class IntegerSchemaFactory extends ValueTypeSchemaFactory implements
		JsonIntegerFormatVisitor {

	protected IntegerSchema integerSchema;
	
	public IntegerSchemaFactory(SchemaFactory parent) {
		super(parent);
		integerSchema = new IntegerSchema();
	}

	/**
	 * @param provider
	 */
	public IntegerSchemaFactory(SerializerProvider provider) {
		super(provider);
		integerSchema = new IntegerSchema();
	}

	public ValueTypeSchema getValueSchema() {
		return integerSchema;
	}

}
