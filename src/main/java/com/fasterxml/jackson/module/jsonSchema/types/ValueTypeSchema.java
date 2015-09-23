package com.fasterxml.jackson.module.jsonSchema.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This class represents a {@link JsonSchema} 
 * A primitive type. 
 */
public abstract class ValueTypeSchema extends SimpleTypeSchema
{
	/**
	 * This provides an enumeration of all possible values that are valid
	   for the instance property.  This MUST be an array, and each item in
	   the array represents a possible value for the instance value.  If
	   this attribute is defined, the instance value MUST be one of the
	   values in the array in order for the schema to be valid.  Comparison
	   of enum values uses the same algorithm as defined in "uniqueItems"
	   (Section 5.15).
	 */
	@JsonProperty(value = "enum")
	protected Set<String> enums = new LinkedHashSet<String>();
	
	/**
	 * This property defines the type of data, content type, or microformat to
	 * be expected in the instance property values. A format attribute MAY be
	 * one of the values listed below, and if so, SHOULD adhere to the semantics
	 * describing for the format. A format SHOULD only be used to give meaning
	 * to primitive types (string, integer, number, or boolean). Validators MAY
	 * (but are not required to) validate that the instance values conform to a
	 * format.
	 * 
	 * Additional custom formats MAY be created. These custom formats MAY be
	 * expressed as an URI, and this URI MAY reference a schema of that
	 */
	@JsonProperty
	@JsonDeserialize(using = JsonValueFormatDeserializer.class)
	@JsonSerialize(using = JsonValueFormatSerializer.class)
	protected JsonValueFormat format;

	@Override
	public ValueTypeSchema asValueSchemaSchema() { return this; }

	public Set<String> getEnums() {
	    return enums;
	}

	public JsonValueFormat getFormat() {
	    return format;
	}

	@Override
	public boolean isValueTypeSchema() { return true; }

	public void setEnums(Set<String> enums) {
	    this.enums = enums;
	}

	public void setFormat(JsonValueFormat format) {
	    this.format = format;
	}

     @Override
     public boolean equals(Object obj)
     {
         if (obj == this) return true;
         if (obj == null) return false;
         if (!(obj instanceof ValueTypeSchema)) return false;
         return _equals((ValueTypeSchema) obj);
     }

     protected boolean _equals(ValueTypeSchema that)
     {
         return equals(getFormat(), that.getFormat())
                 && equals(getEnums(), that.getEnums())
                 && super._equals(that);
     }
}
