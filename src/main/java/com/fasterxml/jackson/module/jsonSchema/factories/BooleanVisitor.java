package com.fasterxml.jackson.module.jsonSchema.factories;

import java.util.Set;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonBooleanFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.module.jsonSchema.types.BooleanSchema;

public class BooleanVisitor implements JsonBooleanFormatVisitor, JsonSchemaProducer
{
    protected final ValueTypeSchemaFactory parent;
    protected final BooleanSchema schema;
	
    public BooleanVisitor(ValueTypeSchemaFactory parent, BooleanSchema schema) {
        this.parent = parent;
        this.schema = schema;
    }

    /*
    /*********************************************************************
    /* JsonSchemaProducer
    /*********************************************************************
     */
    
    @Override
    public BooleanSchema getSchema() {
        return schema;
    }
    
    /*
    /*********************************************************************
    /* JsonBooleanFormatVisitor impl
    /*********************************************************************
     */
	
    public void enumTypes(Set<String> enums) {
        parent.enumTypes(enums);
    }

    public void format(JsonValueFormat format) {
        parent.format(format);
    }
}
