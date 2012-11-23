package com.fasterxml.jackson.module.jsonSchema.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

/**
 * This class represents a {@link JsonSchema} of type boolean
 * @author jphelan
 *
 */
public class BooleanSchema extends ValueTypeSchema {
	
	@JsonIgnore
	private final JsonFormatTypes type = JsonFormatTypes.BOOLEAN;
	
	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.jsonSchema.types.JsonSchema#isBooleanSchema()
	 */
	@Override
	public boolean isBooleanSchema() { return true; }
	
	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.jsonSchema.types.JsonSchema#getType()
	 */
	@Override
	public JsonFormatTypes getType() {
		return type;
	}
	
	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.jsonSchema.types.JsonSchema#asBooleanSchema()
	 */
	@Override
	public BooleanSchema asBooleanSchema() { return this; }
}