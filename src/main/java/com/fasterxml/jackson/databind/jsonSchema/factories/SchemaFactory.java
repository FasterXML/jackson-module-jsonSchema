package com.fasterxml.jackson.databind.jsonSchema.factories;

import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonSchema.types.JsonSchema;

public abstract class SchemaFactory {

	
	protected SerializerProvider provider;
	

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

	public abstract JsonSchema getSchema();
	
}
