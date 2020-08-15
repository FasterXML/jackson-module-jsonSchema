	package com.fasterxml.jackson.module.jsonSchema.attributes;

import com.fasterxml.jackson.databind.BeanProperty;

public interface AttributeConstraintResolver {

	String getSchemaRef(BeanProperty prop);

	String getId(BeanProperty prop);

	String getTitle(BeanProperty prop);

	String getDescription(BeanProperty prop);

	String getPattern(BeanProperty prop);

//	String getFormat(BeanProperty prop);

	String getValidationMessage(BeanProperty prop);
	
	Boolean getRequired(BeanProperty prop);

	Boolean getReadonly(BeanProperty prop);

	Double getMaximum(BeanProperty prop);

	Boolean getExclusiveMaximum(BeanProperty prop);

	Double getMinimum(BeanProperty prop);

	Boolean getExclusiveMinimum(BeanProperty prop);
	
	String[] getEnums(BeanProperty prop);

	Integer getMinItems(BeanProperty prop);

	Integer getMaxItems(BeanProperty prop);

	Boolean getUniqueItems(BeanProperty prop);

	Integer getMinLength(BeanProperty prop);

	Integer getMaxLength(BeanProperty prop);	
}
