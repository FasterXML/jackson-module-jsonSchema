package com.fasterxml.jackson.module.jsonSchema.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Meant to add items to the schema that are not currently part of the schema.
 * 
 * @author amerritt
 */
@Target({ METHOD, FIELD, PARAMETER, TYPE })
@Retention(RUNTIME)
@Repeatable(NonStandardProperties.class)
public @interface NonStandardProperty {
    String name();
    String value();
}
