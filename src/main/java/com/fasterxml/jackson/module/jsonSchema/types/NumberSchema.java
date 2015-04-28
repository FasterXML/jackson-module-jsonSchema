package com.fasterxml.jackson.module.jsonSchema.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

/**
 * This class represents a {@link JsonSchema} as a number type
 * @author jphelan
 */
public class NumberSchema extends ValueTypeSchema 
{
	/**
	 * This attribute indicates if the value of the instance (if the
	   instance is a number) can not equal the number defined by the
	   "maximum" attribute.
	 */
	@JsonProperty
	private Boolean exclusiveMaximum;
	
	/**
	 * This attribute indicates if the value of the instance (if the
	   instance is a number) can not equal the number defined by the
	   "minimum" attribute.
	 */
	@JsonProperty
	private Boolean exclusiveMinimum;
	
	/**This attribute defines the maximum value of the instance property*/
	@JsonProperty
	private Double maximum = null;
	
	/**This attribute defines the minimum value of the instance property*/
	@JsonProperty
	private Double minimum = null;

	@Override
	public NumberSchema asNumberSchema() { return this; }

	public Boolean getExclusiveMaximum() {
		return exclusiveMaximum;
	}

	public Boolean getExclusiveMinimum() {
		return exclusiveMinimum;
	}

	public Double getMaximum() {
		return maximum;
	}

	public Double getMinimum() {
		return minimum;
	}
	
	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.jsonSchema.types.JsonSchema#getType()
	 */
	@Override
	public JsonFormatTypes getType()
	{
	    return JsonFormatTypes.NUMBER;
	}
	
	@Override
	public boolean isNumberSchema() { return true; }

	public void setExclusiveMaximum(Boolean exclusiveMaximum) {
	    this.exclusiveMaximum = exclusiveMaximum;
	}

	public void setExclusiveMinimum(Boolean exclusiveMinimum) {
	    this.exclusiveMinimum = exclusiveMinimum;
	}

	public void setMaximum(Double maximum) {
	    this.maximum = maximum;
	}

	public void setMinimum(Double minimum) {
	    this.minimum = minimum;
	}

     @Override
     public boolean equals(Object obj)
     {
         if (obj == this) return true;
         if (obj == null) return false;
         if (!(obj instanceof NumberSchema)) return false;
         return _equals((NumberSchema) obj);
     }

     protected boolean _equals(NumberSchema that)
     {
         return equals(getExclusiveMaximum(), that.getExclusiveMaximum()) &&
                 equals(getExclusiveMinimum(), that.getExclusiveMinimum()) &&
                 equals(getMaximum(), that.getMaximum()) &&
                 equals(getMinimum(), that.getMinimum()) &&
                 super._equals(that);
     } 

}
