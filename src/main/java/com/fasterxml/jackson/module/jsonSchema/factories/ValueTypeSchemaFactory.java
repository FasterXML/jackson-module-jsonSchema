package com.fasterxml.jackson.module.jsonSchema.factories;

import java.util.Set;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormatVisitor;
import com.fasterxml.jackson.module.jsonSchema.types.ValueTypeSchema;

public class ValueTypeSchemaFactory implements JsonValueFormatVisitor, JsonSchemaProducer
{
    protected final SchemaFactory parent; 
    protected final ValueTypeSchema schema;
	
    protected ValueTypeSchemaFactory(SchemaFactory parent, ValueTypeSchema schema) {
        this.parent = parent;
        this.schema = schema;
    }

    @Override
    public void enumTypes(Set<String> enums) {
        getSchema().setEnums(enums);
    }

    @Override
    public void format(JsonValueFormat format) {
        getSchema().setFormat(format);
    }

    @Override
    public ValueTypeSchema getSchema() {
        return schema;
    }
}
