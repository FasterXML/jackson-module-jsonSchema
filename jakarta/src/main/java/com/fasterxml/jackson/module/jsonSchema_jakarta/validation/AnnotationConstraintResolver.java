package com.fasterxml.jackson.module.jsonSchema_jakarta.validation;

import com.fasterxml.jackson.databind.BeanProperty;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * @author cponomaryov
 * 
 * @since 2.5
 */
public class AnnotationConstraintResolver
    extends ValidationConstraintResolver
{
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

    @Override
    public Boolean getRequired(BeanProperty prop) {
        NotNull notNull = prop.getAnnotation(NotNull.class);
        return notNull != null ? true : null;
    }

    private Size getSizeAnnotation(BeanProperty prop) {
        return prop.getAnnotation(Size.class);
    }
}
