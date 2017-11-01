package com.fasterxml.jackson.module.jsonSchema.property.constraint;

import javax.validation.constraints.Max;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.types.NumberSchema;

/**
 * @author amerritt
 * 
 * @since 4.0
 */
public class SchemaPropertyProcessorConstraintMax extends SchemaPropertyProcessorConstraint
{
    @Override
    public void process(JsonSchema schema, BeanProperty prop) {
        if (schema.isNumberSchema()) {
            NumberSchema numberSchema = schema.asNumberSchema();
            numberSchema.setMaximum(getNumberMaximum(prop));
        }
    }

    public Double getNumberMaximum(BeanProperty prop) {
        Max maxAnnotation = getAnnotation(prop, Max.class);
        return maxAnnotation != null ? (double)maxAnnotation.value() : null;
    }
}
