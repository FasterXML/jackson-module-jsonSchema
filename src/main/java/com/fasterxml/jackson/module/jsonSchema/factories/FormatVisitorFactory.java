package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;

import com.fasterxml.jackson.module.jsonSchema.types.*;

/**
 * Factory class used for constructing visitors for building various
 * JSON Schema instances via visitor interface.
 */
public class FormatVisitorFactory
{		
    public FormatVisitorFactory() { }

    public ValueTypeSchemaFactory valueTypeSchemaFactory(
            JsonSchemaReference parent, ValueTypeSchema valueTypeSchema) {
        return new ValueTypeSchemaFactory(parent, valueTypeSchema);
    }

    /*
    /**********************************************************
    /* Factory methods for visitors
    /**********************************************************
     */

    public JsonAnyFormatVisitor anyFormatVisitor(JsonSchemaReference delegate,
            AnySchema anySchema) {
        return null;
    }

    public JsonArrayFormatVisitor arrayFormatVisitor(SerializerProvider provider,
            JsonSchemaReference parent, ArraySchema arraySchema) {
        return new ArrayVisitor(provider, arraySchema);
    }

 	public JsonBooleanFormatVisitor booleanFormatVisitor(ValueTypeSchemaFactory parent, BooleanSchema booleanSchema) {
 		return new BooleanVisitor(parent, booleanSchema);
 	}

 	public JsonIntegerFormatVisitor integerFormatVisitor(ValueTypeSchemaFactory parent,
 			IntegerSchema integerSchema) {
 		return new IntegerVisitor(parent, integerSchema);
 	}

 	public JsonNullFormatVisitor nullFormatVisitor(NullSchema nullSchema) {
 		return new NullVisitor(nullSchema);
 	}

     public JsonMapFormatVisitor mapFormatVisitor(SerializerProvider provider,
             JsonSchemaReference parent, ObjectSchema objectSchema) {
         return new MapVisitor(provider, parent, objectSchema);
     }

 	public JsonNumberFormatVisitor numberFormatVisitor(
 			ValueTypeSchemaFactory parent,
 			NumberSchema numberSchema) {
 		return new NumberVisitor(parent, numberSchema);
 	}

 	public JsonObjectFormatVisitor objectFormatVisitor(SerializerProvider provider,
 			JsonSchemaReference parent, ObjectSchema objectSchema) {
 		return new ObjectVisitor(provider, parent, objectSchema);
 	}

 	public JsonStringFormatVisitor stringFormatVisitor(
 			ValueTypeSchemaFactory parent,
 			StringSchema stringSchema) {
 		return new StringVisitor(parent, stringSchema);
 	}
}