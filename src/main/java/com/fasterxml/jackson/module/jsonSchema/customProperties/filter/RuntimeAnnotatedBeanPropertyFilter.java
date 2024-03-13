package com.fasterxml.jackson.module.jsonSchema.customProperties.filter;

import com.fasterxml.jackson.databind.BeanProperty;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Rejects {@link com.fasterxml.jackson.databind.BeanProperty} if it's
 * annotated with at least one given annotation.
 *
 * @author wololock
 */
public class RuntimeAnnotatedBeanPropertyFilter implements BeanPropertyFilter {

	private final List<Class<? extends Annotation>> rejectedAnnotations;

	public RuntimeAnnotatedBeanPropertyFilter(List<Class<? extends Annotation>> rejectedAnnotations) {
		this.rejectedAnnotations = Collections.unmodifiableList(rejectedAnnotations);
	}

	public RuntimeAnnotatedBeanPropertyFilter(Class<? extends Annotation> ...classes) {
		this(Arrays.asList(classes));
	}

	@Override
	public boolean test(BeanProperty property) {
		boolean accept = true;
		if (hasAnnotations(property)) {
			Iterator<Class<? extends Annotation>> iterator = rejectedAnnotations.iterator();
			while (accept && iterator.hasNext()) {
				accept = !property.getMember().hasAnnotation(iterator.next());
			}
		}
		return accept;
	}

	private boolean hasAnnotations(BeanProperty property) {
		return property.getMember().annotations().iterator().hasNext();
	}
}
