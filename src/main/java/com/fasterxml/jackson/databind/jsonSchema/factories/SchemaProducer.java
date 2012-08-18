package com.fasterxml.jackson.databind.jsonSchema.factories;

import com.fasterxml.jackson.databind.jsonSchema.types.JsonSchema;

/**
 * @author jphelan
 *
 */
public interface SchemaProducer {

	public JsonSchema getSchema();
}
