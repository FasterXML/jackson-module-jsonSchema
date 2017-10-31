package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.module.jsonSchema.types.AnySchema;
import com.fasterxml.jackson.module.jsonSchema.types.ArraySchema;
import com.fasterxml.jackson.module.jsonSchema.types.BooleanSchema;
import com.fasterxml.jackson.module.jsonSchema.types.IntegerSchema;
import com.fasterxml.jackson.module.jsonSchema.types.NullSchema;
import com.fasterxml.jackson.module.jsonSchema.types.NumberSchema;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;
import com.fasterxml.jackson.module.jsonSchema.types.SimpleTypeSchema;
import com.fasterxml.jackson.module.jsonSchema.types.StringSchema;

public class JsonSchemaFactory
{
    public AnySchema anySchema(ObjectSchema parent) {
        return setParent(new AnySchema(), parent);
    }

    public ArraySchema arraySchema(ObjectSchema parent) {
        return setParent(new ArraySchema(), parent);
    }

    public BooleanSchema booleanSchema(ObjectSchema parent) {
        return setParent(new BooleanSchema(), parent);
    }

    public IntegerSchema integerSchema(ObjectSchema parent) {
        return setParent(new IntegerSchema(), parent);
    }

    public NullSchema nullSchema(ObjectSchema parent) {
        return setParent(new NullSchema(), parent);
    }

    public NumberSchema numberSchema(ObjectSchema parent) {
        return setParent(new NumberSchema(), parent);
    }

    public ObjectSchema objectSchema(ObjectSchema parent) {
        return setParent(new ObjectSchema(), parent);
    }

    public StringSchema stringSchema(ObjectSchema parent) {
        return setParent(new StringSchema(), parent);
    }

    private <T extends SimpleTypeSchema> T setParent(T schema, ObjectSchema parent) {
        schema.setParent(parent);
        return schema;
    }
}