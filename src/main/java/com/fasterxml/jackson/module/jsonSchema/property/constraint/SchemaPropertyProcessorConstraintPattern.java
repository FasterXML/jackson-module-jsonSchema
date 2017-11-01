package com.fasterxml.jackson.module.jsonSchema.property.constraint;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.types.StringSchema;

/**
 * @author amerritt
 * 
 * @since 4.0
 */
public class SchemaPropertyProcessorConstraintPattern extends SchemaPropertyProcessorConstraint
{
    @Override
    public void process(JsonSchema schema, BeanProperty prop) {
        if (schema.isStringSchema()) {
            StringSchema stringSchema = schema.asStringSchema();
            stringSchema.setPattern(getStringPattern(prop));
        }
    }

    public String getStringPattern(final BeanProperty prop) {
        Pattern patternAnnotation = getAnnotation(prop, Pattern.class);
        if (patternAnnotation != null) {
            return patternAnnotation.regexp();
    }
        return null;
    }

}
