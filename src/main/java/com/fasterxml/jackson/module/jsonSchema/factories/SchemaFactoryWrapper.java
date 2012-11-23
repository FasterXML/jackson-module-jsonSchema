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
    private SchemaFactory delegate;
    protected FormatVisitorFactory visitorFactory;
    protected SerializerProvider provider;
    protected JsonSchemaProvider schemaProvider;

    public SchemaFactoryWrapper() {
        schemaProvider = new JsonSchemaProvider();
        visitorFactory = new FormatVisitorFactory();
    }

    /*
    /*********************************************************************
    /* JsonFormatVisitorWrapper implementation
    /*********************************************************************
     */
	
    public JsonAnyFormatVisitor expectAnyFormat(JavaType convertedType) {
		AnySchema anySchema = schemaProvider.anySchema();
		delegate = visitorFactory.schemaFactory(provider, anySchema);
		return visitorFactory.anyFormatVisitor(delegate, anySchema);
    }
	
    public JsonArrayFormatVisitor expectArrayFormat(JavaType convertedType) {
		ArraySchema arraySchema = schemaProvider.arraySchema();
		delegate = visitorFactory.schemaFactory(provider, arraySchema);
		return visitorFactory.arrayFormatVisitor(delegate, arraySchema);
    }

    public JsonBooleanFormatVisitor expectBooleanFormat(JavaType convertedType) {
		BooleanSchema booleanSchema = schemaProvider.booleanSchema();
		delegate = visitorFactory.schemaFactory(provider, booleanSchema);
		ValueTypeSchemaFactory valueTypeSchemaFactory = visitorFactory.valueTypeSchemaFactory(delegate, booleanSchema);
		return visitorFactory.booleanFormatVisitor(valueTypeSchemaFactory, booleanSchema);
    }

    public JsonIntegerFormatVisitor expectIntegerFormat(JavaType convertedType) {
		IntegerSchema integerSchema = schemaProvider.integerSchema();
		delegate = visitorFactory.schemaFactory(provider, integerSchema);
		ValueTypeSchemaFactory valueTypeSchemaFactory = visitorFactory.valueTypeSchemaFactory(delegate, integerSchema);
		return visitorFactory.integerFormatVisitor(valueTypeSchemaFactory, integerSchema);
    }

    public JsonNullFormatVisitor expectNullFormat(JavaType convertedType) {
		NullSchema nullSchema = schemaProvider.nullSchema();
		delegate = visitorFactory.schemaFactory(provider, nullSchema);
		return visitorFactory.nullFormatVisitor(delegate, nullSchema);
    }

    public JsonNumberFormatVisitor expectNumberFormat(JavaType convertedType) {
		NumberSchema numberSchema = schemaProvider.numberSchema();
		delegate = visitorFactory.schemaFactory(provider, numberSchema);
		ValueTypeSchemaFactory valueTypeSchemaFactory = visitorFactory.valueTypeSchemaFactory(delegate, numberSchema);
		return visitorFactory.numberFormatVisitor(valueTypeSchemaFactory, numberSchema);
    }
	
    public JsonObjectFormatVisitor expectObjectFormat(JavaType convertedType) {
		ObjectSchema objectSchema = schemaProvider.objectSchema();
		delegate = visitorFactory.schemaFactory(provider, objectSchema);
		return visitorFactory.objectFormatVisitor(delegate, objectSchema);
    }

    public JsonStringFormatVisitor expectStringFormat(JavaType convertedType) {
		StringSchema stringSchema = schemaProvider.stringSchema();
		delegate = visitorFactory.schemaFactory(provider, stringSchema);
		ValueTypeSchemaFactory valueTypeSchemaFactory = visitorFactory.valueTypeSchemaFactory(delegate, stringSchema);
		return visitorFactory.stringFormatVisitor(valueTypeSchemaFactory, stringSchema);
    }

    @Override
    public JsonMapFormatVisitor expectMapFormat(JavaType type)
        throws JsonMappingException
    {
        /* 22-Nov-2012, tatu: Looks as if JSON Schema did not have
         *   concept of Map (distring from Record or Object); so best
         *   we can do is to consider it a vague kind-a Object...
         */
        ObjectSchema objectSchema = schemaProvider.objectSchema();
        delegate = visitorFactory.schemaFactory(provider, objectSchema);
        return visitorFactory.mapFormatVisitor(delegate, objectSchema);
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

	public SerializerProvider getProvider() {
		return provider;
	}

	public void setProvider(SerializerProvider provider) {
		this.provider = provider;
	}

    /*
    /*********************************************************************
    /* Helper classes
    /*********************************************************************
    */

    public static class SchemaFactoryWrapperProvider {
          public SchemaFactoryWrapper schemaFactoryWrapper(SerializerProvider provider) { 
              SchemaFactoryWrapper wrapper = new SchemaFactoryWrapper();
              wrapper.setProvider(provider);
              return wrapper;
          }
	}
}
