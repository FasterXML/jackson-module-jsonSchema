package com.fasterxml.jackson.module.jsonSchema.factories;

import java.util.Set;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.module.jsonSchema.types.StringSchema;

public class StringVisitor implements JsonStringFormatVisitor, JsonSchemaProducer
{
    protected final ValueTypeSchemaFactory parent;
    protected final StringSchema schema;
	
    public StringVisitor(ValueTypeSchemaFactory parent, StringSchema schema) {
        this.parent = parent;
        this.schema = schema;
    }

    /*
    /*********************************************************************
    /* JsonSchemaProducer
    /*********************************************************************
     */
	
    @Override
    public void enumTypes(Set<String> enums) {
        parent.enumTypes(enums);
    }

    @Override
    public void format(JsonValueFormat format) {
        parent.format(format);
    }

    @Override
    public StringSchema getSchema() {
        return schema;
    }
}
