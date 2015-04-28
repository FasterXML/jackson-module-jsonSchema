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
	public JsonFormatTypes getType() {
	    return JsonFormatTypes.NULL;
	}
	
	@Override
	public boolean isNullSchema() { return true; }

	@Override
	public boolean equals(Object obj)
	{
	    if (obj == this) return true;
	    if (obj == null) return false;
	    if (!(obj instanceof NullSchema)) return false;
	    return _equals((NullSchema) obj);
	}
}