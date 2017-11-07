package com.fasterxml.jackson.module.jsonSchema.property.constraint;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.types.ArraySchema;
import com.fasterxml.jackson.module.jsonSchema.types.StringSchema;

/**
 * @author amerritt
 * 
 * @since 4.0
 */
public class SchemaPropertyProcessorConstraintSize extends SchemaPropertyProcessorConstraint<Size>
{
    @Override
    protected void processNonNullAnnotation(JsonSchema schema, Size sizeAnnotation) {
        if (schema.isArraySchema()) {
            ArraySchema arraySchema = schema.asArraySchema();
            arraySchema.setMaxItems(getMaxSize(sizeAnnotation));
            arraySchema.setMinItems(getMinSize(sizeAnnotation));
        } else if (schema.isStringSchema()) {
            StringSchema stringSchema = schema.asStringSchema();
            stringSchema.setMaxLength(getMaxSize(sizeAnnotation));
            stringSchema.setMinLength(getMinSize(sizeAnnotation));
        }
    }

    private Integer getMaxSize(Size ann) {
        if (ann != null) {
            int value = ann.max();
            if (value != Integer.MAX_VALUE) {
                return value;
            }
        }
        return null;
    }

    private Integer getMinSize(Size ann) {
        if (ann != null) {
            int value = ann.min();
            if (value != 0) {
                return value;
            }
        }
        return null;
    }
}
