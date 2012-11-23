package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.databind.SerializerProvider;

public abstract class StructuredTypeVisitor implements JsonSchemaProducer
{
    protected SerializerProvider provider;

    protected StructuredTypeVisitor(SerializerProvider provider)
    {
        this.provider = provider;
    }

    public SerializerProvider getProvider() {
        return provider;
    }

    public void setProvider(SerializerProvider p) {
        provider = p;
    }

}

