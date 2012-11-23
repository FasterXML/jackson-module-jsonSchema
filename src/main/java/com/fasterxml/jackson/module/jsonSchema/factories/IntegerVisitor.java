package com.fasterxml.jackson.module.jsonSchema.factories;

import java.util.Set;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.module.jsonSchema.types.IntegerSchema;

public class IntegerVisitor implements JsonIntegerFormatVisitor, JsonSchemaProducer
{
    protected final ValueTypeSchemaFactory parent;
    protected final IntegerSchema schema;
	
    public IntegerVisitor(ValueTypeSchemaFactory parent, IntegerSchema schema) {
        this.parent = parent;
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
		parent.enumTypes(enums);
	}

	public void format(JsonValueFormat format) {
		parent.format(format);
	}
}
