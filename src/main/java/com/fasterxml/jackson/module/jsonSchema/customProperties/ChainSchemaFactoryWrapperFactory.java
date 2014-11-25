package com.fasterxml.jackson.module.jsonSchema.customProperties;

import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.module.jsonSchema.customProperties.filter.BeanPropertyFilter;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.factories.VisitorContext;
import com.fasterxml.jackson.module.jsonSchema.factories.WrapperFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Creates {@link com.fasterxml.jackson.module.jsonSchema.customProperties.ChainSchemaFactoryWrapper} with
 * injected list of {@link com.fasterxml.jackson.module.jsonSchema.customProperties.filter.BeanPropertyFilter} filters.
 *
 * This class is thread-safe.
 *
 * @author wololock
 */
public class ChainSchemaFactoryWrapperFactory extends WrapperFactory {

	private final List<BeanPropertyFilter> filters;

	public ChainSchemaFactoryWrapperFactory(List<BeanPropertyFilter> filters) {
		this.filters = Collections.unmodifiableList(filters != null ? filters : new LinkedList<BeanPropertyFilter>());
	}

	public List<BeanPropertyFilter> getFilters() {
		return filters;
	}

	@Override
	public SchemaFactoryWrapper getWrapper(SerializerProvider p) {
		SchemaFactoryWrapper wrapper = new ChainSchemaFactoryWrapper(this);
		wrapper.setProvider(p);
		return wrapper;
	}

	@Override
	public SchemaFactoryWrapper getWrapper(SerializerProvider p, VisitorContext rvc) {
		SchemaFactoryWrapper wrapper = new ChainSchemaFactoryWrapper(this);
		wrapper.setProvider(p);
		wrapper.setVisitorContext(rvc);
		return wrapper;
	}
}
