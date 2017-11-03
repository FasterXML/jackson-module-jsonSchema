package com.fasterxml.jackson.module.jsonSchema.property;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
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
        JsonSchemaTitle titleAnnotation = prop.getAnnotation(JsonSchemaTitle.class);
        setTitle(schema, titleAnnotation);
    }

    @Override
    public void process(JsonSchema schema, JavaType type) {
        JsonSchemaTitle titleAnnotation = type.getRawClass().getAnnotation(JsonSchemaTitle.class);
        setTitle(schema, titleAnnotation);
    }

    private void setTitle(JsonSchema schema, JsonSchemaTitle titleAnnotation) {
        if (schema.isSimpleTypeSchema() && titleAnnotation != null) {
                String title = titleAnnotation.value();
                schema.asSimpleTypeSchema().setTitle(title);
        }
    }
}
