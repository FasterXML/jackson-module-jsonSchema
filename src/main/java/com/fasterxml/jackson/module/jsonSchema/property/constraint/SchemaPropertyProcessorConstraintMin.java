package com.fasterxml.jackson.module.jsonSchema.property.constraint;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.types.NumberSchema;

/**
 * @author amerritt
 * 
 * @since 4.0
 */
public class SchemaPropertyProcessorConstraintMin extends SchemaPropertyProcessorConstraint
{
    @Override
    public void process(JsonSchema schema, BeanProperty prop) {
        if (schema.isNumberSchema()) {
            NumberSchema numberSchema = schema.asNumberSchema();
            Double numberMinimum = getNumberMinimum(prop);
            //NOTE: Min and DecimalMin set this so to keep from one overwriting the other only set it if the value is not null
            if (numberMinimum != null) {
                numberSchema.setMinimum(numberMinimum);
            }
        }
    }

    public Double getNumberMinimum(BeanProperty prop) {
        Min minAnnotation = getAnnotation(prop, Min.class);
        return minAnnotation != null ? (double)minAnnotation.value() : null;
    }

}
