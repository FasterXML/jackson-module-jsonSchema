// Generated 28-Mar-2019 using Moditect maven plugin
module tools.jackson.module.jsonSchema {
    requires validation.api;

    requires com.fasterxml.jackson.annotation;
    requires tools.jackson.core;
    requires tools.jackson.databind;

    exports tools.jackson.module.jsonSchema;
    exports tools.jackson.module.jsonSchema.annotation;
    exports tools.jackson.module.jsonSchema.customProperties;
    exports tools.jackson.module.jsonSchema.factories;
    exports tools.jackson.module.jsonSchema.types;
    exports tools.jackson.module.jsonSchema.validation;

    opens tools.jackson.module.jsonSchema;
    opens tools.jackson.module.jsonSchema.types;
}
