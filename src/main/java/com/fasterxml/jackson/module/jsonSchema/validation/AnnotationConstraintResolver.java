package com.fasterxml.jackson.module.jsonSchema.validation;

import static java.util.stream.Collectors.toMap;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import com.fasterxml.jackson.databind.BeanProperty;

/**
 * @author cponomaryov
 * 
 * @since 2.5
 */
public class AnnotationConstraintResolver
    extends ValidationConstraintResolver
{
    Map<String, List<Annotation>> propertyConstraints;

    public AnnotationConstraintResolver() {

    }

    public AnnotationConstraintResolver(Class<?> type, Class<?>... groups) {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        BeanDescriptor beanDescriptor = validator.getConstraintsForClass(type);
        propertyConstraints = beanDescriptor.getConstrainedProperties().stream().collect(toMap(pd -> pd.getPropertyName(), propertyDescriptor -> processPropertDescriptor(propertyDescriptor, groups)));
    }

    private Integer getMaxSize(BeanProperty prop) {
        Size ann = getSizeAnnotation(prop);
        if (ann != null) {
            int value = ann.max();
            if (value != Integer.MAX_VALUE) {
                return value;
            }
        }
        return null;
    }

    private Integer getMinSize(BeanProperty prop) {
        Size ann = getSizeAnnotation(prop);
        if (ann != null) {
            int value = ann.min();
            if (value != 0) {
                return value;
            }
        }
        return null;
    }

    @Override
    public Integer getArrayMaxItems(BeanProperty prop) {
        return getMaxSize(prop);
    }

    @Override
    public Integer getArrayMinItems(BeanProperty prop) {
        return getMinSize(prop);
    }

    @Override
    public Double getNumberMaximum(BeanProperty prop) {
        Max maxAnnotation = getAnnotation(prop, Max.class);
        if (maxAnnotation != null) {
            return (double) maxAnnotation.value();
        }
        DecimalMax decimalMaxAnnotation = getAnnotation(prop, DecimalMax.class);
        return decimalMaxAnnotation != null ? new BigDecimal(decimalMaxAnnotation.value()).doubleValue() : null;
    }

    @Override
    public Double getNumberMinimum(BeanProperty prop) {
        Min minAnnotation = getAnnotation(prop, Min.class);
        if (minAnnotation != null) {
            return (double) minAnnotation.value();
        }
        DecimalMin decimalMinAnnotation = getAnnotation(prop, DecimalMin.class);
        return decimalMinAnnotation != null ? new BigDecimal(decimalMinAnnotation.value()).doubleValue() : null;
    }

    @Override
    public Integer getStringMaxLength(BeanProperty prop) {
        return getMaxSize(prop);
    }

    @Override
    public Integer getStringMinLength(BeanProperty prop) {
        return getMinSize(prop);
    }

    @Override
    public String getStringPattern(final BeanProperty prop) {
        Pattern patternAnnotation = getAnnotation(prop, Pattern.class);
        if (patternAnnotation != null) {
            return patternAnnotation.regexp();
        }
        return null;
    }

    @Override
    public Boolean getRequired(BeanProperty prop) {
        NotNull notNull = getAnnotation(prop, NotNull.class);
        return notNull != null ? true : null;
    }

    private Size getSizeAnnotation(BeanProperty prop) {
        return getAnnotation(prop, Size.class);
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

    List<Annotation> processPropertDescriptor(PropertyDescriptor propertyDescriptor, Class<?>... groups) {
        Set<ConstraintDescriptor<?>> descriptorsForGroup = propertyDescriptor.findConstraints().unorderedAndMatchingGroups(groups).getConstraintDescriptors();
        List<Annotation> propertyConstraintAnnotations = new ArrayList<>();
        for (ConstraintDescriptor<?> constraintDescriptor : descriptorsForGroup) {
            processNestedDescriptors(constraintDescriptor, propertyConstraintAnnotations);
        }
        return propertyConstraintAnnotations;
    }

    void processNestedDescriptors(ConstraintDescriptor<?> constraintDescriptor, List<Annotation> propertyConstraintAnnotations) {
        Set<ConstraintDescriptor<?>> composingConstraints = constraintDescriptor.getComposingConstraints();
        if (composingConstraints != null && composingConstraints.size() > 0) {
            for (ConstraintDescriptor<?> constraintDescriptor2 : composingConstraints) {
                processNestedDescriptors(constraintDescriptor2, propertyConstraintAnnotations);
            }
        }
        propertyConstraintAnnotations.add(constraintDescriptor.getAnnotation());
    }
}
