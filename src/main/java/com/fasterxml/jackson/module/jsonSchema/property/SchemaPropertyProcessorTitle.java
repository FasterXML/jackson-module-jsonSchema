package com.fasterxml.jackson.module.jsonSchema.property;

import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.annotation.JsonSchemaTitle;

/**
 * @author amerritt
 * 
 * @since 4.0
 */
public class SchemaPropertyProcessorTitle extends SchemaPropertyAnnotationProcessor<JsonSchemaTitle>
{
    @Override
    protected void processNonNullAnnotation(JsonSchema schema, JsonSchemaTitle titleAnnotation) {
        if (schema.isSimpleTypeSchema()) {
            String title = titleAnnotation.value();
            schema.asSimpleTypeSchema().setTitle(title);
        }

    }
}
