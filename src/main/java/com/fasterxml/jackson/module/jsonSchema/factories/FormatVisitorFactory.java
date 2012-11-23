package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonBooleanFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonMapFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonNullFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonNumberFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper.SchemaFactoryWrapperProvider;
import com.fasterxml.jackson.module.jsonSchema.types.AnySchema;
import com.fasterxml.jackson.module.jsonSchema.types.ArraySchema;
import com.fasterxml.jackson.module.jsonSchema.types.BooleanSchema;
import com.fasterxml.jackson.module.jsonSchema.types.IntegerSchema;
import com.fasterxml.jackson.module.jsonSchema.types.NullSchema;
import com.fasterxml.jackson.module.jsonSchema.types.NumberSchema;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;
import com.fasterxml.jackson.module.jsonSchema.types.StringSchema;
import com.fasterxml.jackson.module.jsonSchema.types.ValueTypeSchema;

/**
 * Factory class used for constructing visitors for building various
 * JSON Schema instances via visitor interface.
 */
public class FormatVisitorFactory
{		
    protected SchemaFactoryWrapperProvider factoryWrapperProvider;
 	
    public SchemaFactoryWrapperProvider getFactoryWrapperProvider() {
        return factoryWrapperProvider;
    }
 	
 	public void setFactoryWrapperProvider(SchemaFactoryWrapperProvider factoryWrapperProvider) {
 		this.factoryWrapperProvider = factoryWrapperProvider;
 	}

 	public FormatVisitorFactory() {
 		factoryWrapperProvider = new SchemaFactoryWrapperProvider();
 	}

     public ValueTypeSchemaFactory valueTypeSchemaFactory(
             SchemaFactory parent, ValueTypeSchema valueTypeSchema) {
         return new ValueTypeSchemaFactory(parent, valueTypeSchema);
     }

     public SchemaFactory schemaFactory(SerializerProvider provider, JsonSchema schema) {
         return new SchemaFactory(provider, schema);
     }

     /*
     /**********************************************************
     /* Factory methods for visitors
     /**********************************************************
      */
     
 	public JsonAnyFormatVisitor anyFormatVisitor(SchemaFactory delegate,
 			AnySchema anySchema) {
 		return null;
 	}

 	public JsonArrayFormatVisitor arrayFormatVisitor(SerializerProvider provider,
 			SchemaFactory parent, ArraySchema arraySchema) {
 	    return new ArrayVisitor(provider, arraySchema, factoryWrapperProvider);
 	}

 	public JsonBooleanFormatVisitor booleanFormatVisitor(ValueTypeSchemaFactory parent, BooleanSchema booleanSchema) {
 		return new BooleanVisitor(parent, booleanSchema);
 	}

 	public JsonIntegerFormatVisitor integerFormatVisitor(
 			ValueTypeSchemaFactory parent,
 			IntegerSchema integerSchema) {
 		return new IntegerVisitor(parent, integerSchema);
 	}

 	public JsonNullFormatVisitor nullFormatVisitor(NullSchema nullSchema) {
 		return new NullVisitor(nullSchema);
 	}

     public JsonMapFormatVisitor mapFormatVisitor(SerializerProvider provider,
             SchemaFactory parent, ObjectSchema objectSchema) {
         return new MapVisitor(provider, parent, objectSchema, factoryWrapperProvider);
     }

 	public JsonNumberFormatVisitor numberFormatVisitor(
 			ValueTypeSchemaFactory parent,
 			NumberSchema numberSchema) {
 		return new NumberVisitor(parent, numberSchema);
 	}

 	public JsonObjectFormatVisitor objectFormatVisitor(SerializerProvider provider,
 			SchemaFactory parent, ObjectSchema objectSchema) {
 		return new ObjectVisitor(provider, parent, objectSchema, factoryWrapperProvider);
 	}

 	public JsonStringFormatVisitor stringFormatVisitor(
 			ValueTypeSchemaFactory parent,
 			StringSchema stringSchema) {
 		return new StringVisitor(parent, stringSchema);
 	}
}