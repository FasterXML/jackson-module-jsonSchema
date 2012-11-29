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
    protected FormatVisitorFactory visitorFactory;
    protected JsonSchemaFactory schemaProvider;

    protected SerializerProvider provider;
    protected JsonSchema schema;

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
        schema = anySchema;
        return visitorFactory.anyFormatVisitor(anySchema);
    }
	
    @Override
    public JsonArrayFormatVisitor expectArrayFormat(JavaType convertedType) {
        ArraySchema arraySchema = schemaProvider.arraySchema();
        schema = arraySchema;
        return visitorFactory.arrayFormatVisitor(provider, arraySchema);
    }

    @Override
    public JsonBooleanFormatVisitor expectBooleanFormat(JavaType convertedType) {
        BooleanSchema booleanSchema = schemaProvider.booleanSchema();
        schema = booleanSchema;
        return visitorFactory.booleanFormatVisitor(booleanSchema);
    }

    @Override
    public JsonIntegerFormatVisitor expectIntegerFormat(JavaType convertedType) {
        IntegerSchema integerSchema = schemaProvider.integerSchema();
        schema = integerSchema;
        return visitorFactory.integerFormatVisitor(integerSchema);
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
        ObjectSchema objectSchema = schemaProvider.objectSchema();
        schema = objectSchema;
        return visitorFactory.objectFormatVisitor(provider, objectSchema);
    }

    @Override
    public JsonStringFormatVisitor expectStringFormat(JavaType convertedType) {
        StringSchema stringSchema = schemaProvider.stringSchema();
        schema = stringSchema;
        return visitorFactory.stringFormatVisitor(stringSchema);
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
        schema = objectSchema;
        return visitorFactory.mapFormatVisitor(provider, objectSchema);
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
