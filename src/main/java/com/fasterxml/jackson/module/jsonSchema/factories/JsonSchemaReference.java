package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

public class JsonSchemaReference implements JsonSchemaProducer
{
    protected final SerializerProvider provider;
    protected final JsonSchema schema;

    public JsonSchemaReference(SerializerProvider provider, JsonSchema schema) {
        this.provider = provider;
        this.schema = schema;
    }

    public SerializerProvider getProvider() {
        return provider;
    }
	
    public JsonSchema getSchema() {
        return schema;
    }
}
