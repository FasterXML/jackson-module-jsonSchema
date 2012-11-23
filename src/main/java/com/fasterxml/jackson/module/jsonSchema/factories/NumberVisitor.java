package com.fasterxml.jackson.module.jsonSchema.factories;

import java.util.Set;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonNumberFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.module.jsonSchema.types.NumberSchema;

public class NumberVisitor implements JsonNumberFormatVisitor, JsonSchemaProducer
{
    protected final ValueTypeSchemaFactory parent;
    protected final NumberSchema schema;
	
    public NumberVisitor(ValueTypeSchemaFactory parent, NumberSchema schema) {
        this.parent = parent;
        this.schema = schema;
    }

    /*
    /*********************************************************************
    /* JsonSchemaProducer
    /*********************************************************************
     */

    public NumberSchema getSchema() {
        return schema;
    }
    
    /*
    /*********************************************************************
    /* JsonSchemaProducer
    /*********************************************************************
     */
	
    public void enumTypes(Set<String> enums) {
        parent.enumTypes(enums);
    }

    public void format(JsonValueFormat format) {
        parent.format(format);
    }
}
