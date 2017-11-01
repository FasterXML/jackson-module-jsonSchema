package com.fasterxml.jackson.module.jsonSchema.property.constraint;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.types.NumberSchema;

/**
 * @author amerritt
 * 
 * @since 4.0
 */
public class SchemaPropertyProcessorConstraintDecimalMin extends SchemaPropertyProcessorConstraint
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
        DecimalMin decimalMinAnnotation = getAnnotation(prop, DecimalMin.class);
        return decimalMinAnnotation != null ? new BigDecimal(decimalMinAnnotation.value()).doubleValue() : null;
    }

}
