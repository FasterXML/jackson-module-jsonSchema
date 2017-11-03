package com.fasterxml.jackson.module.jsonSchema.customProperties;

import com.fasterxml.jackson.module.jsonSchema.factories.WrapperFactory.JsonSchemaVersion;
import com.fasterxml.jackson.module.jsonSchema.property.manager.SchemaPropertyProcessorManagerConstraint;

/**
 * @author amerritt
 */
public class ValidationSchemaPropertyProcessorManagerFactoryWrapper extends SchemaPropertyProcessorManagerFactoryWrapper {
    public ValidationSchemaPropertyProcessorManagerFactoryWrapper(Class<?> type, Class<?>... groups) {
        super(new SchemaPropertyProcessorManagerConstraint(type, groups));
    }

    public ValidationSchemaPropertyProcessorManagerFactoryWrapper(JsonSchemaVersion version, Class<?> type, Class<?>... groups) {
        super(new SchemaPropertyProcessorManagerConstraint(type, groups), version);
    }
}
