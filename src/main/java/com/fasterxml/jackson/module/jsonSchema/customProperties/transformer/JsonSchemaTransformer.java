package com.fasterxml.jackson.module.jsonSchema.customProperties.transformer;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

/**
 * JsonSchemaTransformer defines additional {@link com.fasterxml.jackson.module.jsonSchema.JsonSchema}
 * transformation.
 *
 * @author wololock
 */
public interface JsonSchemaTransformer {
	JsonSchema transform(JsonSchema jsonSchema, BeanProperty beanProperty);
}
