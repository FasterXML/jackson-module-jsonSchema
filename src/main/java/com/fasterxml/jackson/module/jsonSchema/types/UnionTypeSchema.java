package com.fasterxml.jackson.module.jsonSchema.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

/**
 * This class represents a {@link JsonSchema} as a Union Type Schema:
 * "An array of two or more simple type definitions.  Each
      item in the array MUST be a simple type definition or a schema.
      The instance value is valid if it is of the same type as one of
      the simple type definitions, or valid by one of the schemas, in

 * @author jphelan
 */
public class UnionTypeSchema extends JsonSchema
{
	@JsonProperty
	protected ValueTypeSchema[] elements;

     @Override
     public boolean isUnionTypeSchema() {
          return true;
     }

     @Override
	public UnionTypeSchema asUnionTypeSchema() {
		return this;
	}

     // Important: This is the Type Id, as defined by base class `JsonSchema`
     @Override
     public JsonFormatTypes getType() {
         // 29-Dec-2015, tatu: As per [module-jsonSchema#90], can not return null.
         //  ... but, alas, there is no real suitable value to return. Just can not
         //  be null; but if not null, will result in wrong deserialization.
//         return JsonFormatTypes.UNION;
         return null;
     }
     
	public ValueTypeSchema[] getElements() {
		return elements;
	}

	public void setElements(ValueTypeSchema[] elements) {
	    if (elements.length < 2) {
	        throw new IllegalArgumentException("Union Type Schemas must contain two or more Simple Type Schemas");
	    }
		this.elements = elements;
	}

     @Override
     public boolean equals(Object obj)
     {
         if (obj == this) return true;
         if (obj == null) return false;
         if (!(obj instanceof UnionTypeSchema)) return false;
         return _equals((UnionTypeSchema) obj);
     }

     protected boolean _equals(UnionTypeSchema that)
     {
         return arraysEqual(getElements(), that.getElements())
                 && super._equals(that);
     }
}