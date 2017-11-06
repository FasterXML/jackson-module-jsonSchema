package com.fasterxml.jackson.module.jsonSchema.property.manager;

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
import com.fasterxml.jackson.module.jsonSchema.property.constraint.SchemaPropertyProcessorConstraint;
import com.fasterxml.jackson.module.jsonSchema.property.constraint.SchemaPropertyProcessorConstraintDecimalMax;
import com.fasterxml.jackson.module.jsonSchema.property.constraint.SchemaPropertyProcessorConstraintDecimalMin;
import com.fasterxml.jackson.module.jsonSchema.property.constraint.SchemaPropertyProcessorConstraintMax;
import com.fasterxml.jackson.module.jsonSchema.property.constraint.SchemaPropertyProcessorConstraintMin;
import com.fasterxml.jackson.module.jsonSchema.property.constraint.SchemaPropertyProcessorConstraintPattern;
import com.fasterxml.jackson.module.jsonSchema.property.constraint.SchemaPropertyProcessorConstraintRequired;
import com.fasterxml.jackson.module.jsonSchema.property.constraint.SchemaPropertyProcessorConstraintSize;

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

    @Override
    public SchemaPropertyProcessorManagerApi createCopyForType(Class<?> type) {
        return new SchemaPropertyProcessorManagerConstraint(type, groups);
    }

    /**
     * Get all the constraints for the type for the given groups.  These will then be passed 
     * to the constraint processors so they don't each have to look them up every time.
     * 
     * @param type - Current class to get constraints from.
     * @param groups - Active groups for constraints.
     */
    private void init(Class<?> type, Class<?>... groups) {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        BeanDescriptor beanDescriptor = validator.getConstraintsForClass(type);
        propertyConstraints = beanDescriptor.getConstrainedProperties().stream().collect(toMap(pd -> pd.getPropertyName(), propertyDescriptor -> processPropertDescriptor(propertyDescriptor, groups)));
    }

    @Override
    public void process(JsonSchema schema, BeanProperty prop) {
        getProcessors().forEach(processor -> {
            if (processor instanceof SchemaPropertyProcessorConstraint) {
                ((SchemaPropertyProcessorConstraint)processor).setPropertyConstraints(propertyConstraints);
            }
            processor.process(schema, prop);
        });
    }

    protected List<Annotation> processPropertDescriptor(PropertyDescriptor propertyDescriptor, Class<?>... groups) {
        Set<ConstraintDescriptor<?>> descriptorsForGroup = propertyDescriptor.findConstraints().unorderedAndMatchingGroups(groups).getConstraintDescriptors();
        List<Annotation> propertyConstraintAnnotations = new ArrayList<>();
        for (ConstraintDescriptor<?> constraintDescriptor : descriptorsForGroup) {
            processNestedDescriptors(constraintDescriptor, propertyConstraintAnnotations);
        }
        return propertyConstraintAnnotations;
    }

    protected void processNestedDescriptors(ConstraintDescriptor<?> constraintDescriptor, List<Annotation> propertyConstraintAnnotations) {
        Set<ConstraintDescriptor<?>> composingConstraints = constraintDescriptor.getComposingConstraints();
        if (composingConstraints != null && composingConstraints.size() > 0) {
            for (ConstraintDescriptor<?> constraintDescriptor2 : composingConstraints) {
                processNestedDescriptors(constraintDescriptor2, propertyConstraintAnnotations);
            }
        }
        propertyConstraintAnnotations.add(constraintDescriptor.getAnnotation());
    }
}
