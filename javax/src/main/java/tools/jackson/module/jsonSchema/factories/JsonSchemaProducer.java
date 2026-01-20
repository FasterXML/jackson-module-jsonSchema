package tools.jackson.module.jsonSchema.factories;

import tools.jackson.module.jsonSchema.JsonSchema;

/**
 * Interface for objects that produce {@link JsonSchema} instances;
 * implemented by visitors.
 * 
 * @author jphelan
 */
public interface JsonSchemaProducer {
	public JsonSchema getSchema();
}
