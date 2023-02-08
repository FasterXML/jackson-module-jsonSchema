package com.fasterxml.jackson.module.jsonSchema.types;

import java.util.Collections;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

/**
 * This class encapsulates the functionality of container type {@link JsonSchema}
 * Array and Object
 * @author jphelan
 */
public abstract class ContainerTypeSchema extends SimpleTypeSchema
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
	@JsonProperty(value = "enum", required = true)
	protected Set<String> enums = Collections.emptySet();

	/**
	 * This provides an enumeration of all possible values that are valid
	 for the instance property.  This MUST be an array, and each item in
	 the array represents a possible value for the instance value.  If
	 this attribute is defined, the instance value MUST be one of the
	 values in the array in order for the schema to be valid.  Comparison
	 of enum values uses the same algorithm as defined in "uniqueItems"
	 (Section 5.15).
	 */
	@JsonProperty(value = "oneOf", required = true)
	protected Set<Object> oneOf = Collections.emptySet();

	/**
	 * @deprecated Since 2.7
	 */
	@Deprecated
	@Override
	public ContainerTypeSchema asContainerSchema() {
		return asContainerTypeSchema();
	}

	/**
	 * @since 2.7
	 */
	@Override
	public ContainerTypeSchema asContainerTypeSchema() { return this; }

	public Set<String> getEnums() {
	    return enums;
	}

	@Override
	public boolean isContainerTypeSchema() { return true; }

	public void setEnums(Set<String> enums) {
	    this.enums = enums;
	}

	public Set<Object> getOneOf() {
		return oneOf;
	}

	public void setOneOf(Set<Object> oneOf) {
		this.oneOf = oneOf;
	}

	@Override
	public boolean equals(Object obj)
	{
	    if (obj == this) return true;
	    if (obj == null) return false;
	    if (!(obj instanceof ContainerTypeSchema)) return false;
	    return _equals((ContainerTypeSchema) obj);
	}
    
	protected boolean _equals(ContainerTypeSchema that)
	{
	    return equals(getOneOf(), that.getOneOf())
	            && super._equals(that);
     } 
}
