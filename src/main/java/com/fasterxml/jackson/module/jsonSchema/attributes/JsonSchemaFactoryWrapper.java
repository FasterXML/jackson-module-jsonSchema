package com.fasterxml.jackson.module.jsonSchema.attributes;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.ObjectVisitor;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.factories.VisitorContext;
import com.fasterxml.jackson.module.jsonSchema.factories.WrapperFactory;
import com.fasterxml.jackson.module.jsonSchema.types.ArraySchema;
import com.fasterxml.jackson.module.jsonSchema.types.NumberSchema;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;
import com.fasterxml.jackson.module.jsonSchema.types.StringSchema;

public class JsonSchemaFactoryWrapper extends SchemaFactoryWrapper {

	private AttributeConstraintResolver constraintResolver;

	private static class JsonSchemaFactoryWrapperFactory extends WrapperFactory {

		@Override
		public SchemaFactoryWrapper getWrapper(SerializerProvider p) {
			SchemaFactoryWrapper wrapper = new JsonSchemaFactoryWrapper();
			wrapper.setProvider(p);
			return wrapper;
		}

		@Override
		public SchemaFactoryWrapper getWrapper(SerializerProvider p, VisitorContext rvc) {
			SchemaFactoryWrapper wrapper = new JsonSchemaFactoryWrapper();
			wrapper.setProvider(p);
			wrapper.setVisitorContext(rvc);
			return wrapper;
		}
	}

	public JsonSchemaFactoryWrapper() {
		this(new JsonSchemaAttributeConstraintResolver());
	}

	public JsonSchemaFactoryWrapper(AttributeConstraintResolver constraintResolver) {
		super(new JsonSchemaFactoryWrapperFactory());
		this.constraintResolver = constraintResolver;
	}

	@Override
	public JsonObjectFormatVisitor expectObjectFormat(JavaType convertedType) {

		return new ObjectVisitorDecorator((ObjectVisitor) super.expectObjectFormat(convertedType)) {

			private JsonSchema getPropertySchema(BeanProperty writer) {
				return ((ObjectSchema) getSchema()).getProperties().get(writer.getName());
			}

			@Override
			public void optionalProperty(BeanProperty writer) throws JsonMappingException {
				super.optionalProperty(writer);
				addJsonSchemaAttributes(getPropertySchema(writer), writer);
			}

			@Override
			public void property(BeanProperty writer) throws JsonMappingException {
				super.property(writer);
				addJsonSchemaAttributes(getPropertySchema(writer), writer);
			}
		};
	}

	private JsonSchema addJsonSchemaAttributes(JsonSchema schema, BeanProperty prop) {
		
		if (schema.isNumberSchema()) {
			
			NumberSchema numberSchema = schema.asNumberSchema();
			
			Double maximum = constraintResolver.getMaximum(prop);
			if (maximum != null) { numberSchema.setMaximum(maximum); }
			
			Boolean exclusiveMaximum = constraintResolver.getExclusiveMaximum(prop);
			if (exclusiveMaximum != null) { numberSchema.setExclusiveMaximum(exclusiveMaximum); }
			
			Double minimum = constraintResolver.getMinimum(prop);
			numberSchema.setMinimum(minimum);
			
			Boolean exclusiveMinimum = constraintResolver.getExclusiveMinimum(prop);
			if (exclusiveMinimum != null) { numberSchema.setExclusiveMinimum(exclusiveMinimum); }
			
		} else if (schema.isStringSchema()) {

			StringSchema stringSchema = schema.asStringSchema();
			
			Integer maxLength = constraintResolver.getMaxLength(prop);
			if (maxLength != null) { stringSchema.setMaxLength(maxLength); }
			
			Integer minLength = constraintResolver.getMinLength(prop);
			if (minLength != null) { stringSchema.setMinLength(minLength); }
			
			String pattern = constraintResolver.getPattern(prop);
			if (pattern != null) { stringSchema.setPattern(pattern); }
			
		} else if (schema.isArraySchema()) {
			
			ArraySchema arraySchema = schema.asArraySchema();
			
			Integer maxItems = constraintResolver.getMaxItems(prop);
			if (maxItems != null) { arraySchema.setMaxItems(maxItems); }

			Integer minItems = constraintResolver.getMinItems(prop);
			if (minItems != null) { arraySchema.setMinItems(minItems); }

			Boolean uniqueItems = constraintResolver.getUniqueItems(prop);
			if (uniqueItems != null) { arraySchema.setUniqueItems(uniqueItems); }

		} else if (schema.isObjectSchema()) {
		
			ObjectSchema objectSchema = schema.asObjectSchema();
			
//			Boolean required = constraintResolver.getRequired(prop);
//			if (required != null) { objectSchema.setRequired(required); }			
		}
		
		String schemaRef = constraintResolver.getSchemaRef(prop);
		if (schemaRef != null) { schema.set$ref(schemaRef); }
		
		String id = constraintResolver.getId(prop);
		if (id != null) { schema.setId(id); }
		
		String description = constraintResolver.getDescription(prop);
		if (description != null) { schema.setDescription(description); }

		Boolean required = constraintResolver.getRequired(prop);
		if (required != null) { schema.setRequired(required); }

		Boolean readonly = constraintResolver.getReadonly(prop);
		if (readonly != null) { schema.setReadonly(readonly); }
		
		String title = constraintResolver.getTitle(prop);
		if (title != null) { schema.setTitle(title); }
		
		return schema;
	}
}
