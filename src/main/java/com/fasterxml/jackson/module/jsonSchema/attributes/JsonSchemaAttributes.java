package com.fasterxml.jackson.module.jsonSchema.attributes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.TYPE })
public @interface JsonSchemaAttributes {

	String $ref() default "";

	String id() default "";

	String title() default "";

	String description() default "";

	String pattern() default "";

//	String format() default "";

	boolean required() default false;

	boolean readonly() default false;

	double maximum() default Double.NaN;

	boolean exclusiveMaximum() default false;

	double minimum() default Double.NaN;

	boolean exclusiveMinimum() default false;
	
//	String[] enums() default {};

	int minItems() default -1;

	int maxItems() default -1;

	boolean uniqueItems() default false;

//	int multipleOf() default 0;

	int minLength() default -1;

	int maxLength() default -1;
}
