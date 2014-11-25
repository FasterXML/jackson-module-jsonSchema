package com.fasterxml.jackson.module.jsonSchema.customProperties;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.customProperties.filter.BeanPropertyFilter;
import com.fasterxml.jackson.module.jsonSchema.customProperties.transformer.JsonSchemaTransformer;
import com.fasterxml.jackson.module.jsonSchema.factories.ObjectVisitor;
import com.fasterxml.jackson.module.jsonSchema.factories.ObjectVisitorDecorator;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;

import java.util.Iterator;
import java.util.List;

/**
 * This subtype of {@link com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper} allows
 * you to filter out {@link com.fasterxml.jackson.databind.BeanProperty} from generating schema by applying
 * to each property {@link com.fasterxml.jackson.module.jsonSchema.customProperties.filter.BeanPropertyFilter}.
 *
 * BeanProperty will be excluded if at least one filter excludes it.
 *
 * This wrapper also uses {@link com.fasterxml.jackson.module.jsonSchema.customProperties.transformer.JsonSchemaTransformer}
 * transformers to apply some additional transformation of {@link com.fasterxml.jackson.module.jsonSchema.JsonSchema}
 *
 * @author wololock
 */
public class FilterChainSchemaFactoryWrapper extends SchemaFactoryWrapper {

	private final List<BeanPropertyFilter> filters;

	private final List<JsonSchemaTransformer> transformers;

	public FilterChainSchemaFactoryWrapper(FilterChainSchemaFactoryWrapperFactory wrapperFactory) {
		super(wrapperFactory);
		this.filters = wrapperFactory.getFilters();
		this.transformers = wrapperFactory.getTransformers();
	}

	@Override
	public JsonObjectFormatVisitor expectObjectFormat(JavaType convertedType) {
		return new ObjectVisitorDecorator((ObjectVisitor) super.expectObjectFormat(convertedType)) {
			@Override
			public void optionalProperty(BeanProperty writer) throws JsonMappingException {
				boolean allowed = applyFilters(writer);
				if (allowed) {
					super.optionalProperty(writer);
					applyTransformations(writer);
				}
			}

			@Override
			public void property(BeanProperty writer) throws JsonMappingException {
				boolean allowed = applyFilters(writer);
				if (allowed) {
					super.property(writer);
					applyTransformations(writer);
				}
			}

			private boolean applyFilters(BeanProperty writer) {
				boolean allowed = true;
				Iterator<BeanPropertyFilter> iterator = filters.iterator();
				while (iterator.hasNext() && allowed) {
					allowed = iterator.next().test(writer);
				}
				return allowed;
			}

			private void applyTransformations(BeanProperty beanProperty) {
				if (!transformers.isEmpty()) {
					JsonSchema jsonSchema = getPropertySchema(beanProperty);
					for (JsonSchemaTransformer transformer : transformers) {
						jsonSchema = transformer.transform(jsonSchema, beanProperty);
					}
				}
			}

			private JsonSchema getPropertySchema(BeanProperty beanProperty) {
				return ((ObjectSchema) getSchema()).getProperties().get(beanProperty.getName());
			}
		};
	}

}
