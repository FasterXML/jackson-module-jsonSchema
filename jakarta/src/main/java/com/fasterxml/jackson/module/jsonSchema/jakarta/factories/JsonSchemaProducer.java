package com.fasterxml.jackson.module.jsonSchema.jakarta.factories;

import com.fasterxml.jackson.module.jsonSchema.jakarta.JsonSchema;

/**
 * Interface for objects that produce {@link JsonSchema} instances;
 * implemented by visitors.
 * 
 * @author jphelan
 */
public interface JsonSchemaProducer {
	public JsonSchema getSchema();
}
