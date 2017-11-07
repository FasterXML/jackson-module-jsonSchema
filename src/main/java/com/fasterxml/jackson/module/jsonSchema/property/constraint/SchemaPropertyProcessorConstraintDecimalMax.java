package com.fasterxml.jackson.module.jsonSchema.property.constraint;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;

import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.types.NumberSchema;

/**
 * @author amerritt
 * 
 * @since 4.0
 */
public class SchemaPropertyProcessorConstraintDecimalMax extends SchemaPropertyProcessorConstraint<DecimalMax>
{
    @Override
    protected void processNonNullAnnotation(JsonSchema schema, DecimalMax decimalMaxAnnotation) {
        if (schema.isNumberSchema()) {
            NumberSchema numberSchema = schema.asNumberSchema();
            numberSchema.setMaximum(new BigDecimal(decimalMaxAnnotation.value()).doubleValue());
        }
    }
}
