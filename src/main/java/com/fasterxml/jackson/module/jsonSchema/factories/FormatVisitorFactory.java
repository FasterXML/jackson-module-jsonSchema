package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;

import com.fasterxml.jackson.module.jsonSchema.types.*;

/**
 * Factory class used for constructing visitors for building various
 * JSON Schema instances via visitor interface.
 */
public class FormatVisitorFactory {

    private final WrapperFactory wrapperFactory;

    public FormatVisitorFactory() {
        this(new WrapperFactory());
    }

    public FormatVisitorFactory(WrapperFactory wrapperFactory) {
        this.wrapperFactory = wrapperFactory;
    }

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
        return new ArrayVisitor(provider, arraySchema, wrapperFactory);
    }

    public JsonMapFormatVisitor mapFormatVisitor(SerializerProvider provider,
            ObjectSchema objectSchema) {
        return new MapVisitor(provider, objectSchema, wrapperFactory);
    }

    public JsonObjectFormatVisitor objectFormatVisitor(SerializerProvider provider,
            ObjectSchema objectSchema) {
        return new ObjectVisitor(provider, objectSchema, wrapperFactory);
    }


    protected JsonArrayFormatVisitor arrayFormatVisitor(SerializerProvider provider,
            ArraySchema arraySchema, VisitorContext rvc) {
        ArrayVisitor v = new ArrayVisitor(provider, arraySchema, wrapperFactory);
        v.setVisitorContext(rvc);
        return v;
    }

    protected JsonMapFormatVisitor mapFormatVisitor(SerializerProvider provider,
            ObjectSchema objectSchema, VisitorContext rvc) {
        MapVisitor v = new MapVisitor(provider, objectSchema, wrapperFactory);
        v.setVisitorContext(rvc);
        return v;
    }

    protected JsonObjectFormatVisitor objectFormatVisitor(SerializerProvider provider,
            ObjectSchema objectSchema, VisitorContext rvc) {
        ObjectVisitor v = new ObjectVisitor(provider, objectSchema, wrapperFactory);
        v.setVisitorContext(rvc);
        return v;
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