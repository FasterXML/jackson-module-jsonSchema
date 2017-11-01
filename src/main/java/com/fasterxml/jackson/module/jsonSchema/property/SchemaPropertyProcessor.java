package com.fasterxml.jackson.module.jsonSchema.property;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

/**
 * @author amerritt
 * 
 * @since 4.0
 */
public interface SchemaPropertyProcessor {
    void process(JsonSchema schema, BeanProperty prop);
}
