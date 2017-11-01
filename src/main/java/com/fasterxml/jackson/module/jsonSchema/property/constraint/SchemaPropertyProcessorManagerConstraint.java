package com.fasterxml.jackson.module.jsonSchema.property.constraint;

import static java.util.stream.Collectors.toMap;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.property.SchemaPropertyProcessorManager;

/**
 * @author amerritt
 * 
 * @since 4.0
 */
public class SchemaPropertyProcessorManagerConstraint extends SchemaPropertyProcessorManager {
    private Class<?>[] groups = new Class<?>[0];

    private Map<String, List<Annotation>> propertyConstraints;

    public SchemaPropertyProcessorManagerConstraint(Class<?> type, Class<?>... groups) {
        this.groups = groups;
        registerSchemaPropertyProcessor(new SchemaPropertyProcessorConstraintRequired());
        registerSchemaPropertyProcessor(new SchemaPropertyProcessorConstraintSize());
        registerSchemaPropertyProcessor(new SchemaPropertyProcessorConstraintPattern());
        registerSchemaPropertyProcessor(new SchemaPropertyProcessorConstraintMin());
        registerSchemaPropertyProcessor(new SchemaPropertyProcessorConstraintDecimalMin());
        registerSchemaPropertyProcessor(new SchemaPropertyProcessorConstraintMax());
        registerSchemaPropertyProcessor(new SchemaPropertyProcessorConstraintDecimalMax());
        init(type, groups);
    }

    public SchemaPropertyProcessorManagerConstraint createCopyForType(Class<?> type) {
        SchemaPropertyProcessorManagerConstraint copy = new SchemaPropertyProcessorManagerConstraint(type, groups);
        copy.setProcessors(getProcessors());
        return copy;
    }

    private void init(Class<?> type, Class<?>... groups) {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        BeanDescriptor beanDescriptor = validator.getConstraintsForClass(type);
        propertyConstraints = beanDescriptor.getConstrainedProperties().stream().collect(toMap(pd -> pd.getPropertyName(), propertyDescriptor -> processPropertDescriptor(propertyDescriptor, groups)));
    }

    public void process(JsonSchema schema, BeanProperty prop) {
        getProcessors().forEach(processor -> {
            if (processor instanceof SchemaPropertyProcessorConstraint) {
                ((SchemaPropertyProcessorConstraint)processor).setPropertyConstraints(propertyConstraints);
            }
            processor.process(schema, prop);
        });
    }

    public List<Annotation> processPropertDescriptor(PropertyDescriptor propertyDescriptor, Class<?>... groups) {
        Set<ConstraintDescriptor<?>> descriptorsForGroup = propertyDescriptor.findConstraints().unorderedAndMatchingGroups(groups).getConstraintDescriptors();
        List<Annotation> propertyConstraintAnnotations = new ArrayList<>();
        for (ConstraintDescriptor<?> constraintDescriptor : descriptorsForGroup) {
            processNestedDescriptors(constraintDescriptor, propertyConstraintAnnotations);
        }
        return propertyConstraintAnnotations;
    }

    public void processNestedDescriptors(ConstraintDescriptor<?> constraintDescriptor, List<Annotation> propertyConstraintAnnotations) {
        Set<ConstraintDescriptor<?>> composingConstraints = constraintDescriptor.getComposingConstraints();
        if (composingConstraints != null && composingConstraints.size() > 0) {
            for (ConstraintDescriptor<?> constraintDescriptor2 : composingConstraints) {
                processNestedDescriptors(constraintDescriptor2, propertyConstraintAnnotations);
            }
        }
        propertyConstraintAnnotations.add(constraintDescriptor.getAnnotation());
    }
}
