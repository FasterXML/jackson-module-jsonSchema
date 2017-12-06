package com.fasterxml.jackson.module.jsonSchema.property.constraint;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.WrapperFactory.JsonSchemaVersion;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;
import com.fasterxml.jackson.module.jsonSchema.types.SimpleTypeSchema;

/**
 * @author amerritt
 * 
 * @since 4.0
 */
public class SchemaPropertyProcessorConstraintNotNull extends SchemaPropertyProcessorConstraint<NotNull>
{
    @Override
    protected void processNonNullAnnotation(JsonSchema schema, NotNull annotation) {
        if (schema.isSimpleTypeSchema()) {
            SimpleTypeSchema sts = schema.asSimpleTypeSchema();
            if (JsonSchemaVersion.DRAFT_V3.equals(schema.getVersion())) {
                schema.setRequired(true);
            } else {
                ObjectSchema parent = sts.getParent();
                parent.getRequiredPropertyNames().add(propertyName);
            }
        }
    }
}
