package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper.SchemaFactoryWrapperProvider;
import com.fasterxml.jackson.module.jsonSchema.types.ArraySchema;

public class ArrayVisitor
    implements JsonArrayFormatVisitor, JsonSchemaProducer
{
    protected SerializerProvider provider;
    protected SchemaFactoryWrapperProvider factoryWrapperProvider;

    protected final BeanProperty property; 
    protected final SchemaFactory parent;
    protected final ArraySchema schema;
	
    public ArrayVisitor(SerializerProvider provider,
            SchemaFactory schemaFactory, ArraySchema schema) {
        this(provider, schemaFactory, null, schema);
    }

    public ArrayVisitor(SerializerProvider provider,
            SchemaFactory parent, BeanProperty property, ArraySchema schema) {
        this.provider = provider;
        this.parent = parent;
        this.schema = schema;
        this.property = property;
    }

    public void setFactoryWrapperProvider(SchemaFactoryWrapperProvider factoryWrapperProvider) {
        this.factoryWrapperProvider = factoryWrapperProvider;
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
        return parent.getProvider();
    }

    @Override
    public void itemsFormat(JsonFormatVisitable handler, JavaType contentType)
        throws JsonMappingException
    {
        // An array of object matches any values, thus we leave the schema empty.
        if (contentType.getRawClass() != Object.class) {
            SchemaFactoryWrapper visitor = factoryWrapperProvider.schemaFactoryWrapper(
                    parent.getProvider());
            visitor.setProvider(parent.getProvider());
            handler.acceptJsonFormatVisitor(visitor, contentType);
            schema.setItemsSchema(visitor.finalSchema());
        }
	}

	public void itemsFormat(JsonFormatTypes format) throws JsonMappingException
	{
		schema.setItemsSchema(JsonSchema.minimalForFormat(format));
	}

	@Override
	public void setProvider(SerializerProvider provider) {
		parent.setProvider(provider);
	}
}
