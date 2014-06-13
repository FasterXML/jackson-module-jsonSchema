package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.types.ArraySchema;
import com.fasterxml.jackson.module.jsonSchema.types.ReferenceSchema;

public class ArrayVisitor extends JsonArrayFormatVisitor.Base
    implements JsonSchemaProducer, RecursiveVisitor
{
    protected final ArraySchema schema;

    protected SerializerProvider provider;

    private WrapperFactory wrapperFactory = new WrapperFactory();

    private RecursiveVisitorContext recursiveVisitorContext;

    public ArrayVisitor(SerializerProvider provider, ArraySchema schema) {
        this(provider, schema, new WrapperFactory());
    }

    public ArrayVisitor(SerializerProvider provider, ArraySchema schema, WrapperFactory wrapperFactory) {
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
    public void itemsFormat(JsonFormatVisitable handler, JavaType contentType)
        throws JsonMappingException
    {
        // An array of object matches any values, thus we leave the schema empty.
        if (contentType.getRawClass() != Object.class) {

            // check if we've seen this sub-schema already and return a reference-schema if we have
            if (recursiveVisitorContext != null) {
                String seenSchemaUri = recursiveVisitorContext.getSeenSchemaUri(contentType);
                if (seenSchemaUri != null) {
                    schema.setItemsSchema(new ReferenceSchema(seenSchemaUri));
                    return;
                }
            }

            SchemaFactoryWrapper visitor = wrapperFactory.getWrapper(getProvider(), recursiveVisitorContext);
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
    public void setRecursiveVisitorContext(RecursiveVisitorContext rvc) {
        recursiveVisitorContext = rvc;
    }
}
