package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper.SchemaFactoryWrapperProvider;
import com.fasterxml.jackson.module.jsonSchema.types.ArraySchema;
import com.fasterxml.jackson.module.jsonSchema.types.JsonSchema;

public class ArraySchemaFactory
    implements JsonArrayFormatVisitor, SchemaProducer
{
    protected BeanProperty property; 
    protected SchemaFactoryWrapperProvider factoryWrapperProvider;
    protected SchemaFactory parent;
    protected ArraySchema schema;
	
    public ArraySchemaFactory(SchemaFactory schemaFactory, ArraySchema schema) {
        this(schemaFactory, null, schema);
    }

    public ArraySchemaFactory(SchemaFactory parent, BeanProperty property, ArraySchema schema) {
        this.parent = parent;
        this.schema = schema;
    }

    public BeanProperty getProperty() {
        return property;
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

    public JsonSchema getSchema() {
        return schema;
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

	public void setProperty(BeanProperty p) {
	    property = p;
	}

	public void setFactoryWrapperProvider(
			SchemaFactoryWrapperProvider factoryWrapperProvider) {
		this.factoryWrapperProvider = factoryWrapperProvider;
	}

	public void setParent(SchemaFactory parent) {
		this.parent = parent;
	}

	public void setProvider(SerializerProvider provider) {
		parent.setProvider(provider);
	}

	public void setSchema(ArraySchema schema) {
		this.schema = schema;
	}

}
