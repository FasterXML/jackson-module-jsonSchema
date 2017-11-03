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
import com.fasterxml.jackson.module.jsonSchema.factories.WrapperFactory.JsonSchemaVersion;
import com.fasterxml.jackson.module.jsonSchema.property.manager.SchemaPropertyProcessorManagerApi;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;

/**
 * @author amerritt
 */
public class SchemaPropertyProcessorManagerFactoryWrapper extends SchemaFactoryWrapper {

    private SchemaPropertyProcessorManagerApi schemaPropertyProcessorManager;

    public SchemaPropertyProcessorManagerFactoryWrapper(SchemaPropertyProcessorManagerApi schemaPropertyProcessorManager) {
        this(schemaPropertyProcessorManager, JsonSchemaVersion.DRAFT_V4);
    }

    public SchemaPropertyProcessorManagerFactoryWrapper(SchemaPropertyProcessorManagerApi schemaPropertyProcessorManager, JsonSchemaVersion version) {
        super(new ValidationSchemaFactoryWrapperFactory(schemaPropertyProcessorManager, version));
        this.schemaPropertyProcessorManager = schemaPropertyProcessorManager;
    }

    @Override
    public JsonObjectFormatVisitor expectObjectFormat(JavaType convertedType) {
        ObjectVisitor objectVisitor = (ObjectVisitor) super.expectObjectFormat(convertedType);
        schemaPropertyProcessorManager.process(objectVisitor.getSchema(), convertedType);
        return new ObjectVisitorDecorator(objectVisitor) {
            private JsonSchema getPropertySchema(BeanProperty writer) {
                return ((ObjectSchema) getSchema()).getProperties().get(writer.getName());
            }

            @Override
            public void optionalProperty(BeanProperty writer) throws JsonMappingException {
                super.optionalProperty(writer);
                schemaPropertyProcessorManager.process(getPropertySchema(writer), writer);
            }

            @Override
            public void property(BeanProperty writer) throws JsonMappingException {
                super.property(writer);
                schemaPropertyProcessorManager.process(getPropertySchema(writer), writer);
            }
        };
    }

    private static class ValidationSchemaFactoryWrapperFactory extends WrapperFactory {
        SchemaPropertyProcessorManagerApi schemaPropertyProcessorManager;

        ValidationSchemaFactoryWrapperFactory(SchemaPropertyProcessorManagerApi schemaPropertyProcessorManager, JsonSchemaVersion version) {
            super(version);
            this.schemaPropertyProcessorManager = schemaPropertyProcessorManager;
        }

        @Override
        public SchemaFactoryWrapper getWrapper(SerializerProvider p) {
            SchemaFactoryWrapper wrapper = new SchemaPropertyProcessorManagerFactoryWrapper(schemaPropertyProcessorManager, getVersion());
            wrapper.setProvider(p);
            return wrapper;
        }

        @Override
        public SchemaFactoryWrapper getWrapper(SerializerProvider p, VisitorContext rvc) {
            SchemaFactoryWrapper wrapper = new SchemaPropertyProcessorManagerFactoryWrapper(schemaPropertyProcessorManager, getVersion());
            wrapper.setProvider(p);
            wrapper.setVisitorContext(rvc);
            return wrapper;
        }

        @Override
        public SchemaFactoryWrapper getWrapper(SerializerProvider p, VisitorContext rvc, ObjectSchema parent, Class<?> type) {
            SchemaFactoryWrapper wrapper = new SchemaPropertyProcessorManagerFactoryWrapper(schemaPropertyProcessorManager.createCopyForType(type), getVersion());
            wrapper.setProvider(p);
            wrapper.setVisitorContext(rvc);
            wrapper.setParent(parent);
            return wrapper;
        }
    }
}
