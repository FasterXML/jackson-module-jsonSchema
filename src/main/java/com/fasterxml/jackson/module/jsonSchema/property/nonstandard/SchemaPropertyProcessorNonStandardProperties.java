package com.fasterxml.jackson.module.jsonSchema.property.nonstandard;

import java.util.Arrays;

import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.annotation.NonStandardProperties;
import com.fasterxml.jackson.module.jsonSchema.property.SchemaPropertyAnnotationProcessor;

/**
 * Used to add items to the schema that may not yet or even may never be part of the standard.
 * 
 * @author amerritt
 * 
 * @since 4.0
 */
public class SchemaPropertyProcessorNonStandardProperties extends SchemaPropertyAnnotationProcessor<NonStandardProperties>
{
    @Override
    protected void processNonNullAnnotation(JsonSchema schema, NonStandardProperties nonStandardPropertiesAnnotation) {
        Arrays.stream(nonStandardPropertiesAnnotation.value()).forEach(nonStandardPropertyAnnotation -> schema.addNonStandardProperty(nonStandardPropertyAnnotation.name(), nonStandardPropertyAnnotation.value()));
    }
}
