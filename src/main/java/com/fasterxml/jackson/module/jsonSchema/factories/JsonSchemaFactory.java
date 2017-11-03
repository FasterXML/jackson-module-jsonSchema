package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.module.jsonSchema.factories.WrapperFactory.JsonSchemaVersion;
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
    private JsonSchemaVersion version;

    //NOTE: you could use the version to return different version but since Required went from a boolean to a List<String> 
    //      it would require maintaining to complete sets of classes.  Not worth the time and effort at this point.
    public JsonSchemaFactory(JsonSchemaVersion version) {
        this.version = version;
    }

    public AnySchema anySchema(ObjectSchema parent) {
        return setParent(new AnySchema(version), parent);
    }

    public ArraySchema arraySchema(ObjectSchema parent) {
        return setParent(new ArraySchema(version), parent);
    }

    public BooleanSchema booleanSchema(ObjectSchema parent) {
        return setParent(new BooleanSchema(version), parent);
    }

    public IntegerSchema integerSchema(ObjectSchema parent) {
        return setParent(new IntegerSchema(version), parent);
    }

    public NullSchema nullSchema(ObjectSchema parent) {
        return setParent(new NullSchema(version), parent);
    }

    public NumberSchema numberSchema(ObjectSchema parent) {
        return setParent(new NumberSchema(version), parent);
    }

    public ObjectSchema objectSchema(ObjectSchema parent) {
        return setParent(new ObjectSchema(version), parent);
    }

    public StringSchema stringSchema(ObjectSchema parent) {
        return setParent(new StringSchema(version), parent);
    }

    public String getSchemaString() {
        return version.getSchemaString();
    }

    private <T extends SimpleTypeSchema> T setParent(T schema, ObjectSchema parent) {
        schema.setParent(parent);
        return schema;
    }
}