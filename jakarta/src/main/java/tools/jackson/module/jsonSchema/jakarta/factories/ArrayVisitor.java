package tools.jackson.module.jsonSchema.jakarta.factories;

import tools.jackson.databind.*;
import tools.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import tools.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import tools.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import tools.jackson.module.jsonSchema.jakarta.JsonSchema;
import tools.jackson.module.jsonSchema.jakarta.types.ArraySchema;
import tools.jackson.module.jsonSchema.jakarta.types.ReferenceSchema;

public class ArrayVisitor extends JsonArrayFormatVisitor.Base
    implements JsonSchemaProducer, Visitor
{
    protected final ArraySchema schema;

    protected SerializationContext content;

    private final WrapperFactory wrapperFactory;

    private VisitorContext visitorContext;

    public ArrayVisitor(SerializationContext content, ArraySchema schema) {
        this(content, schema, new WrapperFactory());
    }

    public ArrayVisitor(SerializationContext content, ArraySchema schema, WrapperFactory wrapperFactory) {
        this.content = content;
        this.schema = schema;
        this.wrapperFactory = wrapperFactory;
    }

    /*
    /*********************************************************************
    /* JsonSchemaProducer
    /*********************************************************************
     */

    @Override
    public JsonSchema getSchema() {
        return schema;
    }

    /*
    /*********************************************************************
    /* JsonArrayFormatVisitor
    /*********************************************************************
     */

    @Override
    public SerializationContext getContext() {
        return content;
    }

    @Override
    public void setContext(SerializationContext c) {
        content = c;
    }
    
    public WrapperFactory getWrapperFactory() {
        return wrapperFactory;
    }

    @Override
    public void itemsFormat(JsonFormatVisitable handler, JavaType contentType)
    {
        // An array of object matches any values, thus we leave the schema empty.
        if (contentType.getRawClass() != Object.class) {

            // check if we've seen this sub-schema already and return a reference-schema if we have
            if (visitorContext != null) {
                String seenSchemaUri = visitorContext.getSeenSchemaUri(contentType);
                if (seenSchemaUri != null) {
                    schema.setItemsSchema(new ReferenceSchema(seenSchemaUri));
                    return;
                }
            }

            SchemaFactoryWrapper visitor = wrapperFactory.getWrapper(getContext(), visitorContext);
            handler.acceptJsonFormatVisitor(visitor, contentType);
            schema.setItemsSchema(visitor.finalSchema());
        }
    }

    @Override
    public void itemsFormat(JsonFormatTypes format)
    {
        schema.setItemsSchema(JsonSchema.minimalForFormat(format));
    }

    @Override
    public Visitor setVisitorContext(VisitorContext rvc) {
        visitorContext = rvc;
        return this;
    }
}
