// Generated 28-Mar-2019 using Moditect maven plugin
module com.fasterxml.jackson.module.jsonSchema {
    requires validation.api;

    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    exports com.fasterxml.jackson.module.jsonSchema;
    exports com.fasterxml.jackson.module.jsonSchema.annotation;
    exports com.fasterxml.jackson.module.jsonSchema.customProperties;
    exports com.fasterxml.jackson.module.jsonSchema.factories;
    exports com.fasterxml.jackson.module.jsonSchema.types;
    exports com.fasterxml.jackson.module.jsonSchema.validation;
}
