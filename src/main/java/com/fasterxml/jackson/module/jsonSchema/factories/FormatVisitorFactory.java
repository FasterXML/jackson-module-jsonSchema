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

    /*
    /**********************************************************
    /* Factory methods for visitors, structured types
    /**********************************************************
     */

    public JsonAnyFormatVisitor anyFormatVisitor(AnySchema anySchema) {
        return null;
    }

    public JsonArrayFormatVisitor arrayFormatVisitor(SerializerProvider provider,
            ArraySchema arraySchema) {
        return new ArrayVisitor(provider, arraySchema);
    }

    public JsonMapFormatVisitor mapFormatVisitor(SerializerProvider provider,
            ObjectSchema objectSchema) {
        return new MapVisitor(provider, objectSchema);
    }

    public JsonObjectFormatVisitor objectFormatVisitor(SerializerProvider provider,
            ObjectSchema objectSchema) {
        return new ObjectVisitor(provider, objectSchema);
    }
    
    /*
    /**********************************************************
    /* Factory methods for visitors, value types
    /**********************************************************
     */

    public JsonBooleanFormatVisitor booleanFormatVisitor(BooleanSchema booleanSchema) {
        return new BooleanVisitor(booleanSchema);
    }

    public JsonIntegerFormatVisitor integerFormatVisitor(IntegerSchema integerSchema) {
        return new IntegerVisitor(integerSchema);
    }

    // no ValueTypeSchemaFactory, since null type has no formatting
    public JsonNullFormatVisitor nullFormatVisitor(NullSchema nullSchema) {
        return new NullVisitor(nullSchema);
    }

    public JsonNumberFormatVisitor numberFormatVisitor(NumberSchema numberSchema) {
        return new NumberVisitor(numberSchema);
    }

    public JsonStringFormatVisitor stringFormatVisitor(StringSchema stringSchema) {
        return new StringVisitor(stringSchema);
    }
}