package com.fasterxml.jackson.module.jsonSchema.validation;

import com.fasterxml.jackson.databind.BeanProperty;

/**
 * Note: implementations should 
 *
 * @author cponomaryov
 * 
 * @since 2.5
 */
public interface ValidationConstraintResolver
{
    Integer getArrayMaxItems(BeanProperty prop);

    Integer getArrayMinItems(BeanProperty prop);

    Double getNumberMaximum(BeanProperty prop);

    Double getNumberMinimum(BeanProperty prop);

    Integer getStringMaxLength(BeanProperty prop);

    Integer getStringMinLength(BeanProperty prop);

    String getStringPattern(BeanProperty prop);

    /**
     * @since 2.7
     */
    Boolean getRequired(BeanProperty prop);

    /**
     * Helper class that implements all methods, allowing convenient sub-classing,
     * as well as insulates implementations from problems if methods are added
     * in the interface.
     */
    public static class Base implements ValidationConstraintResolver
    {
        @Override
        public Integer getArrayMaxItems(BeanProperty prop) { return null; }

        @Override
        public Integer getArrayMinItems(BeanProperty prop) { return null; }

        @Override
        public Double getNumberMaximum(BeanProperty prop) { return null; }

        @Override
        public Double getNumberMinimum(BeanProperty prop) { return null; }

        @Override
        public Integer getStringMaxLength(BeanProperty prop) { return null; }

        @Override
        public Integer getStringMinLength(BeanProperty prop) { return null; }

        @Override
        public String getStringPattern(BeanProperty prop) { return null; }

        @Override
        public  Boolean getRequired(BeanProperty prop) { return null; }
    }
}
