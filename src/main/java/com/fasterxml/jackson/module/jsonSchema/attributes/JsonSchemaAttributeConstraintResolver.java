package com.fasterxml.jackson.module.jsonSchema.attributes;

import com.fasterxml.jackson.databind.BeanProperty;

public class JsonSchemaAttributeConstraintResolver implements AttributeConstraintResolver {

	@Override
	public String getSchemaRef(BeanProperty prop) {
		JsonSchemaAttributes attrs = prop.getAnnotation(JsonSchemaAttributes.class);
		if (attrs == null) {
			return null;
		}
		
		String ret = attrs.$ref();
		if ("".equals(ret)) {
			return null;
		} else {
			return ret;
		}
	}

	@Override
	public String getId(BeanProperty prop) {
		JsonSchemaAttributes attrs = prop.getAnnotation(JsonSchemaAttributes.class);
		if (attrs == null) {
			return null;
		}
		
		String ret = attrs.id();
		if ("".equals(ret)) {
			return null;
		} else {
			return ret;
		}
	}

	@Override
	public String getTitle(BeanProperty prop) {
		JsonSchemaAttributes attrs = prop.getAnnotation(JsonSchemaAttributes.class);
		if (attrs == null) {
			return null;
		}
	
		String ret = attrs.title();
		if ("".equals(ret)) {
			return null;
		} else {
			return ret;
		}
	}

	@Override
	public String getDescription(BeanProperty prop) {
		JsonSchemaAttributes attrs = prop.getAnnotation(JsonSchemaAttributes.class);
		if (attrs == null) {
			return null;
		}

		String ret = attrs.description();
		if ("".equals(ret)) {
			return null;
		} else {
			return ret;
		}
	}

	@Override
	public String getPattern(BeanProperty prop) {
		JsonSchemaAttributes attrs = prop.getAnnotation(JsonSchemaAttributes.class);
		if (attrs == null) {
			return null;
		}
		
		String ret = attrs.pattern();
		if ("".equals(ret)) {
			return null;
		} else {
			return ret;
		}
	}

//	@Override
//	public String getFormat(BeanProperty prop) {
//		JsonSchemaAttributes attrs = prop.getAnnotation(JsonSchemaAttributes.class);
//		if (attrs == null) {
//			return null;
//		}
//	
//		String ret = attrs.format();
//		if ("".equals(ret)) {
//			return null;
//		} else {
//			return ret;
//		}
//	}
	
	@Override
	public String getValidationMessage(BeanProperty prop) {
		JsonSchemaAttributes attrs = prop.getAnnotation(JsonSchemaAttributes.class);
		if (attrs == null) {
			return null;
		}
		
		String ret = attrs.validationMessage();
		if ("".equals(ret)) {
			return null;
		} else {
			return ret;
		}
	}

	@Override
	public Boolean getRequired(BeanProperty prop) {
		JsonSchemaAttributes attrs = prop.getAnnotation(JsonSchemaAttributes.class);
		if (attrs == null) {
			return null;
		}
		
		boolean ret = attrs.required();
		if (false == ret) {
			return null;
		} else {
			return ret;
		}
	}

	@Override
	public Boolean getReadonly(BeanProperty prop) {
		JsonSchemaAttributes attrs = prop.getAnnotation(JsonSchemaAttributes.class);
		if (attrs == null) {
			return null;
		}
		
		boolean ret = attrs.readonly();
		if (false == ret) {
			return null;
		} else {
			return ret;
		}
	}

	@Override
	public Double getMaximum(BeanProperty prop) {
		JsonSchemaAttributes attrs = prop.getAnnotation(JsonSchemaAttributes.class);
		if (attrs == null) {
			return null;
		}
		
		double ret = attrs.maximum();
		if (Double.isNaN(ret)) {
			return null;
		} else {
			return ret;
		}
	}

	@Override
	public Boolean getExclusiveMaximum(BeanProperty prop) {
		JsonSchemaAttributes attrs = prop.getAnnotation(JsonSchemaAttributes.class);
		if (attrs == null) {
			return null;
		} 
		
		boolean ret = attrs.exclusiveMaximum();
		if (false == ret) {
			return null;
		} else {
			return ret;
		}
	}

	@Override
	public Double getMinimum(BeanProperty prop) {
		JsonSchemaAttributes attrs = prop.getAnnotation(JsonSchemaAttributes.class);
		if (attrs == null) {
			return null;
		} 

		double ret = attrs.minimum();
		if (Double.isNaN(ret)) {
			return null;
		} else {
			return ret;
		}
	}

	@Override
	public Boolean getExclusiveMinimum(BeanProperty prop) {
		JsonSchemaAttributes attrs = prop.getAnnotation(JsonSchemaAttributes.class);
		if (attrs == null) {
			return null;
		}

		boolean ret = attrs.exclusiveMinimum();
		if (false == ret) {
			return null;
		} else {
			return ret;
		}
	}

	@Override
	public String[] getEnums(BeanProperty prop) {
		JsonSchemaAttributes attrs = prop.getAnnotation(JsonSchemaAttributes.class);
		if (attrs == null) {
			return null;
		}
		
		String[] ret = attrs.enums();
		if (ret.length == 0) {
			return null;
		} else {
			return ret;
		}
	}

	@Override
	public Integer getMinItems(BeanProperty prop) {
		JsonSchemaAttributes attrs = prop.getAnnotation(JsonSchemaAttributes.class);
		if (attrs == null) {
			return null;
		}
		
		int ret = attrs.minItems();
		if (-1 == ret) {
			return null;
		} else {
			return ret;
		}
	}

	@Override
	public Integer getMaxItems(BeanProperty prop) {
		JsonSchemaAttributes attrs = prop.getAnnotation(JsonSchemaAttributes.class);
		if (attrs == null) {
			return null;
		}
		
		int ret = attrs.maxItems();
		if (-1 == ret) {
			return null;
		} else {
			return ret;
		}
	}

	@Override
	public Boolean getUniqueItems(BeanProperty prop) {
		JsonSchemaAttributes attrs = prop.getAnnotation(JsonSchemaAttributes.class);
		if (attrs == null) {
			return null;
		}
		
		boolean ret = attrs.uniqueItems();
		if (false == ret) {
			return null;
		} else {
			return ret;
		}
	}

	@Override
	public Integer getMinLength(BeanProperty prop) {
		JsonSchemaAttributes attrs = prop.getAnnotation(JsonSchemaAttributes.class);
		if (attrs == null) {
			return null;
		}
		
		int ret = attrs.minLength();
		if (-1 == ret) {
			return null;
		} else {
			return ret;
		}
	}

	@Override
	public Integer getMaxLength(BeanProperty prop) {
		JsonSchemaAttributes attrs = prop.getAnnotation(JsonSchemaAttributes.class);
		if (attrs == null) {
			return null;
		}
		
		int ret = attrs.maxLength();
		if (-1 == ret) {
			return null;
		} else {
			return ret;
		}
	}
}
