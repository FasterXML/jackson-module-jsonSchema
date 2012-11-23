package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

/**
 * @author jphelan
 */
public interface SchemaProducer {

	public JsonSchema getSchema();
}
