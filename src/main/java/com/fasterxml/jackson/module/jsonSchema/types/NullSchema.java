package com.fasterxml.jackson.module.jsonSchema.types;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

/**
 * This class represents a {@link JsonSchema} as a null type
 * @author jphelan
 */
public class NullSchema extends SimpleTypeSchema
{
	@Override
	public NullSchema asNullSchema() { return this; }
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof NullSchema && super.equals(obj));
	}
	
	@Override
	public JsonFormatTypes getType() {
	    return JsonFormatTypes.NULL;
	}
	
	@Override
	public boolean isNullSchema() { return true; }
	
}