package com.fasterxml.jackson.module.jsonSchema.factories;

import java.util.Set;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonNumberFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.module.jsonSchema.types.NumberSchema;

public class NumberVisitor extends JsonNumberFormatVisitor.Base
    implements JsonSchemaProducer
{
    protected final NumberSchema schema;
	
    public NumberVisitor(NumberSchema schema) {
        this.schema = schema;
    }

    /*
    /*********************************************************************
    /* JsonSchemaProducer
    /*********************************************************************
     */

    @Override
    public NumberSchema getSchema() {
        return schema;
    }
    
    /*
    /*********************************************************************
    /* JsonNumberFormatVisitor
    /*********************************************************************
     */

    @Override
    public void enumTypes(Set<String> enums) {
        schema.setEnums(enums);
    }

    @Override
    public void format(JsonValueFormat format) {
        schema.setFormat(format);
    }
}
