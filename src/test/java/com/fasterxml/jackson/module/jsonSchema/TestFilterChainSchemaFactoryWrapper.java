package com.fasterxml.jackson.module.jsonSchema;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.customProperties.FilterChainSchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.customProperties.FilterChainSchemaFactoryWrapperFactory;
import com.fasterxml.jackson.module.jsonSchema.customProperties.filter.BeanPropertyFilter;
import com.fasterxml.jackson.module.jsonSchema.customProperties.filter.RuntimeAnnotatedBeanPropertyFilter;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestFilterChainSchemaFactoryWrapper {

	@Retention(RetentionPolicy.RUNTIME)
	private static @interface FilterThatOne {
	}

	private static class InternalValue {
		private BigDecimal value;

		private boolean required;

		public BigDecimal getValue() {
			return value;
		}

		public void setValue(BigDecimal value) {
			this.value = value;
		}

		@FilterThatOne
		public boolean isRequired() {
			return required;
		}

		public void setRequired(boolean required) {
			this.required = required;
		}
	}

	private static class TestBean {
		private String name;

		@FilterThatOne
		private String oldName;

		private InternalValue internalValue;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getOldName() {
			return oldName;
		}

		public void setOldName(String oldName) {
			this.oldName = oldName;
		}

		public InternalValue getInternalValue() {
			return internalValue;
		}

		public void setInternalValue(InternalValue internalValue) {
			this.internalValue = internalValue;
		}
	}

	private FilterChainSchemaFactoryWrapperFactory factory;

	private FilterChainSchemaFactoryWrapper visitor;

	private ObjectMapper objectMapper;

	private JsonSchema jsonSchema;

	@Before
	public void setup() throws JsonMappingException {
		factory = new FilterChainSchemaFactoryWrapperFactory(
				Arrays.<BeanPropertyFilter>asList(
						new RuntimeAnnotatedBeanPropertyFilter(FilterThatOne.class, Deprecated.class),

						// This test filter removes properties which names start
						// with 'value'
						new BeanPropertyFilter() {
							@Override
							public boolean test(BeanProperty property) {
								return !property.getName().startsWith("value");
							}
						}
				)
		);

		visitor = new FilterChainSchemaFactoryWrapper(factory);

		objectMapper = new ObjectMapper();
		objectMapper.acceptJsonFormatVisitor(TestBean.class, visitor);

		jsonSchema = visitor.finalSchema();
	}


	@Test
	public void shouldFilterOutOldNamePropertyOfTestBeanClass() throws JsonProcessingException {
		//given:
		ObjectSchema objectSchema = jsonSchema.asObjectSchema();
		//when:
		Map<String,JsonSchema> properties = objectSchema.getProperties();
		//then:
		assertFalse(properties.containsKey("oldName"));
	}


	@Test
	public void shouldNotFilterNamePropertyOfTestBeanClass() {
		//given:
		ObjectSchema objectSchema = jsonSchema.asObjectSchema();
		//when:
		Map<String,JsonSchema> properties = objectSchema.getProperties();
		//then:
		assertTrue(properties.containsKey("name"));
	}

	@Test
	public void shouldNotFilterInternalValuePropertyOfTestBeanClass() {
		//given:
		ObjectSchema objectSchema = jsonSchema.asObjectSchema();
		//when:
		Map<String,JsonSchema> properties = objectSchema.getProperties();
		//then:
		assertTrue(properties.containsKey("internalValue"));
	}

	@Test
	public void shouldFilterOutRequiredPropertyOfInternalValueClass() {
		//given:
		ObjectSchema objectSchema = jsonSchema.asObjectSchema()
				.getProperties()
				.get("internalValue")
				.asObjectSchema();
		//when:
		Map<String,JsonSchema> properties = objectSchema.getProperties();
		//then:
		assertFalse(properties.containsKey("required"));
	}

	@Test
	public void shouldFilterOutValuePropertyOfInternalValueClass() {
		//given:
		ObjectSchema objectSchema = jsonSchema.asObjectSchema()
				.getProperties()
				.get("internalValue")
				.asObjectSchema();
		//when:
		Map<String,JsonSchema> properties = objectSchema.getProperties();
		//then:
		assertFalse(properties.containsKey("value"));
	}
}
