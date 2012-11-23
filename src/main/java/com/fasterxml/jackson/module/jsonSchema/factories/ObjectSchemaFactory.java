package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper.SchemaFactoryWrapperProvider;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;

public class ObjectSchemaFactory implements JsonObjectFormatVisitor, SchemaProducer
{
    protected SchemaFactoryWrapperProvider factoryWrapperProvider;
    protected SchemaFactory parent;
    protected ObjectSchema schema;
	
    public ObjectSchemaFactory(SchemaFactory parent, ObjectSchema schema) {
        this.parent = parent;
        this.schema = schema;
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

    public void setFactoryWrapperProvider(SchemaFactoryWrapperProvider factoryWrapperProvider) {
        this.factoryWrapperProvider = factoryWrapperProvider;
    }
     
    public void setParent(SchemaFactory parent) {
        this.parent = parent;
    }

    public void setProvider(SerializerProvider provider) {
        parent.setProvider(provider);
    }

    public void setSchema(ObjectSchema schema) {
        this.schema = schema;
    }

    /*
    /*********************************************************************
    /* Visitor methods
    /*********************************************************************
     */
    
    @Override
    public void optionalProperty(BeanProperty writer) throws JsonMappingException {
        schema.putOptionalProperty(writer.getName(), propertySchema(writer));
    }

    @Override
    public void optionalProperty(String name) throws JsonMappingException {
        schema.putOptionalProperty(name, JsonSchema.minimalForFormat(JsonFormatTypes.ANY));
    }	
	
    @Override
    public void optionalProperty(String name, JsonFormatVisitable handler, JavaType propertyTypeHint)
            throws JsonMappingException {
        schema.putOptionalProperty(name, propertySchema(handler, propertyTypeHint));
    }

    @Override
    public void property(BeanProperty writer) throws JsonMappingException {
        schema.putProperty(writer.getName(), propertySchema(writer));
    }
	
    @Override
    public void property(String name) throws JsonMappingException {
		schema.putProperty(name, JsonSchema.minimalForFormat(JsonFormatTypes.ANY));
    }

    @Override
    public void property(String name, JsonFormatVisitable handler, JavaType propertyTypeHint)
            throws JsonMappingException {
        schema.putProperty(name, propertySchema(handler, propertyTypeHint));
    }

    protected JsonSchema propertySchema(BeanProperty writer)
        throws JsonMappingException
    {
        if (writer == null) {
            throw new IllegalArgumentException("Null writer");
        }
        SchemaFactoryWrapper visitor = factoryWrapperProvider.schemaFactoryWrapper(parent.getProvider());
        JsonSerializer<Object> ser = getSer(writer);
        if (ser != null) {
            JavaType type = writer.getType();
            if (type == null) {
                throw new IllegalStateException("Missing type for property '"+writer.getName()+"'");
            }
            ser.acceptJsonFormatVisitor(visitor, type);
        }
        return visitor.finalSchema();
    }
	
    protected JsonSchema propertySchema(JsonFormatVisitable handler, JavaType propertyTypeHint)
        throws JsonMappingException
    {
		SchemaFactoryWrapper visitor = factoryWrapperProvider.schemaFactoryWrapper(parent.getProvider());
		handler.acceptJsonFormatVisitor(visitor, propertyTypeHint);
		return visitor.finalSchema();
    }

    private JsonSerializer<Object> getSer(BeanProperty writer)
        throws JsonMappingException
    {
        JsonSerializer<Object> ser = null;
        if (writer instanceof BeanPropertyWriter) {
            ser = ((BeanPropertyWriter)writer).getSerializer();
        }
        if (ser == null) {
            ser = getProvider().findValueSerializer(writer.getType(), writer);
        }
        return ser;
    }
}
