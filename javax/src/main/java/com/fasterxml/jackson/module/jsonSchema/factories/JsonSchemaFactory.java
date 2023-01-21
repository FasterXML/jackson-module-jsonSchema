package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.module.jsonSchema.types.AnySchema;
import com.fasterxml.jackson.module.jsonSchema.types.ArraySchema;
import com.fasterxml.jackson.module.jsonSchema.types.BooleanSchema;
import com.fasterxml.jackson.module.jsonSchema.types.IntegerSchema;
import com.fasterxml.jackson.module.jsonSchema.types.NullSchema;
import com.fasterxml.jackson.module.jsonSchema.types.NumberSchema;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;
import com.fasterxml.jackson.module.jsonSchema.types.StringSchema;

public class JsonSchemaFactory
{
    public AnySchema anySchema() {
        return new AnySchema();
    }

    public ArraySchema arraySchema() {
        return new ArraySchema();
    }

    public BooleanSchema booleanSchema() {
        return new BooleanSchema();
    }

    public IntegerSchema integerSchema() {
        return new IntegerSchema();
    }

    public NullSchema nullSchema() {
        return new NullSchema();
    }

    public NumberSchema numberSchema() {
        return new NumberSchema();
    }

    public ObjectSchema objectSchema() {
        return new ObjectSchema();
    }

    public StringSchema stringSchema() {
        return new StringSchema();
    }
}