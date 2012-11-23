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
public class MapSchemaFactory implements JsonMapFormatVisitor, JsonSchemaProducer
{
    protected SchemaFactoryWrapperProvider factoryWrapperProvider;
    protected SchemaFactory parent;
    protected ObjectSchema schema;
     
    public MapSchemaFactory(SchemaFactory parent, ObjectSchema schema) {
        this.parent = parent;
        this.schema = schema;
    }

    public void setFactoryWrapperProvider(SchemaFactoryWrapperProvider factoryWrapperProvider) {
        this.factoryWrapperProvider = factoryWrapperProvider;
    }

    public SchemaFactoryWrapperProvider getFactoryWrapperProvider() {
        return factoryWrapperProvider;
    }

    public SchemaFactory getParent() {
        return parent;
    }

    public SerializerProvider getProvider() {
        return parent.getProvider();
    } 
    
    public ObjectSchema getSchema() {
        return schema;
    }

    @Override
    public void setProvider(SerializerProvider provider) {
        parent.setProvider(provider);
    }

    /*
    /*********************************************************************
    /* Visitor methods
    /*********************************************************************
     */
    

    @Override
    public void keyFormat(JsonFormatVisitable handler, JavaType keyType)
            throws JsonMappingException {
        // no info here
    }

    @Override
    public void valueFormat(JsonFormatVisitable handler, JavaType valueType)
            throws JsonMappingException {
        // TODO Auto-generated method stub
        
    }
}
