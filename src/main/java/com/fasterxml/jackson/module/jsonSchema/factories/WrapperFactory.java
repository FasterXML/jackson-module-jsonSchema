package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;

/**
 * Exists to supply {@link SchemaFactoryWrapper} or its subclasses
 * to nested schema factories.
 * @author jphelan
 */
public class WrapperFactory
{
    public SchemaFactoryWrapper getWrapper(SerializerProvider provider) {
        return new SchemaFactoryWrapper(provider);
    }

    public SchemaFactoryWrapper getWrapper(SerializerProvider provider, VisitorContext rvc) {
        SchemaFactoryWrapper wrapper = new SchemaFactoryWrapper(provider);
        wrapper.setVisitorContext(rvc);
        return wrapper;
    }

    public SchemaFactoryWrapper getWrapper(SerializerProvider provider, VisitorContext rvc, ObjectSchema parent, Class<?> type) {
        SchemaFactoryWrapper wrapper = new SchemaFactoryWrapper(provider);
        wrapper.setVisitorContext(rvc);
        wrapper.setParent(parent);
        wrapper.setType(type);
        return wrapper;
    }
}