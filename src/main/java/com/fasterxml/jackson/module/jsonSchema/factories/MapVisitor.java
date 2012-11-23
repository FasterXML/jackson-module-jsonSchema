package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonMapFormatVisitor;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper.SchemaFactoryWrapperProvider;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;

/**
 * While JSON Schema does not have notion of "Map" type (unlimited property
 * names), Jackson has, so the distinction is exposed. We will need
 * to handle it here, produce JSON Schema Object type.
 */
public class MapVisitor implements JsonMapFormatVisitor, JsonSchemaProducer
{
    protected final SchemaFactoryWrapperProvider factoryWrapperProvider;
    protected final SchemaFactory parent;
    protected final ObjectSchema schema;

    protected SerializerProvider provider;
    
    public MapVisitor(SerializerProvider provider,
            SchemaFactory parent, ObjectSchema schema, SchemaFactoryWrapperProvider wp)
    {
        this.provider = provider;
        this.parent = parent;
        this.schema = schema;
        factoryWrapperProvider = wp;
    }

    /*
    /*********************************************************************
    /* JsonSchemaProducer
    /*********************************************************************
     */

    public ObjectSchema getSchema() {
        return schema;
    }
    
    /*
    /*********************************************************************
    /* JsonMapFormatVisitor
    /*********************************************************************
     */

    @Override
    public SerializerProvider getProvider() {
        return parent.getProvider();
    }
    
    @Override
    public void setProvider(SerializerProvider provider) {
        parent.setProvider(provider);
    }
    
    @Override
    public void keyFormat(JsonFormatVisitable handler, JavaType keyType)
            throws JsonMappingException {
    }

    @Override
    public void valueFormat(JsonFormatVisitable handler, JavaType valueType)
            throws JsonMappingException {
    }
}
