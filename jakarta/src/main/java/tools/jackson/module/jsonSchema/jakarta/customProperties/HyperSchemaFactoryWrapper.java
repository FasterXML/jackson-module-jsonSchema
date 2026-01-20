package tools.jackson.module.jsonSchema.jakarta.customProperties;

import tools.jackson.module.jsonSchema.jakarta.JsonSchema;
import tools.jackson.module.jsonSchema.jakarta.annotation.JsonHyperSchema;
import tools.jackson.module.jsonSchema.jakarta.annotation.Link;
import tools.jackson.module.jsonSchema.jakarta.factories.ArrayVisitor;
import tools.jackson.module.jsonSchema.jakarta.factories.ObjectVisitor;
import tools.jackson.module.jsonSchema.jakarta.factories.SchemaFactoryWrapper;
import tools.jackson.module.jsonSchema.jakarta.factories.VisitorContext;
import tools.jackson.module.jsonSchema.jakarta.factories.WrapperFactory;
import tools.jackson.module.jsonSchema.jakarta.types.LinkDescriptionObject;
import tools.jackson.module.jsonSchema.jakarta.types.ReferenceSchema;
import tools.jackson.module.jsonSchema.jakarta.types.SimpleTypeSchema;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import tools.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import tools.jackson.databind.ser.SerializationContextExt;

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
        public SchemaFactoryWrapper getWrapper(SerializationContext c) {
            return new HyperSchemaFactoryWrapper(c);
        };

        @Override
        public SchemaFactoryWrapper getWrapper(SerializationContext c, VisitorContext rvc)
        {
            return new HyperSchemaFactoryWrapper(c)
                .setVisitorContext(rvc);
        }
    };

    public HyperSchemaFactoryWrapper() {
        super(new HyperSchemaFactoryWrapperFactory());
    }

    public HyperSchemaFactoryWrapper(SerializationContext c) {
        super(c, new HyperSchemaFactoryWrapperFactory());
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
        if (context instanceof SerializationContextExt && targetSchema != void.class) {
            JavaType targetType = context.constructType(targetSchema);
            if (visitorContext != null) {
                String seenSchemaUri = visitorContext.getSeenSchemaUri(targetType);
                if (seenSchemaUri != null) {
                    return new ReferenceSchema(seenSchemaUri);
                }
            }
            HyperSchemaFactoryWrapper targetVisitor = new HyperSchemaFactoryWrapper();
            targetVisitor.setVisitorContext(visitorContext);

            ((SerializationContextExt) context).acceptJsonFormatVisitor(targetType, targetVisitor);
            return targetVisitor.finalSchema();
        }
        return null;
    }
}
