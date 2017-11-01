package com.fasterxml.jackson.module.jsonSchema.customProperties;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.ObjectVisitor;
import com.fasterxml.jackson.module.jsonSchema.factories.ObjectVisitorDecorator;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.factories.VisitorContext;
import com.fasterxml.jackson.module.jsonSchema.factories.WrapperFactory;
import com.fasterxml.jackson.module.jsonSchema.property.constraint.SchemaPropertyProcessorManagerConstraint;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;

/**
 * @author cponomaryov
 */
public class ValidationSchemaFactoryWrapper extends SchemaFactoryWrapper {

    private SchemaPropertyProcessorManagerConstraint schemaPropertyProcessorManagerConstraint;

    private static class ValidationSchemaFactoryWrapperFactory extends WrapperFactory {
        SchemaPropertyProcessorManagerConstraint schemaPropertyProcessorManagerConstraint;

        ValidationSchemaFactoryWrapperFactory(SchemaPropertyProcessorManagerConstraint schemaPropertyProcessorManagerConstraint) {
            this.schemaPropertyProcessorManagerConstraint = schemaPropertyProcessorManagerConstraint;
        }

        @Override
        public SchemaFactoryWrapper getWrapper(SerializerProvider p) {
            SchemaFactoryWrapper wrapper = new ValidationSchemaFactoryWrapper(schemaPropertyProcessorManagerConstraint);
            wrapper.setProvider(p);
            return wrapper;
        }

        @Override
        public SchemaFactoryWrapper getWrapper(SerializerProvider p, VisitorContext rvc) {
            SchemaFactoryWrapper wrapper = new ValidationSchemaFactoryWrapper(schemaPropertyProcessorManagerConstraint);
            wrapper.setProvider(p);
            wrapper.setVisitorContext(rvc);
            return wrapper;
        }

        @Override
        public SchemaFactoryWrapper getWrapper(SerializerProvider p, VisitorContext rvc, ObjectSchema parent, Class<?> type) {
            SchemaFactoryWrapper wrapper = new ValidationSchemaFactoryWrapper(schemaPropertyProcessorManagerConstraint.createCopyForType(type));
            wrapper.setProvider(p);
            wrapper.setVisitorContext(rvc);
            wrapper.setParent(parent);
            return wrapper;
        }
    }

    public ValidationSchemaFactoryWrapper(Class<?> type, Class<?>... groups) {
        this(new SchemaPropertyProcessorManagerConstraint(type, groups));
    }

    public ValidationSchemaFactoryWrapper(SchemaPropertyProcessorManagerConstraint schemaPropertyProcessorManagerConstraint) {
        super(new ValidationSchemaFactoryWrapperFactory(schemaPropertyProcessorManagerConstraint));
        this.schemaPropertyProcessorManagerConstraint = schemaPropertyProcessorManagerConstraint;
    }

    @Override
    public JsonObjectFormatVisitor expectObjectFormat(JavaType convertedType) {
        return new ObjectVisitorDecorator((ObjectVisitor) super.expectObjectFormat(convertedType)) {
            private JsonSchema getPropertySchema(BeanProperty writer) {
                return ((ObjectSchema) getSchema()).getProperties().get(writer.getName());
            }

            @Override
            public void optionalProperty(BeanProperty writer) throws JsonMappingException {
                super.optionalProperty(writer);
                schemaPropertyProcessorManagerConstraint.process(getPropertySchema(writer), writer);
            }

            @Override
            public void property(BeanProperty writer) throws JsonMappingException {
                super.property(writer);
                schemaPropertyProcessorManagerConstraint.process(getPropertySchema(writer), writer);
            }
        };
    }
}
