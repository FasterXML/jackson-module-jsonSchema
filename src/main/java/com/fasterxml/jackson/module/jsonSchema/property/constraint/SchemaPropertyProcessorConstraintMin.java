package com.fasterxml.jackson.module.jsonSchema.property.constraint;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.types.NumberSchema;

/**
 * @author amerritt
 * 
 * @since 4.0
 */
public class SchemaPropertyProcessorConstraintMin extends SchemaPropertyProcessorConstraint<Min>
{
    @Override
    protected void processNonNullAnnotation(JsonSchema schema, Min minAnnotation) {
        if (schema.isNumberSchema()) {
            NumberSchema numberSchema = schema.asNumberSchema();
            numberSchema.setMinimum((double)minAnnotation.value());
        }
    }

}
