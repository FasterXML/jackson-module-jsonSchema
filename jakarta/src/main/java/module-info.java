// Generated 28-Mar-2019 using Moditect maven plugin
module tools.jackson.module.jsonSchema.jakarta {
    requires jakarta.validation;

    requires com.fasterxml.jackson.annotation;
    requires tools.jackson.core;
    requires tools.jackson.databind;

    exports tools.jackson.module.jsonSchema.jakarta;
    exports tools.jackson.module.jsonSchema.jakarta.annotation;
    exports tools.jackson.module.jsonSchema.jakarta.customProperties;
    exports tools.jackson.module.jsonSchema.jakarta.factories;
    exports tools.jackson.module.jsonSchema.jakarta.types;
    exports tools.jackson.module.jsonSchema.jakarta.validation;

    opens tools.jackson.module.jsonSchema.jakarta;
    opens tools.jackson.module.jsonSchema.jakarta.types;
}
