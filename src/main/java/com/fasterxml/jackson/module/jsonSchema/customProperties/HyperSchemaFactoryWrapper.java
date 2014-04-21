package com.fasterxml.jackson.module.jsonSchema.customProperties;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.annotation.HyperSchema;
import com.fasterxml.jackson.module.jsonSchema.annotation.Link;
import com.fasterxml.jackson.module.jsonSchema.factories.ArrayVisitor;
import com.fasterxml.jackson.module.jsonSchema.factories.ObjectVisitor;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.factories.WrapperFactory;
import com.fasterxml.jackson.module.jsonSchema.types.LinkDescriptionObject;

/**
 * Adds a hyperlink to object schema, either root level or nested. Generally
 * useful for writing additional properties to a schema.
 *
 * @author mavarazy
 */
public class HyperSchemaFactoryWrapper extends SchemaFactoryWrapper {

    private static class HyperSchemaFactoryWrapperFactory extends WrapperFactory {
        @Override
        public SchemaFactoryWrapper getWrapper(SerializerProvider p) {
            SchemaFactoryWrapper wrapper = new HyperSchemaFactoryWrapper();
            wrapper.setProvider(p);
            return wrapper;
        };
    };

    public HyperSchemaFactoryWrapper() {
        super(new HyperSchemaFactoryWrapperFactory());
    }

    @Override
    public JsonObjectFormatVisitor expectObjectFormat(JavaType convertedType) {
        ObjectVisitor visitor = ((ObjectVisitor)super.expectObjectFormat(convertedType));

        // could add other properties here
        addHyperlinks(visitor.getSchema(), convertedType);

        return visitor;
    }

    @Override
    public JsonArrayFormatVisitor expectArrayFormat(JavaType convertedType) {
        ArrayVisitor visitor = ((ArrayVisitor)super.expectArrayFormat(convertedType));

        // could add other properties here
        addHyperlinks(visitor.getSchema(), convertedType);

        return visitor;
    }

    /**
     * Adds writes the type as the title of the schema.
     *
     * @param schema The schema who's title to set.
     * @param type The type of the object represented by the schema.
     */
    private void addHyperlinks(JsonSchema schema, JavaType type) {
        if (!schema.isSimpleTypeSchema()) {
            throw new RuntimeException("given non simple type schema: " + schema.getType());
        }
        Class<?> rawClass = type.getRawClass();
        if (rawClass.isAnnotationPresent(HyperSchema.class)) {
            HyperSchema hyperSchema = rawClass.getAnnotation(HyperSchema.class);
            Link[] links = hyperSchema.links();
            LinkDescriptionObject[] linkDescriptionObjects = new LinkDescriptionObject[links.length];
            for(int i = 0; i < links.length; i++) {
                Link link = links[i];
                linkDescriptionObjects[i] = new LinkDescriptionObject()
                    .setHref(link.href())
                    .setRel(link.rel())
                    .setMethod(link.method())
                    .setEnctype(link.enctype())
                    .setTargetSchema(fetchSchema(link.targetSchema()))
                    .setJsonSchema(fetchSchema(link.jsonSchema()));
            }
            schema.asSimpleTypeSchema().setLinks(linkDescriptionObjects);
        }
    }

    private JsonSchema fetchSchema(Class<?> targetSchema) {
        if (provider instanceof DefaultSerializerProvider && targetSchema != void.class) {
            JavaType targetType = provider.constructType(targetSchema);
            try {
                HyperSchemaFactoryWrapper targetVisitor = new HyperSchemaFactoryWrapper();
                ((DefaultSerializerProvider) provider).acceptJsonFormatVisitor(targetType, targetVisitor);
                return targetVisitor.finalSchema();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
