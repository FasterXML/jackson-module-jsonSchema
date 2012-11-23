package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.module.jsonSchema.types.JsonSchema;

/**
 * @author jphelan
 */
public interface SchemaProducer {

	public JsonSchema getSchema();
}
