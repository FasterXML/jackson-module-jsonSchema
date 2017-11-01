package com.fasterxml.jackson.module.jsonSchema.property;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.annotation.JsonSchemaTitle;

/**
 * @author amerritt
 * 
 * @since 4.0
 */
public class SchemaPropertyProcessorTitle implements SchemaPropertyProcessor
{
    @Override
    public void process(JsonSchema schema, BeanProperty prop) {
        if (schema.isSimpleTypeSchema()) {
            JsonSchemaTitle titleAnnotation = prop.getAnnotation(JsonSchemaTitle.class);
            if (titleAnnotation != null) {
                String title = titleAnnotation.value();
                schema.asSimpleTypeSchema().setTitle(title);
            }
        }
    }
}
