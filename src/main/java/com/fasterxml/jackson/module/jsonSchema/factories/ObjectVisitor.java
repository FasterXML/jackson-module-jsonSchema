package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;

public class ObjectVisitor extends JsonObjectFormatVisitor.Base
    implements JsonSchemaProducer
{
    protected final ObjectSchema schema;
    protected SerializerProvider provider;

    private WrapperFactory wrapperFactory;

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

    @Override
    public void setProvider(SerializerProvider p) {
        provider = p;
    }
    
	public WrapperFactory getWrapperFactory() {
		return wrapperFactory;
	}

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
        SchemaFactoryWrapper visitor = wrapperFactory.getWrapper(getProvider());
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
		SchemaFactoryWrapper visitor = wrapperFactory.getWrapper(getProvider());
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
}
