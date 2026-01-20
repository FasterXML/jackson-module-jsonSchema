package tools.jackson.module.jsonSchema.jakarta.factories;

import tools.jackson.databind.*;
import tools.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import tools.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import tools.jackson.databind.ser.BeanPropertyWriter;
import tools.jackson.module.jsonSchema.jakarta.JsonSchema;
import tools.jackson.module.jsonSchema.jakarta.types.ObjectSchema;
import tools.jackson.module.jsonSchema.jakarta.types.ReferenceSchema;

public class ObjectVisitor extends JsonObjectFormatVisitor.Base
    implements JsonSchemaProducer, Visitor
{
    protected final ObjectSchema schema;
    protected SerializationContext context;
    private WrapperFactory wrapperFactory;
    private VisitorContext visitorContext;

    /**
     * @deprecated Since 2.4; call constructor that takes {@link WrapperFactory}
     */
    @Deprecated
    public ObjectVisitor(SerializationContext context, ObjectSchema schema) {
        this(context, schema, new WrapperFactory());
    }

    public ObjectVisitor(SerializationContext context, ObjectSchema schema, WrapperFactory wrapperFactory) {
        this.context = context;
        this.schema = schema;
        this.wrapperFactory = wrapperFactory;
    }

    /*
    /*********************************************************************
    /* JsonSchemaProducer
    /*********************************************************************
     */

    @Override
    public ObjectSchema getSchema() {
        return schema;
    }
    
    /*
    /*********************************************************************
    /* JsonObjectFormatVisitor impl
    /*********************************************************************
     */

    @Override
    public SerializationContext getContext() {
        return context;
    }

    /**
     * @deprecated Construct instances with provider instead
     */
    @Deprecated
    @Override
    public void setContext(SerializationContext c) {
        context = c;
    }

    public WrapperFactory getWrapperFactory() {
        return wrapperFactory;
    }

    @Override
    public void optionalProperty(BeanProperty prop) {
        schema.putOptionalProperty(prop, propertySchema(prop));
    }

    @Override
    public void optionalProperty(String name, JsonFormatVisitable handler, JavaType propertyTypeHint) {
        schema.putOptionalProperty(name, propertySchema(handler, propertyTypeHint));
    }

    @Override
    public void property(BeanProperty prop) {
        schema.putProperty(prop, propertySchema(prop));
    }

    @Override
    public void property(String name, JsonFormatVisitable handler, JavaType propertyTypeHint) {
        schema.putProperty(name, propertySchema(handler, propertyTypeHint));
    }

    protected JsonSchema propertySchema(BeanProperty prop)
    {
        if (prop == null) {
            throw new IllegalArgumentException("Null property");
        }

        // check if we've seen this argument's sub-schema already and return a reference-schema if we have
        String seenSchemaUri = visitorContext.getSeenSchemaUri(prop.getType());
        if (seenSchemaUri != null) {
            return new ReferenceSchema(seenSchemaUri);
        }

        SchemaFactoryWrapper visitor = wrapperFactory.getWrapper(getContext(), visitorContext);
        ValueSerializer<Object> ser = getSer(prop);
        if (ser != null) {
            JavaType type = prop.getType();
            if (type == null) {
                throw new IllegalStateException("Missing type for property '"+prop.getName()+"'");
            }
            ser.acceptJsonFormatVisitor(visitor, type);
        }
        return visitor.finalSchema();
    }

    protected JsonSchema propertySchema(JsonFormatVisitable handler, JavaType propertyTypeHint)
    {
        // check if we've seen this argument's sub-schema already and return a reference-schema if we have
        if (visitorContext != null) {
            String seenSchemaUri = visitorContext.getSeenSchemaUri(propertyTypeHint);
            if (seenSchemaUri != null) {
                return new ReferenceSchema(seenSchemaUri);
            }
        }

        SchemaFactoryWrapper visitor = wrapperFactory.getWrapper(getContext(), visitorContext);
        handler.acceptJsonFormatVisitor(visitor, propertyTypeHint);
        return visitor.finalSchema();
    }

    protected ValueSerializer<Object> getSer(BeanProperty prop)
    {
        ValueSerializer<Object> ser = null;
        // 26-Jul-2013, tatu: This is ugly, should NOT require cast...
        if (prop instanceof BeanPropertyWriter) {
            ser = ((BeanPropertyWriter)prop).getSerializer();
        }
        if (ser == null) {
            ser = getContext().findValueSerializer(prop.getType());
        }
        return ser;
    }

    @Override
    public Visitor setVisitorContext(VisitorContext rvc) {
        visitorContext = rvc;
        return this;
    }
}
