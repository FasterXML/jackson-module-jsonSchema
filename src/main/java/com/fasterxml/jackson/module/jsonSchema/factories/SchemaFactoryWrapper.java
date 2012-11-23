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
public class SchemaFactoryWrapper implements JsonFormatVisitorWrapper
{
    protected final FormatVisitorFactory visitorFactory;
    protected final JsonSchemaFactory schemaProvider;

    protected SerializerProvider provider;
    private JsonSchemaReference delegate;

    public SchemaFactoryWrapper() {
        this(null);
    }
    
    public SchemaFactoryWrapper(SerializerProvider p) {
        provider = p;
        schemaProvider = new JsonSchemaFactory();
        visitorFactory = new FormatVisitorFactory();
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
        AnySchema anySchema = schemaProvider.anySchema();
        delegate = new JsonSchemaReference(anySchema);
        return visitorFactory.anyFormatVisitor(delegate, anySchema);
    }
	
    @Override
    public JsonArrayFormatVisitor expectArrayFormat(JavaType convertedType) {
        ArraySchema arraySchema = schemaProvider.arraySchema();
        delegate = new JsonSchemaReference(arraySchema);
        return visitorFactory.arrayFormatVisitor(provider, delegate, arraySchema);
    }

    @Override
    public JsonBooleanFormatVisitor expectBooleanFormat(JavaType convertedType) {
        BooleanSchema booleanSchema = schemaProvider.booleanSchema();
        delegate = new JsonSchemaReference(booleanSchema);
        ValueTypeSchemaFactory valueTypeSchemaFactory = new ValueTypeSchemaFactory(delegate, booleanSchema);
        return visitorFactory.booleanFormatVisitor(valueTypeSchemaFactory, booleanSchema);
    }

    @Override
    public JsonIntegerFormatVisitor expectIntegerFormat(JavaType convertedType) {
        IntegerSchema integerSchema = schemaProvider.integerSchema();
        delegate = new JsonSchemaReference(integerSchema);
        ValueTypeSchemaFactory valueTypeSchemaFactory = new ValueTypeSchemaFactory(delegate, integerSchema);
        return visitorFactory.integerFormatVisitor(valueTypeSchemaFactory, integerSchema);
    }

    @Override
    public JsonNullFormatVisitor expectNullFormat(JavaType convertedType) {
        return visitorFactory.nullFormatVisitor(schemaProvider.nullSchema());
    }

    @Override
    public JsonNumberFormatVisitor expectNumberFormat(JavaType convertedType) {
        NumberSchema numberSchema = schemaProvider.numberSchema();
        delegate = new JsonSchemaReference(numberSchema);
        ValueTypeSchemaFactory valueTypeSchemaFactory = new ValueTypeSchemaFactory(delegate, numberSchema);
        return visitorFactory.numberFormatVisitor(valueTypeSchemaFactory, numberSchema);
    }
	
    @Override
    public JsonObjectFormatVisitor expectObjectFormat(JavaType convertedType) {
        ObjectSchema objectSchema = schemaProvider.objectSchema();
        delegate = new JsonSchemaReference(objectSchema);
        return visitorFactory.objectFormatVisitor(provider, delegate, objectSchema);
    }

    @Override
    public JsonStringFormatVisitor expectStringFormat(JavaType convertedType) {
        StringSchema stringSchema = schemaProvider.stringSchema();
        delegate = new JsonSchemaReference(stringSchema);
        ValueTypeSchemaFactory valueTypeSchemaFactory = new ValueTypeSchemaFactory(delegate, stringSchema);
        return visitorFactory.stringFormatVisitor(valueTypeSchemaFactory, stringSchema);
    }

    @Override
    public JsonMapFormatVisitor expectMapFormat(JavaType type)
        throws JsonMappingException
    {
        /* 22-Nov-2012, tatu: Looks as if JSON Schema did not have
         *   concept of Map (distinct from Record or Object); so best
         *   we can do is to consider it a vague kind-a Object...
         */
        ObjectSchema objectSchema = schemaProvider.objectSchema();
        delegate = new JsonSchemaReference(objectSchema);
        return visitorFactory.mapFormatVisitor(provider, delegate, objectSchema);
    }

    /*
    /*********************************************************************
    /* API
    /*********************************************************************
     */
	
    public JsonSchema finalSchema() {
        if (delegate == null) {
            return null;
        }
        return delegate.getSchema();
    }
}
