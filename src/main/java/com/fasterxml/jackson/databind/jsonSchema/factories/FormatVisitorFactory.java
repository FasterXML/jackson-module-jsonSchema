package com.fasterxml.jackson.databind.jsonSchema.factories;

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
import com.fasterxml.jackson.databind.jsonSchema.factories.SchemaFactoryWrapper.SchemaFactoryWrapperProvider;
import com.fasterxml.jackson.databind.jsonSchema.types.AnySchema;
import com.fasterxml.jackson.databind.jsonSchema.types.ArraySchema;
import com.fasterxml.jackson.databind.jsonSchema.types.BooleanSchema;
import com.fasterxml.jackson.databind.jsonSchema.types.IntegerSchema;
import com.fasterxml.jackson.databind.jsonSchema.types.JsonSchema;
import com.fasterxml.jackson.databind.jsonSchema.types.NullSchema;
import com.fasterxml.jackson.databind.jsonSchema.types.NumberSchema;
import com.fasterxml.jackson.databind.jsonSchema.types.ObjectSchema;
import com.fasterxml.jackson.databind.jsonSchema.types.StringSchema;
import com.fasterxml.jackson.databind.jsonSchema.types.ValueTypeSchema;

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

 	public JsonArrayFormatVisitor arrayFormatVisitor(
 			SchemaFactory parent, ArraySchema arraySchema) {
 		ArraySchemaFactory arraySchemaFactory = new ArraySchemaFactory(parent, arraySchema);
 		arraySchemaFactory.setFactoryWrapperProvider(factoryWrapperProvider);
 		return arraySchemaFactory;
 	}

 	public JsonBooleanFormatVisitor booleanFormatVisitor(ValueTypeSchemaFactory parent, BooleanSchema booleanSchema) {
 		return new BooleanSchemaFactory(parent, booleanSchema);
 	}

 	public JsonIntegerFormatVisitor integerFormatVisitor(
 			ValueTypeSchemaFactory parent,
 			IntegerSchema integerSchema) {
 		return new IntegerSchemaFactory(parent, integerSchema);
 	}

 	public JsonNullFormatVisitor nullFormatVisitor(SchemaFactory parent,
 			NullSchema nullSchema) {
 		return new NullSchemaFactory(parent, nullSchema);
 	}

     public JsonMapFormatVisitor mapFormatVisitor(SchemaFactory parent, ObjectSchema objectSchema) {
        
        MapSchemaFactory mapSchemaFactory = new MapSchemaFactory(parent, objectSchema);
        mapSchemaFactory.setFactoryWrapperProvider(factoryWrapperProvider);
        return mapSchemaFactory;
     }

 	public JsonNumberFormatVisitor numberFormatVisitor(
 			ValueTypeSchemaFactory parent,
 			NumberSchema numberSchema) {
 		return new NumberSchemaFactory(parent, numberSchema);
 	}

 	public JsonObjectFormatVisitor objectFormatVisitor(
 			SchemaFactory parent, ObjectSchema objectSchema) {
 		
 		ObjectSchemaFactory objectSchemaFactory = new ObjectSchemaFactory(parent, objectSchema);
 		objectSchemaFactory.setFactoryWrapperProvider(factoryWrapperProvider);
 		return objectSchemaFactory;
 	}

 	public JsonStringFormatVisitor stringFormatVisitor(
 			ValueTypeSchemaFactory parent,
 			StringSchema stringSchema) {
 		return new StringSchemaFactory(parent, stringSchema);
 	}
}