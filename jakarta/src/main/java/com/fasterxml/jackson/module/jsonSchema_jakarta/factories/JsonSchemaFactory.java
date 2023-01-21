package com.fasterxml.jackson.module.jsonSchema_jakarta.factories;

import com.fasterxml.jackson.module.jsonSchema_jakarta.types.AnySchema;
import com.fasterxml.jackson.module.jsonSchema_jakarta.types.ArraySchema;
import com.fasterxml.jackson.module.jsonSchema_jakarta.types.BooleanSchema;
import com.fasterxml.jackson.module.jsonSchema_jakarta.types.IntegerSchema;
import com.fasterxml.jackson.module.jsonSchema_jakarta.types.NullSchema;
import com.fasterxml.jackson.module.jsonSchema_jakarta.types.NumberSchema;
import com.fasterxml.jackson.module.jsonSchema_jakarta.types.ObjectSchema;
import com.fasterxml.jackson.module.jsonSchema_jakarta.types.StringSchema;

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
