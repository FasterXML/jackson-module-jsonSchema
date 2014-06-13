package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Exists to supply {@link SchemaFactoryWrapper} or its subclasses
 * to nested schema factories.
 * @author jphelan
 */
public class WrapperFactory {
	
	public SchemaFactoryWrapper getWrapper(SerializerProvider provider) {
		return new SchemaFactoryWrapper(provider);
	}

    public SchemaFactoryWrapper getWrapper(SerializerProvider provider, RecursiveVisitorContext rvc) {
        return new SchemaFactoryWrapper(provider, rvc);
    }
}