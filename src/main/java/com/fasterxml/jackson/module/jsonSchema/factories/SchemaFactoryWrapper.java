package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.types.*;

/**
 * @author jphelan
 * @author tsaloranta
 */
public class SchemaFactoryWrapper implements JsonFormatVisitorWrapper, Visitor
{
    protected FormatVisitorFactory visitorFactory;
    protected JsonSchemaFactory schemaProvider;
    protected SerializerProvider provider;
    protected JsonSchema schema;
    protected VisitorContext visitorContext;

    public SchemaFactoryWrapper() {
        this(null, new WrapperFactory());
    }

    public SchemaFactoryWrapper(SerializerProvider p) {
        this(p, new WrapperFactory());
    }

    protected SchemaFactoryWrapper(WrapperFactory wrapperFactory) {
        this(null, wrapperFactory);
    }

    protected SchemaFactoryWrapper(SerializerProvider p, WrapperFactory wrapperFactory) {
        provider = p;
        schemaProvider = new JsonSchemaFactory();
        visitorFactory = new FormatVisitorFactory(wrapperFactory);
    }

    /*
    /*********************************************************************
    /* JsonFormatVisitorWrapper implementation
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
    
    @Override
    public JsonAnyFormatVisitor expectAnyFormat(JavaType convertedType) {
        AnySchema s = schemaProvider.anySchema();
        this.schema = s;
        return visitorFactory.anyFormatVisitor(s);
    }
	
    @Override
    public JsonArrayFormatVisitor expectArrayFormat(JavaType convertedType) {
        ArraySchema s = schemaProvider.arraySchema();
        this.schema = s;
        return visitorFactory.arrayFormatVisitor(provider, s, visitorContext);
    }

    @Override
    public JsonBooleanFormatVisitor expectBooleanFormat(JavaType convertedType) {
        BooleanSchema s = schemaProvider.booleanSchema();
        this.schema = s;
        return visitorFactory.booleanFormatVisitor(s);
    }

    @Override
    public JsonIntegerFormatVisitor expectIntegerFormat(JavaType convertedType) {
        IntegerSchema s = schemaProvider.integerSchema();
        this.schema = s;
        return visitorFactory.integerFormatVisitor(s);
    }

    @Override
    public JsonNullFormatVisitor expectNullFormat(JavaType convertedType) {
        NullSchema s = schemaProvider.nullSchema();
        schema = s;
        return visitorFactory.nullFormatVisitor(s);
    }

    @Override
    public JsonNumberFormatVisitor expectNumberFormat(JavaType convertedType) {
        NumberSchema s = schemaProvider.numberSchema();
        schema = s;
        return visitorFactory.numberFormatVisitor(s);
    }
	
    @Override
    public JsonObjectFormatVisitor expectObjectFormat(JavaType convertedType) {
        ObjectSchema s = schemaProvider.objectSchema();
        schema = s;

        // if we don't already have a recursive visitor context, create one
        if (visitorContext == null) {
            visitorContext = new VisitorContext();
        }

        // give each object schema a reference id and keep track of the ones we've seen
        String schemaUri = visitorContext.addSeenSchemaUri(convertedType);
        if (schemaUri != null) {
            s.setId(schemaUri);
        }

        return visitorFactory.objectFormatVisitor(provider, s, visitorContext);
    }

    @Override
    public JsonStringFormatVisitor expectStringFormat(JavaType convertedType) {
        StringSchema s = schemaProvider.stringSchema();
        schema = s;
        return visitorFactory.stringFormatVisitor(s);
    }

    @Override
    public JsonMapFormatVisitor expectMapFormat(JavaType type)
        throws JsonMappingException
    {
        /* 22-Nov-2012, tatu: Looks as if JSON Schema did not have
         *   concept of Map (distinct from Record or Object); so best
         *   we can do is to consider it a vague kind-a Object...
         */
        ObjectSchema s = schemaProvider.objectSchema();
        schema = s;
        return visitorFactory.mapFormatVisitor(provider, s, visitorContext);
    }

    @Override
    public SchemaFactoryWrapper setVisitorContext(VisitorContext rvc) {
        visitorContext = rvc;
        return this;
    }

    /*
    /*********************************************************************
    /* API
    /*********************************************************************
     */
	
    public JsonSchema finalSchema() {
        return schema;
    }
}
