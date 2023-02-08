package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;
import com.fasterxml.jackson.module.jsonSchema.types.ReferenceSchema;

public class ObjectVisitor extends JsonObjectFormatVisitor.Base
    implements JsonSchemaProducer, Visitor
{
    protected final ObjectSchema schema;
    protected SerializerProvider provider;
    private WrapperFactory wrapperFactory;
    private VisitorContext visitorContext;

    /**
     * @deprecated Since 2.4; call constructor that takes {@link WrapperFactory}
     */
    @Deprecated
    public ObjectVisitor(SerializerProvider provider, ObjectSchema schema) {
        this(provider, schema, new WrapperFactory());
    }

    public ObjectVisitor(SerializerProvider provider, ObjectSchema schema, WrapperFactory wrapperFactory) {
        this.provider = provider;
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
    public SerializerProvider getProvider() {
        return provider;
    }

    /**
     * @deprecated Construct instances with provider instead
     */
    @Deprecated
    @Override
    public void setProvider(SerializerProvider p) {
        provider = p;
    }

    public WrapperFactory getWrapperFactory() {
        return wrapperFactory;
    }

    /**
     * @deprecated Construct instances with provider instead
     */
    @Deprecated
    public void setWrapperFactory(WrapperFactory wrapperFactory) {
        this.wrapperFactory = wrapperFactory;
    }

    @Override
    public void optionalProperty(BeanProperty prop) throws JsonMappingException {
        schema.putOptionalProperty(prop, propertySchema(prop));
    }

    @Override
    public void optionalProperty(String name, JsonFormatVisitable handler, JavaType propertyTypeHint)
            throws JsonMappingException {
        schema.putOptionalProperty(name, propertySchema(handler, propertyTypeHint));
    }

    @Override
    public void property(BeanProperty prop) throws JsonMappingException {
        schema.putProperty(prop, propertySchema(prop));
    }

    @Override
    public void property(String name, JsonFormatVisitable handler, JavaType propertyTypeHint)
            throws JsonMappingException {
        schema.putProperty(name, propertySchema(handler, propertyTypeHint));
    }

    protected JsonSchema propertySchema(BeanProperty prop)
        throws JsonMappingException
    {
        if (prop == null) {
            throw new IllegalArgumentException("Null property");
        }

        // check if we've seen this argument's sub-schema already and return a reference-schema if we have
        String seenSchemaUri = visitorContext.getSeenSchemaUri(prop.getType());
        if (seenSchemaUri != null) {
            return new ReferenceSchema(seenSchemaUri);
        }

        SchemaFactoryWrapper visitor = wrapperFactory.getWrapper(getProvider(), visitorContext);
        JsonSerializer<Object> ser = getSer(prop);
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
        throws JsonMappingException
    {
        // check if we've seen this argument's sub-schema already and return a reference-schema if we have
        if (visitorContext != null) {
            String seenSchemaUri = visitorContext.getSeenSchemaUri(propertyTypeHint);
            if (seenSchemaUri != null) {
                return new ReferenceSchema(seenSchemaUri);
            }
        }

        SchemaFactoryWrapper visitor = wrapperFactory.getWrapper(getProvider(), visitorContext);
        handler.acceptJsonFormatVisitor(visitor, propertyTypeHint);
        return visitor.finalSchema();
    }

    protected JsonSerializer<Object> getSer(BeanProperty prop)
        throws JsonMappingException
    {
        JsonSerializer<Object> ser = null;
        // 26-Jul-2013, tatu: This is ugly, should NOT require cast...
        if (prop instanceof BeanPropertyWriter) {
            ser = ((BeanPropertyWriter)prop).getSerializer();
        }
        if (ser == null) {
            ser = getProvider().findValueSerializer(prop.getType(), prop);
        }
        return ser;
    }

    @Override
    public Visitor setVisitorContext(VisitorContext rvc) {
        visitorContext = rvc;
        return this;
    }
}
