package com.fasterxml.jackson.module.jsonSchema.property.constraint;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.property.SchemaPropertyAnnotationProcessor;

/**
 * @author amerritt
 * 
 * @since 4.0
 */
public abstract class SchemaPropertyProcessorConstraint<T extends Annotation> extends SchemaPropertyAnnotationProcessor<T> {
    Map<String, List<Annotation>> propertyConstraints;
    String propertyName;

    public void setPropertyConstraints(Map<String, List<Annotation>> propertyConstraints) {
        this.propertyConstraints = propertyConstraints;
    }

    @Override
    public void process(JsonSchema schema, JavaType type) {
        //Currently not processing constraints on the main class
    }

    @Override
    public void process(JsonSchema schema, BeanProperty prop) {
        propertyName = prop.getName();
        processAnnotation(schema, getAnnotation(prop, annotationClass));
    }

    @SuppressWarnings("unchecked")
    T getAnnotation(BeanProperty prop, Class<T> type) {
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
