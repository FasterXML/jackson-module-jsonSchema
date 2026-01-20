package tools.jackson.module.jsonSchema.jakarta.factories;

import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.jsonFormatVisitors.*;

import tools.jackson.module.jsonSchema.jakarta.types.*;

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

    public JsonArrayFormatVisitor arrayFormatVisitor(SerializationContext context,
            ArraySchema arraySchema) {
        return new ArrayVisitor(context, arraySchema, wrapperFactory);
    }

    public JsonMapFormatVisitor mapFormatVisitor(SerializationContext context,
            ObjectSchema objectSchema) {
        return new MapVisitor(context, objectSchema, wrapperFactory);
    }

    public JsonObjectFormatVisitor objectFormatVisitor(SerializationContext context,
            ObjectSchema objectSchema) {
        return new ObjectVisitor(context, objectSchema, wrapperFactory);
    }


    protected JsonArrayFormatVisitor arrayFormatVisitor(SerializationContext context,
            ArraySchema arraySchema, VisitorContext rvc) {
        ArrayVisitor v = new ArrayVisitor(context, arraySchema, wrapperFactory);
        v.setVisitorContext(rvc);
        return v;
    }

    protected JsonMapFormatVisitor mapFormatVisitor(SerializationContext context,
            ObjectSchema objectSchema, VisitorContext rvc) {
        MapVisitor v = new MapVisitor(context, objectSchema, wrapperFactory);
        v.setVisitorContext(rvc);
        return v;
    }

    protected JsonObjectFormatVisitor objectFormatVisitor(SerializationContext context,
            ObjectSchema objectSchema, VisitorContext rvc) {
        ObjectVisitor v = new ObjectVisitor(context, objectSchema, wrapperFactory);
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
