package com.fasterxml.jackson.module.jsonSchema.property.nonstandard;

import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.annotation.NonStandardProperty;
import com.fasterxml.jackson.module.jsonSchema.property.SchemaPropertyAnnotationProcessor;

/**
 * Used to add items to the schema that may not yet or even may never be part of the standard.
 * 
 * @author amerritt
 * 
 * @since 4.0
 */
public class SchemaPropertyProcessorNonStandardProperty extends SchemaPropertyAnnotationProcessor<NonStandardProperty>
{
    @Override
    protected void processNonNullAnnotation(JsonSchema schema, NonStandardProperty nonStandardPropertyAnnotation) {
        schema.addNonStandardProperty(nonStandardPropertyAnnotation.name(), nonStandardPropertyAnnotation.value());
    }
}
