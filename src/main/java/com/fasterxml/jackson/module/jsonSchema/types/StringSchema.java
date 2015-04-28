package com.fasterxml.jackson.module.jsonSchema.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

/**
 * This represents a {@link JsonSchema} as a String
 * @author jphelan
 *
 */
public class StringSchema extends ValueTypeSchema {

	/** this defines the maximum length of the string. */
	@JsonProperty
	private Integer maxLength;

	/** this defines the minimum length of the string. */
	@JsonProperty
	private Integer minLength;
	/**
	 * this provides a regular expression that a string instance MUST match in
	 * order to be valid. Regular expressions SHOULD follow the regular
	 * expression specification from ECMA 262/Perl 5
	 */
	@JsonProperty
	private String pattern;

	@Override
	public StringSchema asStringSchema() {
		return this;
	}

	public Integer getMaxLength() {
	    return maxLength;
	}

	public Integer getMinLength() {
	    return minLength;
	}

	public String getPattern() {
	    return pattern;
	}

	@Override
	public JsonFormatTypes getType() {
	    return JsonFormatTypes.STRING;
	}

	@Override
	public boolean isStringSchema() {
	    return true;
	}

	public void setMaxLength(Integer maxLength) {
	    this.maxLength = maxLength;
	}

	public void setMinLength(Integer minLength) {
	    this.minLength = minLength;
	}

	public void setPattern(String pattern) {
	    this.pattern = pattern;
	}

     @Override
     public boolean equals(Object obj)
     {
         if (obj == this) return true;
         if (obj == null) return false;
         if (!(obj instanceof StringSchema)) return false;
         return _equals((StringSchema) obj);
     }

     protected boolean _equals(StringSchema that)
     {
         return equals(getMaxLength(), that.getMaxLength())
                 && equals(getMinLength(), that.getMinLength())
                 && equals(getPattern(), that.getPattern())
                 && super.equals(that);
     }
}
