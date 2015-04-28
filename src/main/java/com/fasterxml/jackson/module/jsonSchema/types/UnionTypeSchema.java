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
      the array."

 * @author jphelan
 *
 */
public class UnionTypeSchema extends JsonSchema {

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

     @Override
     public JsonFormatTypes getType() {
         // Hmmh. What should be returned here?
         return null;
     }
     
	public ValueTypeSchema[] getElements() {
		return elements;
	}

	public void setElements(ValueTypeSchema[] elements) {
		assert elements.length >= 2 : "Union Type Schemas must contain two or more Simple Type Schemas";
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