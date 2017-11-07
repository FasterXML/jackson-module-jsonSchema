package com.fasterxml.jackson.module.jsonSchema.property.constraint;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;

import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.types.NumberSchema;

/**
 * @author amerritt
 * 
 * @since 4.0
 */
public class SchemaPropertyProcessorConstraintDecimalMin extends SchemaPropertyProcessorConstraint<DecimalMin>
{
    @Override
    protected void processNonNullAnnotation(JsonSchema schema, DecimalMin decimalMinAnnotation) {
        if (schema.isNumberSchema()) {
            NumberSchema numberSchema = schema.asNumberSchema();
            numberSchema.setMinimum(new BigDecimal(decimalMinAnnotation.value()).doubleValue());
        }
    }

}
