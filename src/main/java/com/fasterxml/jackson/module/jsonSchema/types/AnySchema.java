package com.fasterxml.jackson.module.jsonSchema.types;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.WrapperFactory.JsonSchemaVersion;

/**
 * This class represents a {@link JsonSchema} of type any
 * @author jphelan
 */
public class AnySchema extends SimpleTypeSchema
{
    protected AnySchema() {
        //jackson deserialization only
        super();
    }

    public AnySchema(JsonSchemaVersion version) {
        super(version);
    }

    @Override
	public AnySchema asAnySchema() { return this; }

	@Override
	public boolean isAnySchema() { return true; }

	@Override
	public JsonFormatTypes getType() {
	    return JsonFormatTypes.ANY;
	}

	@Override
     public boolean equals(Object obj) {
             if (obj == this) return true;
             if (obj == null) return false;
             if (!(obj instanceof AnySchema)) return false;
             return _equals((AnySchema) obj);
     }
}
