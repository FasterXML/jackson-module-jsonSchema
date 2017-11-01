package com.fasterxml.jackson.module.jsonSchema.property.constraint;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.types.NumberSchema;

/**
 * @author amerritt
 * 
 * @since 4.0
 */
public class SchemaPropertyProcessorConstraintDecimalMax extends SchemaPropertyProcessorConstraint
{
    @Override
    public void process(JsonSchema schema, BeanProperty prop) {
        if (schema.isNumberSchema()) {
            NumberSchema numberSchema = schema.asNumberSchema();
            Double numberMaximum = getNumberMaximum(prop);
            //NOTE: Max and DecimalMax set this so to keep from one overwriting the other only set it if the value is not null
            if (numberMaximum != null) {
                numberSchema.setMaximum(numberMaximum);
            }
        }
    }

    public Double getNumberMaximum(BeanProperty prop) {
        DecimalMax decimalMaxAnnotation = getAnnotation(prop, DecimalMax.class);
        return decimalMaxAnnotation != null ? new BigDecimal(decimalMaxAnnotation.value()).doubleValue() : null;
    }
}
