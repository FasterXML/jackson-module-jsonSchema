package com.fasterxml.jackson.module.jsonSchema.property.constraint;

import javax.validation.constraints.Max;

import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.types.NumberSchema;

/**
 * @author amerritt
 * 
 * @since 4.0
 */
public class SchemaPropertyProcessorConstraintMax extends SchemaPropertyProcessorConstraint<Max>
{

    @Override
    protected void processNonNullAnnotation(JsonSchema schema, Max maxAnnotation) {
        if (schema.isNumberSchema()) {
            NumberSchema numberSchema = schema.asNumberSchema();
            numberSchema.setMaximum((double)maxAnnotation.value());
        }
    }
}
