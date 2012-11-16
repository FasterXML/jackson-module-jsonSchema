package com.fasterxml.jackson.databind.jsonSchema.factories;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import com.fasterxml.jackson.databind.jsonSchema.types.*;

/**
 * @author jphelan
 *
 */
public class SchemaFactoryWrapper implements JsonFormatVisitorWrapper {

	private SchemaFactory delegate;
	protected FactoryProvider factoryProvider;
	protected SerializerProvider provider;
	protected SchemaProvider schemaProvider;
	
	public SchemaFactoryWrapper() {
		schemaProvider = new SchemaProvider();
		factoryProvider = new FactoryProvider();
	}

	/*
	/*********************************************************************
	/* JsonFormatVisitorWrapper implementation
     /*********************************************************************
	 */
	
	public JsonAnyFormatVisitor expectAnyFormat(JavaType convertedType) {
		AnySchema anySchema = schemaProvider.anySchema();
		delegate = factoryProvider.schemaFactory(anySchema);
		return factoryProvider.anySchemaFactory(delegate, anySchema);
	}
	
	public JsonArrayFormatVisitor expectArrayFormat(JavaType convertedType) {
		ArraySchema arraySchema = schemaProvider.arraySchema();
		delegate = factoryProvider.schemaFactory(arraySchema);
		return (JsonArrayFormatVisitor) factoryProvider.arraySchemaFactory(delegate, arraySchema);
	}
	
	
	public JsonBooleanFormatVisitor expectBooleanFormat(JavaType convertedType) {
		BooleanSchema booleanSchema = schemaProvider.booleanSchema();
		delegate = factoryProvider.schemaFactory(booleanSchema);
		ValueTypeSchemaFactory valueTypeSchemaFactory = factoryProvider.valueTypeSchemaFactory(delegate, booleanSchema);
		return (JsonBooleanFormatVisitor) factoryProvider.booleanSchemaFactory(valueTypeSchemaFactory, booleanSchema);
	}

	public JsonIntegerFormatVisitor expectIntegerFormat(JavaType convertedType) {
		IntegerSchema integerSchema = schemaProvider.integerSchema();
		delegate = factoryProvider.schemaFactory(integerSchema);
		ValueTypeSchemaFactory valueTypeSchemaFactory = factoryProvider.valueTypeSchemaFactory(delegate, integerSchema);
		return (JsonIntegerFormatVisitor) factoryProvider.integerSchemaFactory(valueTypeSchemaFactory, integerSchema);
	}

	public JsonNullFormatVisitor expectNullFormat(JavaType convertedType) {
		NullSchema nullSchema = schemaProvider.nullSchema();
		delegate = factoryProvider.schemaFactory(nullSchema);
		return (JsonNullFormatVisitor) factoryProvider.nullSchemaFactory(delegate, nullSchema);
	}

	public JsonNumberFormatVisitor expectNumberFormat(JavaType convertedType) {
		NumberSchema numberSchema = schemaProvider.numberSchema();
		delegate = factoryProvider.schemaFactory(numberSchema);
		ValueTypeSchemaFactory valueTypeSchemaFactory = factoryProvider.valueTypeSchemaFactory(delegate, numberSchema);
		return (JsonNumberFormatVisitor) factoryProvider.numberSchemaFactory(valueTypeSchemaFactory, numberSchema);
	}
	
	public JsonObjectFormatVisitor expectObjectFormat(JavaType convertedType) {
		ObjectSchema objectSchema = schemaProvider.objectSchema();
		delegate = factoryProvider.schemaFactory(objectSchema);
		return (JsonObjectFormatVisitor) factoryProvider.objectSchemaFactory(delegate, objectSchema);
	}

	public JsonStringFormatVisitor expectStringFormat(JavaType convertedType) {
		StringSchema stringSchema = schemaProvider.StringSchema();
		delegate = factoryProvider.schemaFactory(stringSchema);
		ValueTypeSchemaFactory valueTypeSchemaFactory = factoryProvider.valueTypeSchemaFactory(delegate, stringSchema);
		return (JsonStringFormatVisitor) factoryProvider.stringSchemaFactory(valueTypeSchemaFactory, stringSchema);
	}

     /*
     /*********************************************************************
     /* API
     /*********************************************************************
      */
	
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

    /*
    /*********************************************************************
    /* Helper classes
    /*********************************************************************
    */
	
    protected class FactoryProvider
    {		
        protected SchemaFactoryWrapperProvider factoryWrapperProvider;
		
        public SchemaFactoryWrapperProvider getFactoryWrapperProvider() {
            return factoryWrapperProvider;
        }
		
		public void setFactoryWrapperProvider(SchemaFactoryWrapperProvider factoryWrapperProvider) {
			this.factoryWrapperProvider = factoryWrapperProvider;
		}

		public FactoryProvider() {
			factoryWrapperProvider = new SchemaFactoryWrapperProvider();
		}

		public JsonAnyFormatVisitor anySchemaFactory(SchemaFactory delegate,
				AnySchema anySchema) {
			return null;
		}

		public JsonArrayFormatVisitor arraySchemaFactory(
				SchemaFactory parent, ArraySchema arraySchema) {
			ArraySchemaFactory arraySchemaFactory = new ArraySchemaFactory(parent, arraySchema);
			arraySchemaFactory.setFactoryWrapperProvider(factoryWrapperProvider);
			return arraySchemaFactory;
		}

		public JsonBooleanFormatVisitor booleanSchemaFactory(ValueTypeSchemaFactory parent, BooleanSchema booleanSchema) {
			return new BooleanSchemaFactory(parent, booleanSchema);
		}

		public JsonIntegerFormatVisitor integerSchemaFactory(
				ValueTypeSchemaFactory parent,
				IntegerSchema integerSchema) {
			return new IntegerSchemaFactory(parent, integerSchema);
		}

		public JsonNullFormatVisitor nullSchemaFactory(SchemaFactory parent,
				NullSchema nullSchema) {
			return new NullSchemaFactory(parent, nullSchema);
		}

		public JsonNumberFormatVisitor numberSchemaFactory(
				ValueTypeSchemaFactory parent,
				NumberSchema numberSchema) {
			return new NumberSchemaFactory(parent, numberSchema);
		}

		public JsonObjectFormatVisitor objectSchemaFactory(
				SchemaFactory parent, ObjectSchema objectSchema) {
			
			ObjectSchemaFactory objectSchemaFactory = new ObjectSchemaFactory(parent, objectSchema);
			objectSchemaFactory.setFactoryWrapperProvider(factoryWrapperProvider);
			return objectSchemaFactory;
		}

		public SchemaFactory schemaFactory(JsonSchema schema) {
			return new SchemaFactory(provider, schema);
		}

		public JsonStringFormatVisitor stringSchemaFactory(
				ValueTypeSchemaFactory parent,
				StringSchema stringSchema) {
			return new StringSchemaFactory(parent, stringSchema);
		}

		public ValueTypeSchemaFactory valueTypeSchemaFactory(
				SchemaFactory parent, ValueTypeSchema valueTypeSchema) {
			return new ValueTypeSchemaFactory(parent, valueTypeSchema);
		}
	}
	
	public class SchemaFactoryWrapperProvider {
		public SchemaFactoryWrapper SchemaFactoryWrapper() { 
			SchemaFactoryWrapper wrapper = new SchemaFactoryWrapper();
			wrapper.setProvider(getProvider());
			return wrapper;
		}
	}

    protected class SchemaProvider
    {
        public AnySchema anySchema() {
            return new AnySchema();
        }

        public ArraySchema arraySchema() {
            return new ArraySchema();
        }

        public BooleanSchema booleanSchema() {
            return new BooleanSchema();
        }

        public IntegerSchema integerSchema() {
            return new IntegerSchema();
        }

        public NullSchema nullSchema() {
            return new NullSchema();
        }

        public NumberSchema numberSchema() {
            return new NumberSchema();
        }

        public ObjectSchema objectSchema() {
            return new ObjectSchema();
        }

        public StringSchema StringSchema() {
            return new StringSchema();
        }
    }
}
