package tools.jackson.module.jsonSchema.customProperties;

import tools.jackson.databind.BeanProperty;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import tools.jackson.module.jsonSchema.JsonSchema;
import tools.jackson.module.jsonSchema.factories.ObjectVisitor;
import tools.jackson.module.jsonSchema.factories.ObjectVisitorDecorator;
import tools.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import tools.jackson.module.jsonSchema.factories.VisitorContext;
import tools.jackson.module.jsonSchema.factories.WrapperFactory;
import tools.jackson.module.jsonSchema.types.ArraySchema;
import tools.jackson.module.jsonSchema.types.NumberSchema;
import tools.jackson.module.jsonSchema.types.ObjectSchema;
import tools.jackson.module.jsonSchema.types.StringSchema;
import tools.jackson.module.jsonSchema.validation.AnnotationConstraintResolver;
import tools.jackson.module.jsonSchema.validation.ValidationConstraintResolver;

/**
 * @author cponomaryov
 */
public class ValidationSchemaFactoryWrapper extends SchemaFactoryWrapper {

    private ValidationConstraintResolver constraintResolver;

    private static class ValidationSchemaFactoryWrapperFactory extends WrapperFactory {
        @Override
        public SchemaFactoryWrapper getWrapper(SerializationContext c) {
            SchemaFactoryWrapper wrapper = new ValidationSchemaFactoryWrapper();
            wrapper.setContext(c);
            return wrapper;
        }

        @Override
        public SchemaFactoryWrapper getWrapper(SerializationContext c, VisitorContext rvc) {
            SchemaFactoryWrapper wrapper = new ValidationSchemaFactoryWrapper();
            wrapper.setContext(c);
            wrapper.setVisitorContext(rvc);
            return wrapper;
        }
    }

    public ValidationSchemaFactoryWrapper() {
        this(new AnnotationConstraintResolver());
    }

    public ValidationSchemaFactoryWrapper(ValidationConstraintResolver constraintResolver) {
        super(new ValidationSchemaFactoryWrapperFactory());
        this.constraintResolver = constraintResolver;
    }

    @Override
    public JsonObjectFormatVisitor expectObjectFormat(JavaType convertedType) {
        return new ObjectVisitorDecorator((ObjectVisitor) super.expectObjectFormat(convertedType)) {
            private JsonSchema getPropertySchema(BeanProperty writer) {
                return ((ObjectSchema) getSchema()).getProperties().get(writer.getName());
            }

            @Override
            public void optionalProperty(BeanProperty writer) {
                super.optionalProperty(writer);
                addValidationConstraints(getPropertySchema(writer), writer);
            }

            @Override
            public void property(BeanProperty writer) {
                super.property(writer);
                addValidationConstraints(getPropertySchema(writer), writer);
            }
        };
    }

    protected JsonSchema addValidationConstraints(JsonSchema schema, BeanProperty prop) {
        {
            Boolean required = constraintResolver.getRequired(prop);
            if (required != null) {
                schema.setRequired(required);
            }
        }
        if (schema.isArraySchema()) {
            ArraySchema arraySchema = schema.asArraySchema();
            arraySchema.setMaxItems(constraintResolver.getArrayMaxItems(prop));
            arraySchema.setMinItems(constraintResolver.getArrayMinItems(prop));
        } else if (schema.isNumberSchema()) {
            NumberSchema numberSchema = schema.asNumberSchema();
            numberSchema.setMaximum(constraintResolver.getNumberMaximum(prop));
            numberSchema.setMinimum(constraintResolver.getNumberMinimum(prop));
        } else if (schema.isStringSchema()) {
            StringSchema stringSchema = schema.asStringSchema();
            stringSchema.setMaxLength(constraintResolver.getStringMaxLength(prop));
            stringSchema.setMinLength(constraintResolver.getStringMinLength(prop));
            stringSchema.setPattern(constraintResolver.getStringPattern(prop));
        }
        return schema;
    }

}
