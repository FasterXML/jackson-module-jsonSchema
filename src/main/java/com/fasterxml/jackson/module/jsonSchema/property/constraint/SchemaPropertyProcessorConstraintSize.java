package com.fasterxml.jackson.module.jsonSchema.property.constraint;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.types.ArraySchema;
import com.fasterxml.jackson.module.jsonSchema.types.StringSchema;

/**
 * @author amerritt
 * 
 * @since 4.0
 */
public class SchemaPropertyProcessorConstraintSize extends SchemaPropertyProcessorConstraint
{
    @Override
    public void process(JsonSchema schema, BeanProperty prop) {
        if (schema.isArraySchema()) {
            ArraySchema arraySchema = schema.asArraySchema();
            arraySchema.setMaxItems(getMaxSize(prop));
            arraySchema.setMinItems(getMinSize(prop));
        } else if (schema.isStringSchema()) {
            StringSchema stringSchema = schema.asStringSchema();
            stringSchema.setMaxLength(getMaxSize(prop));
            stringSchema.setMinLength(getMinSize(prop));
        }
    }

    private Integer getMaxSize(BeanProperty prop) {
        Size ann = getSizeAnnotation(prop);
        if (ann != null) {
            int value = ann.max();
            if (value != Integer.MAX_VALUE) {
                return value;
            }
        }
        return null;
    }

    private Integer getMinSize(BeanProperty prop) {
        Size ann = getSizeAnnotation(prop);
        if (ann != null) {
            int value = ann.min();
            if (value != 0) {
                return value;
            }
        }
        return null;
    }

    private Size getSizeAnnotation(BeanProperty prop) {
        return getAnnotation(prop, Size.class);
    }
}
