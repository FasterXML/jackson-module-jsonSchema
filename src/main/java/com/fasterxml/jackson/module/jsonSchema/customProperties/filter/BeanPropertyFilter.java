package com.fasterxml.jackson.module.jsonSchema.customProperties.filter;

import com.fasterxml.jackson.databind.BeanProperty;

/**
 * Checks if given {@link com.fasterxml.jackson.databind.BeanProperty}
 * follows filtering rule.
 *
 * @author wololock
 */
public interface BeanPropertyFilter {
	boolean test(BeanProperty property);
}
