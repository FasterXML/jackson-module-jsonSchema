package com.fasterxml.jackson.module.jsonSchema.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

/**
 * This class represents a {@link JsonSchema} as an integer type
 * @author jphelan
 *
 */
public class IntegerSchema extends NumberSchema
{
	/**
	 * This attribute defines what value the number instance must be
	   divisible by with no remainder (the result of the division must be an
	   integer.)  The value of this attribute SHOULD NOT be 0.
	 */
	private Integer divisibleBy;
	
     @Override
     public boolean isIntegerSchema() { return true; }

     @Override
     public JsonFormatTypes getType() {
         return JsonFormatTypes.INTEGER;
     }

     @Override
     public IntegerSchema asIntegerSchema() { return this; }

     @JsonProperty
     public Integer getDivisibleBy() {
         return divisibleBy;
     }
    
     public void setDivisibleBy(Integer divisibleBy) {
         this.divisibleBy = divisibleBy;
     }

     @Override
     public boolean equals(Object obj)
     {
         if (obj == this) return true;
         if (obj == null) return false;
         if (!(obj instanceof IntegerSchema)) return false;
         return _equals((IntegerSchema) obj);
     }
    
     protected boolean _equals(IntegerSchema that)
     {
         return equals(getDivisibleBy(), that.getDivisibleBy())
                 && super.equals(that);
	} 
}
