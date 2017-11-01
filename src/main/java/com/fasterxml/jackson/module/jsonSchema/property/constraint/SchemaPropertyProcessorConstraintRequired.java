package com.fasterxml.jackson.module.jsonSchema.property.constraint;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;
import com.fasterxml.jackson.module.jsonSchema.types.SimpleTypeSchema;

/**
 * @author amerritt
 * 
 * @since 4.0
 */
public class SchemaPropertyProcessorConstraintRequired extends SchemaPropertyProcessorConstraint
{
    @Override
    public void process(JsonSchema schema, BeanProperty prop) {
        if (schema.isSimpleTypeSchema()) {
            SimpleTypeSchema sts = schema.asSimpleTypeSchema();
            ObjectSchema parent = sts.getParent();
            Boolean required = getRequired(prop);
            if (required != null) {
                parent.getRequired().add(prop.getName());
            }
        }
    }

    public Boolean getRequired(BeanProperty prop) {
        NotNull notNull = getAnnotation(prop, NotNull.class);
        return notNull != null ? true : null;
    }
}
