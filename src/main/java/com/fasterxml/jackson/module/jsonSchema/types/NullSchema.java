package com.fasterxml.jackson.module.jsonSchema.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

/**
 * This class represents a {@link JsonSchema} as a null type
 * @author jphelan
 */
public class NullSchema extends SimpleTypeSchema {
	
	@JsonIgnore
	private final JsonFormatTypes type = JsonFormatTypes.NULL;
	
	@Override
	public NullSchema asNullSchema() { return this; }
	
	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.jsonSchema.types.JsonSchema#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof NullSchema && super.equals(obj));
	}
	
	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.jsonSchema.types.JsonSchema#getType()
	 */
	@Override
	public JsonFormatTypes getType() {
		return type;
	}
	
	@Override
	public boolean isNullSchema() { return true; }
	
}