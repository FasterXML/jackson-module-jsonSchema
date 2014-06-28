package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.module.jsonSchema.validation.AnnotationConstraintResolver;
import com.fasterxml.jackson.module.jsonSchema.validation.ValidationConstraintResolver;

/**
 * Exists to supply {@link SchemaFactoryWrapper} or its subclasses
 * to nested schema factories.
 * @author jphelan
 */
public class WrapperFactory {

    private ValidationConstraintResolver validationConstraintResolver;

    public WrapperFactory() {
        this(new AnnotationConstraintResolver());
    }

    public WrapperFactory(ValidationConstraintResolver validationConstraintResolver) {
        this.validationConstraintResolver = validationConstraintResolver;
    }

    public ValidationConstraintResolver getValidationConstraintResolver() {
        return validationConstraintResolver;
    }

    public SchemaFactoryWrapper getWrapper(SerializerProvider provider) {
		return new SchemaFactoryWrapper(provider);
	}

    public SchemaFactoryWrapper getWrapper(SerializerProvider provider, VisitorContext rvc) {
        SchemaFactoryWrapper wrapper = new SchemaFactoryWrapper(provider);
        wrapper.setVisitorContext(rvc);
        return wrapper;
    }
}