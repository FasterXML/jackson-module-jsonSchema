package com.fasterxml.jackson.module.jsonSchema.customProperties;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.annotation.JsonHyperSchema;
import com.fasterxml.jackson.module.jsonSchema.annotation.Link;
import com.fasterxml.jackson.module.jsonSchema.factories.*;
import com.fasterxml.jackson.module.jsonSchema.types.LinkDescriptionObject;
import com.fasterxml.jackson.module.jsonSchema.types.ReferenceSchema;
import com.fasterxml.jackson.module.jsonSchema.types.SimpleTypeSchema;

/**
 * Adds a hyperlink to object schema, either root level or nested. Generally
 * useful for writing additional properties to a schema.
 *
 * @author mavarazy
 */
public class HyperSchemaFactoryWrapper extends SchemaFactoryWrapper {

    private boolean ignoreDefaults = true;

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

    public void setIgnoreDefaults(boolean ignoreDefaults) {
        this.ignoreDefaults = ignoreDefaults;
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
        if (rawClass.isAnnotationPresent(JsonHyperSchema.class)) {
            JsonHyperSchema hyperSchema = rawClass.getAnnotation(JsonHyperSchema.class);
            String pathStart = hyperSchema.pathStart();
            Link[] links = hyperSchema.links();
            LinkDescriptionObject[] linkDescriptionObjects = new LinkDescriptionObject[links.length];
            for(int i = 0; i < links.length; i++) {
                Link link = links[i];
                linkDescriptionObjects[i] = new LinkDescriptionObject()
                    .setHref(pathStart + link.href())
                    .setRel(link.rel())
                    .setMethod(ignoreDefaults && "GET".equals(link.method()) ? null : link.method())
                    .setEnctype(ignoreDefaults && "application/json".equals(link.enctype()) ? null : link.enctype())
                    .setTargetSchema(fetchSchema(link.targetSchema()))
                    .setSchema(fetchSchema(link.schema()))
                    .setMediaType(ignoreDefaults && "application/json".equals(link.mediaType()) ? null : link.mediaType())
                    .setTitle(link.title());
            }
            SimpleTypeSchema simpleTypeSchema = schema.asSimpleTypeSchema();
            simpleTypeSchema.setLinks(linkDescriptionObjects);
            if(pathStart != null && pathStart.length() != 0)
                simpleTypeSchema.setPathStart(pathStart);
        }
    }

    private JsonSchema fetchSchema(Class<?> targetSchema) {
        if (provider instanceof DefaultSerializerProvider && targetSchema != void.class) {
            JavaType targetType = provider.constructType(targetSchema);
            try {
                if (visitorContext != null) {
                    String seenSchemaUri = visitorContext.getSeenSchemaUri(targetType);
                    if (seenSchemaUri != null) {
                        return new ReferenceSchema(seenSchemaUri);
                    }
                }
                HyperSchemaFactoryWrapper targetVisitor = new HyperSchemaFactoryWrapper();
                targetVisitor.setVisitorContext(visitorContext);

                ((DefaultSerializerProvider) provider).acceptJsonFormatVisitor(targetType, targetVisitor);
                return targetVisitor.finalSchema();
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

}
