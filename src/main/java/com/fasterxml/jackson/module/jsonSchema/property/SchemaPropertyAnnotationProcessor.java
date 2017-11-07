package com.fasterxml.jackson.module.jsonSchema.property;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

/**
 * @author amerritt
 * 
 * @since 4.0
 */
public abstract class SchemaPropertyAnnotationProcessor<T extends Annotation> implements SchemaPropertyProcessor
{
    protected Class<T> annotationClass;

    @SuppressWarnings("unchecked")
    public SchemaPropertyAnnotationProcessor() {
        annotationClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void process(JsonSchema schema, BeanProperty prop) {
        T annotation = prop.getAnnotation(annotationClass);
        processAnnotation(schema, annotation);
    }


    @Override
    public void process(JsonSchema schema, JavaType type) {
        T annotation = type.getRawClass().getAnnotation(annotationClass);
        processAnnotation(schema, annotation);
    }

    protected void processAnnotation(JsonSchema schema, T annotation) {
        if (Objects.nonNull(annotation)) {
            processNonNullAnnotation(schema, annotation);
        }
    }

    /**
     * Method called when it finds the annotation.  Will not be called if annotation is not found.
     * 
     * @param schema - current schema
     * @param annotation - will not be null
     */
    protected abstract void processNonNullAnnotation(JsonSchema schema, T annotation);
}
