package com.fasterxml.jackson.module.jsonSchema.customProperties;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.module.jsonSchema.customProperties.filter.BeanPropertyFilter;
import com.fasterxml.jackson.module.jsonSchema.factories.ObjectVisitor;
import com.fasterxml.jackson.module.jsonSchema.factories.ObjectVisitorDecorator;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;

import java.util.Iterator;
import java.util.List;

/**
 * This subtype of {@link com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper} allows
 * you to filter out {@link com.fasterxml.jackson.databind.BeanProperty} from generating schema by applying
 * to each property {@link com.fasterxml.jackson.module.jsonSchema.customProperties.filter.BeanPropertyFilter}.
 *
 * BeanProperty will be excluded if at least one filter excludes it.
 *
 * @author wololock
 */
public class ChainSchemaFactoryWrapper extends SchemaFactoryWrapper {

	private final List<BeanPropertyFilter> filters;

	public ChainSchemaFactoryWrapper(ChainSchemaFactoryWrapperFactory wrapperFactory) {
		super(wrapperFactory);
		this.filters = wrapperFactory.getFilters();
	}

	@Override
	public JsonObjectFormatVisitor expectObjectFormat(JavaType convertedType) {
		return new ObjectVisitorDecorator((ObjectVisitor) super.expectObjectFormat(convertedType)) {
			@Override
			public void optionalProperty(BeanProperty writer) throws JsonMappingException {
				boolean allowed = applyFilters(writer);
				if (allowed) {
					super.optionalProperty(writer);
				}
			}

			@Override
			public void property(BeanProperty writer) throws JsonMappingException {
				boolean allowed = applyFilters(writer);
				if (allowed) {
					super.property(writer);
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
		};
	}

}
