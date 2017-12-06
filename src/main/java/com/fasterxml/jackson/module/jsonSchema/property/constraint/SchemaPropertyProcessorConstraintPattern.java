package com.fasterxml.jackson.module.jsonSchema.property.constraint;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.types.StringSchema;

/**
 * @author amerritt
 * 
 * @since 4.0
 */
public class SchemaPropertyProcessorConstraintPattern extends SchemaPropertyProcessorConstraint<Pattern>
{
    @Override
    protected void processNonNullAnnotation(JsonSchema schema, Pattern patternAnnotation) {
        if (schema.isStringSchema()) {
            StringSchema stringSchema = schema.asStringSchema();
            stringSchema.setPattern(patternAnnotation.regexp());
        }
    }

}
