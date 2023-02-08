package com.fasterxml.jackson.module.jsonSchema.types;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

/**
 * This class represents a {@link JsonSchema} of type boolean
 * @author jphelan
 *
 */
public class BooleanSchema extends ValueTypeSchema
{
	@Override
	public boolean isBooleanSchema() { return true; }

	@Override
	public JsonFormatTypes getType() {
	    return JsonFormatTypes.BOOLEAN;
	}

	@Override
	public BooleanSchema asBooleanSchema() { return this; }

     @Override
     public boolean equals(Object obj)
     {
         if (obj == this) return true;
         if (obj == null) return false;
         if (!(obj instanceof BooleanSchema)) return false;
         return _equals((BooleanSchema) obj);
     }
    
     protected boolean _equals(BooleanSchema that)
     {
         return super._equals(that);
     }
}
