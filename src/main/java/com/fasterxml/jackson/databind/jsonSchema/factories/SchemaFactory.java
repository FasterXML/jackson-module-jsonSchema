package com.fasterxml.jackson.databind.jsonSchema.factories;

import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonSchema.types.JsonSchema;

public class SchemaFactory {

	
	protected SerializerProvider provider;
	protected JsonSchema schema;

	
	/**
	 * @param provider 
	 * @param schema 
	 * 
	 */
	public SchemaFactory(SerializerProvider provider, JsonSchema schema) {
		this.provider = provider;
		this.schema = schema;
	}
	/**
	 * {@link SchemaFactory#provider}
	 * @param provider the provider to set
	 */
	public void setProvider(SerializerProvider provider) {
		this.provider = provider;
	}
	
	public SerializerProvider getProvider() {
		return provider;
	}

	public JsonSchema getSchema() {
		return schema;
	}

	public void setSchema(JsonSchema schema) {
		this.schema = schema;
	}
	
}
