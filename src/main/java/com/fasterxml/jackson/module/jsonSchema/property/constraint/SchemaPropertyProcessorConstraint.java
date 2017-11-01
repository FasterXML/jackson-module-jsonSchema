package com.fasterxml.jackson.module.jsonSchema.property.constraint;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.module.jsonSchema.property.SchemaPropertyProcessor;

/**
 * @author amerritt
 * 
 * @since 4.0
 */
public abstract class SchemaPropertyProcessorConstraint implements SchemaPropertyProcessor {
    Map<String, List<Annotation>> propertyConstraints;

    public void setPropertyConstraints(Map<String, List<Annotation>> propertyConstraints) {
        this.propertyConstraints = propertyConstraints;
    }

    @SuppressWarnings("unchecked")
    <T extends Annotation> T getAnnotation(BeanProperty prop, Class<T> type) {
        if (propertyConstraints != null) {
            return (T)emptyIfNull(propertyConstraints.get(prop.getName())).stream().filter(a -> type.isInstance(a)).findFirst().orElse(null);
        } else {
            return prop.getAnnotation(type);
        }
    }

    public static <T> List<T> emptyIfNull(final List<T> list) {
        return list == null ? new ArrayList<T>() : list;
    }
}
