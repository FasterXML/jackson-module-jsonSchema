package com.fasterxml.jackson.module.jsonSchema.factories;

import java.util.Set;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.module.jsonSchema.types.IntegerSchema;

public class IntegerVisitor extends JsonIntegerFormatVisitor.Base
    implements JsonSchemaProducer
{
    protected final IntegerSchema schema;
	
    public IntegerVisitor(IntegerSchema schema) {
        this.schema = schema;
    }

    /*
    /*********************************************************************
    /* JsonSchemaProducer
    /*********************************************************************
     */

    public IntegerSchema getSchema() {
        return schema;
    }

    /*
    /*********************************************************************
    /* JsonIntegerFormatVisitor
    /*********************************************************************
     */

    public void enumTypes(Set<String> enums) {
        schema.setEnums(enums);
    }

    public void format(JsonValueFormat format) {
        schema.setFormat(format);
    }
}
