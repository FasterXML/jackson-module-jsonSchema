package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

public class JsonSchemaReference implements JsonSchemaProducer
{
    protected final JsonSchema schema;

    public JsonSchemaReference(JsonSchema schema) {
        this.schema = schema;
    }
	
    public JsonSchema getSchema() {
        return schema;
    }
}
