package com.fasterxml.jackson.module.jsonSchema.types;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

/**
 * This class encapsulates the functionality of container type {@link JsonSchema}
 * Array and Object
 * @author jphelan
 */
public abstract class ContainerTypeSchema extends SimpleTypeSchema {
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
	private Set<String> enums = Collections.emptySet();

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
	private Set<Object> oneOf = Collections.emptySet();

	/* (non-Javadoc)
         * @see com.fasterxml.jackson.databind.jsonSchema.types.JsonSchema#asContainerSchema()
         */
	@Override
	public ContainerTypeSchema asContainerSchema() { return this; }
	
	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.jsonSchema.types.SimpleTypeSchema#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
	    if (obj == this) return true;
	    if (obj instanceof ContainerTypeSchema) {
			ContainerTypeSchema that = (ContainerTypeSchema)obj;
			return equals(getEnums(), that.getEnums()) &&
				super.equals(obj);
	    }
	    return false;
	} 

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
}