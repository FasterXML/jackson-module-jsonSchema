package com.fasterxml.jackson.databind.jsonSchema.factories;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import com.fasterxml.jackson.databind.jsonSchema.types.*;

/**
 * @author jphelan
 *
 */
public class SchemaFactoryWrapper implements JsonFormatVisitorWrapper{

	private SchemaFactory delegate;
	protected FactoryProvider factoryProvider;
	protected SerializerProvider provider;
	protected SchemaProvider schemaProvider;
	
	/**
	 * 
	 */
	public SchemaFactoryWrapper() {
		schemaProvider = new SchemaProvider();
		factoryProvider = new FactoryProvider();
	}
	
	public JsonAnyFormatVisitor expectAnyFormat(JavaType convertedType) {
		AnySchema anySchema = schemaProvider.AnySchema();
		delegate = factoryProvider.SchemaFactory(anySchema);
		return factoryProvider.AnySchemaFactory(delegate, anySchema);
	}
	
	public JsonArrayFormatVisitor expectArrayFormat(JavaType convertedType) {
		ArraySchema arraySchema = schemaProvider.ArraySchema();
		delegate = factoryProvider.SchemaFactory(arraySchema);
		return (JsonArrayFormatVisitor) factoryProvider.ArraySchemaFactory(delegate, arraySchema);
	}
	
	
	public JsonBooleanFormatVisitor expectBooleanFormat(JavaType convertedType) {
		BooleanSchema booleanSchema = schemaProvider.BooleanSchema();
		delegate = factoryProvider.SchemaFactory(booleanSchema);
		ValueTypeSchemaFactory valueTypeSchemaFactory = factoryProvider.ValueTypeSchemaFactory(delegate, booleanSchema);
		return (JsonBooleanFormatVisitor) factoryProvider.BooleanSchemaFactory(valueTypeSchemaFactory, booleanSchema);
	}

	public JsonIntegerFormatVisitor expectIntegerFormat(JavaType convertedType) {
		IntegerSchema integerSchema = schemaProvider.IntegerSchema();
		delegate = factoryProvider.SchemaFactory(integerSchema);
		ValueTypeSchemaFactory valueTypeSchemaFactory = factoryProvider.ValueTypeSchemaFactory(delegate, integerSchema);
		return (JsonIntegerFormatVisitor) factoryProvider.IntegerSchemaFactory(valueTypeSchemaFactory, integerSchema);
	}

	public JsonNullFormatVisitor expectNullFormat(JavaType convertedType) {
		NullSchema nullSchema = schemaProvider.NullSchema();
		delegate = factoryProvider.SchemaFactory(nullSchema);
		return (JsonNullFormatVisitor) factoryProvider.NullSchemaFactory(delegate, nullSchema);
	}

	public JsonNumberFormatVisitor expectNumberFormat(JavaType convertedType) {
		NumberSchema numberSchema = schemaProvider.NumberSchema();
		delegate = factoryProvider.SchemaFactory(numberSchema);
		ValueTypeSchemaFactory valueTypeSchemaFactory = factoryProvider.ValueTypeSchemaFactory(delegate, numberSchema);
		return (JsonNumberFormatVisitor) factoryProvider.NumberSchemaFactory(valueTypeSchemaFactory, numberSchema);
	}
	
	public JsonObjectFormatVisitor expectObjectFormat(JavaType convertedType) {
		ObjectSchema objectSchema = schemaProvider.ObjectSchema();
		delegate = factoryProvider.SchemaFactory(objectSchema);
		return (JsonObjectFormatVisitor) factoryProvider.ObjectSchemaFactory(delegate, objectSchema);
	}

	public JsonStringFormatVisitor expectStringFormat(JavaType convertedType) {
		StringSchema stringSchema = schemaProvider.StringSchema();
		delegate = factoryProvider.SchemaFactory(stringSchema);
		ValueTypeSchemaFactory valueTypeSchemaFactory = factoryProvider.ValueTypeSchemaFactory(delegate, stringSchema);
		return (JsonStringFormatVisitor) factoryProvider.StringSchemaFactory(valueTypeSchemaFactory, stringSchema);
	}

	public JsonSchema finalSchema() {
		assert delegate != null : "SchemaFactory must envoke a delegate method before it can return a JsonSchema.";
		if (delegate == null) {
			return null;
		} else {
			return delegate.getSchema();
		}

	}

	public SerializerProvider getProvider() {
		return provider;
	}

	/**
	 * {@link SchemaFactory#provider}
	 * @param provider the provider to set
	 */
	public void setProvider(SerializerProvider provider) {
		this.provider = provider;
	}

	protected class FactoryProvider {

		
		SchemaFactoryWrapperProvider factoryWrapperProvider;
		
		public SchemaFactoryWrapperProvider getFactoryWrapperProvider() {
			return factoryWrapperProvider;
		}
		
		public void setFactoryWrapperProvider(
				SchemaFactoryWrapperProvider factoryWrapperProvider) {
			this.factoryWrapperProvider = factoryWrapperProvider;
		}
		/**
		 * 
		 */
		public FactoryProvider() {
			factoryWrapperProvider = new SchemaFactoryWrapperProvider();
		}
		/**
		 * @param delegate
		 * @param schema
		 * @return
		 */
		public JsonAnyFormatVisitor AnySchemaFactory(SchemaFactory delegate,
				AnySchema anySchema) {
			return null;
		}

		/**
		 * @param parent
		 * @param arraySchema
		 * @return
		 */
		public JsonArrayFormatVisitor ArraySchemaFactory(
				SchemaFactory parent, ArraySchema arraySchema) {
			ArraySchemaFactory arraySchemaFactory = new ArraySchemaFactory(parent, arraySchema);
			arraySchemaFactory.setFactoryWrapperProvider(factoryWrapperProvider);
			return arraySchemaFactory;
		}

		/**
		 * @param delegate
		 * @param booleanSchema
		 * @return
		 */
		public JsonBooleanFormatVisitor BooleanSchemaFactory(ValueTypeSchemaFactory parent, BooleanSchema booleanSchema) {
			return new BooleanSchemaFactory(parent, booleanSchema);
		}

		/**
		 * @param parent
		 * @param integerSchema
		 * @return
		 */
		public JsonIntegerFormatVisitor IntegerSchemaFactory(
				ValueTypeSchemaFactory parent,
				IntegerSchema integerSchema) {
			return new IntegerSchemaFactory(parent, integerSchema);
		}

		/**
		 * @param parent
		 * @param nullSchema
		 * @return
		 */
		public JsonNullFormatVisitor NullSchemaFactory(SchemaFactory parent,
				NullSchema nullSchema) {
			return new NullSchemaFactory(parent, nullSchema);
		}

		/**
		 * @param parent
		 * @param numberSchema
		 * @return
		 */
		public JsonNumberFormatVisitor NumberSchemaFactory(
				ValueTypeSchemaFactory parent,
				NumberSchema numberSchema) {
			return new NumberSchemaFactory(parent, numberSchema);
		}

		/**
		 * @param delegate
		 * @param objectSchema
		 * @return
		 */
		public JsonObjectFormatVisitor ObjectSchemaFactory(
				SchemaFactory parent, ObjectSchema objectSchema) {
			
			ObjectSchemaFactory objectSchemaFactory = new ObjectSchemaFactory(parent, objectSchema);
			objectSchemaFactory.setFactoryWrapperProvider(factoryWrapperProvider);
			return objectSchemaFactory;
		}

		/**
		 * @param schema 
		 * @return
		 */
		public SchemaFactory SchemaFactory(JsonSchema schema) {
			return new SchemaFactory(provider, schema);
		}

		/**
		 * @param valueTypeSchemaFactory
		 * @param stringSchema
		 * @return
		 */
		public JsonStringFormatVisitor StringSchemaFactory(
				ValueTypeSchemaFactory parent,
				StringSchema stringSchema) {
			return new StringSchemaFactory(parent, stringSchema);
		}

		/**
		 * @param parent
		 * @param valueTypeSchema
		 * @return
		 */
		public ValueTypeSchemaFactory ValueTypeSchemaFactory(
				SchemaFactory parent, ValueTypeSchema valueTypeSchema) {
			return new ValueTypeSchemaFactory(parent, valueTypeSchema);
		}}
	
	public class SchemaFactoryWrapperProvider {
		public SchemaFactoryWrapper SchemaFactoryWrapper() { 
			SchemaFactoryWrapper wrapper = new SchemaFactoryWrapper();
			wrapper.setProvider(getProvider());
			return wrapper;
		}
	}
	
	protected class SchemaProvider {

		/**
		 * @return
		 */
		public AnySchema AnySchema() {
			return new AnySchema();
		}

		/**
		 * @return
		 */
		public ArraySchema ArraySchema() {
			return new ArraySchema();
		}

		/**
		 * @return
		 */
		public BooleanSchema BooleanSchema() {
			return new BooleanSchema();
		}

		/**
		 * @return
		 */
		public IntegerSchema IntegerSchema() {
			return new IntegerSchema();
		}

		/**
		 * @return
		 */
		public NullSchema NullSchema() {
			return new NullSchema();
		}

		/**
		 * @return
		 */
		public NumberSchema NumberSchema() {
			return new NumberSchema();
		}

		/**
		 * @return
		 */
		public ObjectSchema ObjectSchema() {
			return new ObjectSchema();
		}

		/**
		 * @return
		 */
		public StringSchema StringSchema() {
			return new StringSchema();
		}}
	
}
