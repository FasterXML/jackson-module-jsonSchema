package com.fasterxml.jackson.module.jsonSchema.validation;

import com.fasterxml.jackson.databind.BeanProperty;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * @author cponomaryov
 */
public class AnnotationConstraintResolver implements ValidationConstraintResolver {

    private Size getSizeAnnotation(BeanProperty prop) {
        return prop.getAnnotation(Size.class);
    }

    private Integer getMaxSize(BeanProperty prop) {
        Size sizeAnnotation = getSizeAnnotation(prop);
        return sizeAnnotation != null && sizeAnnotation.max() != Integer.MAX_VALUE ? sizeAnnotation.max() : null;
    }

    private Integer getMinSize(BeanProperty prop) {
        Size sizeAnnotation = getSizeAnnotation(prop);
        return sizeAnnotation != null && sizeAnnotation.min() != 0 ? sizeAnnotation.min() : null;
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
        Max maxAnnotation = prop.getAnnotation(Max.class);
        if (maxAnnotation != null) {
            return (double) maxAnnotation.value();
        }
        DecimalMax decimalMaxAnnotation = prop.getAnnotation(DecimalMax.class);
        return decimalMaxAnnotation != null ? new BigDecimal(decimalMaxAnnotation.value()).doubleValue() : null;
    }

    @Override
    public Double getNumberMinimum(BeanProperty prop) {
        Min minAnnotation = prop.getAnnotation(Min.class);
        if (minAnnotation != null) {
            return (double) minAnnotation.value();
        }
        DecimalMin decimalMinAnnotation = prop.getAnnotation(DecimalMin.class);
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
        Pattern patternAnnotation = prop.getAnnotation(Pattern.class);
        if (patternAnnotation != null) {
            return patternAnnotation.regexp();
        }
        return null;
    }
}
