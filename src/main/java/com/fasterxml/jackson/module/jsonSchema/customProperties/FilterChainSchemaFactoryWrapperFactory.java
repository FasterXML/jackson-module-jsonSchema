package com.fasterxml.jackson.module.jsonSchema.customProperties;

import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.module.jsonSchema.customProperties.filter.BeanPropertyFilter;
import com.fasterxml.jackson.module.jsonSchema.customProperties.transformer.JsonSchemaTransformer;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.factories.VisitorContext;
import com.fasterxml.jackson.module.jsonSchema.factories.WrapperFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Creates {@link FilterChainSchemaFactoryWrapper} with
 * injected list of {@link com.fasterxml.jackson.module.jsonSchema.customProperties.filter.BeanPropertyFilter} filters
 * and additional list of {@link com.fasterxml.jackson.module.jsonSchema.customProperties.transformer.JsonSchemaTransformer}
 * transformers.
 *
 * This class is thread-safe.
 *
 * @author wololock
 */
public class FilterChainSchemaFactoryWrapperFactory extends WrapperFactory {

	/**
	 * Chain of filters
	 *
	 * Only properties that match all filters will be included in final
	 * JSON schema.
	 */
	private final List<BeanPropertyFilter> filters;

	/**
	 * Additional transformations that have to be applied to filtered
	 * bean properties.
	 */
	private final List<JsonSchemaTransformer> transformers;

	public FilterChainSchemaFactoryWrapperFactory(List<BeanPropertyFilter> filters, List<JsonSchemaTransformer> transformers) {
		this.filters = Collections.unmodifiableList(filters != null ? filters : new LinkedList<BeanPropertyFilter>());
		this.transformers = Collections.unmodifiableList(transformers != null ? transformers : new LinkedList<JsonSchemaTransformer>());
	}

	public List<BeanPropertyFilter> getFilters() {
		return filters;
	}

	public List<JsonSchemaTransformer> getTransformers() {
		return transformers;
	}

	@Override
	public SchemaFactoryWrapper getWrapper(SerializerProvider p) {
		SchemaFactoryWrapper wrapper = new FilterChainSchemaFactoryWrapper(this);
		wrapper.setProvider(p);
		return wrapper;
	}

	@Override
	public SchemaFactoryWrapper getWrapper(SerializerProvider p, VisitorContext rvc) {
		SchemaFactoryWrapper wrapper = new FilterChainSchemaFactoryWrapper(this);
		wrapper.setProvider(p);
		wrapper.setVisitorContext(rvc);
		return wrapper;
	}
}
