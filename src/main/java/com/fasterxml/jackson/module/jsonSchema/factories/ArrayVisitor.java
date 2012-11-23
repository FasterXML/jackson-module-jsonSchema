package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.types.ArraySchema;

public class ArrayVisitor
    implements JsonArrayFormatVisitor, JsonSchemaProducer
{
    protected SerializerProvider provider;

    protected final SchemaFactoryWrapperProvider factoryWrapperProvider;
    protected final ArraySchema schema;

    public ArrayVisitor(SerializerProvider provider,
            ArraySchema schema, SchemaFactoryWrapperProvider wp) {
        this.provider = provider;
        this.schema = schema;
        factoryWrapperProvider = wp;
    }
    
    /*
    /*********************************************************************
    /* JsonSchemaProducer
    /*********************************************************************
     */

    public JsonSchema getSchema() {
        return schema;
    }

    /*
    /*********************************************************************
    /* JsonArrayFormatVisitor
    /*********************************************************************
     */

    @Override
    public SerializerProvider getProvider() {
        return provider;
    }

    @Override
    public void itemsFormat(JsonFormatVisitable handler, JavaType contentType)
        throws JsonMappingException
    {
        // An array of object matches any values, thus we leave the schema empty.
        if (contentType.getRawClass() != Object.class) {
            SchemaFactoryWrapper visitor = factoryWrapperProvider.schemaFactoryWrapper(getProvider());
            visitor.setProvider(getProvider());
            handler.acceptJsonFormatVisitor(visitor, contentType);
            schema.setItemsSchema(visitor.finalSchema());
        }
    }

    @Override
    public void itemsFormat(JsonFormatTypes format) throws JsonMappingException
    {
        schema.setItemsSchema(JsonSchema.minimalForFormat(format));
    }

    @Override
    public void setProvider(SerializerProvider p) {
        provider = p;
    }
}
