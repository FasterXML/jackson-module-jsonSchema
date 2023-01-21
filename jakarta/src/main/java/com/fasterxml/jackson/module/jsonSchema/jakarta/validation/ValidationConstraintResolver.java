package com.fasterxml.jackson.module.jsonSchema.jakarta.validation;

import com.fasterxml.jackson.databind.BeanProperty;

/**
 * Note: implementations should 
 *
 * @author cponomaryov
 * 
 * @since 2.5 NOTE: changed from interface (2.5 - 2.7) to abstract class in 2.8
 */
public abstract class ValidationConstraintResolver
{
    public abstract Integer getArrayMaxItems(BeanProperty prop);

    public abstract Integer getArrayMinItems(BeanProperty prop);

    public abstract Double getNumberMaximum(BeanProperty prop);

    public abstract Double getNumberMinimum(BeanProperty prop);

    public abstract Integer getStringMaxLength(BeanProperty prop);

    public abstract Integer getStringMinLength(BeanProperty prop);

    public abstract String getStringPattern(BeanProperty prop);

    /**
     * @since 2.7
     */
    public abstract Boolean getRequired(BeanProperty prop);
}
