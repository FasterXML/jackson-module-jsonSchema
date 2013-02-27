package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.types.ArraySchema;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;

/**
 * Adds a title to every object schema, either root level or nested. Generally
 * useful for writing additional properties to a schema.
 * 
 * @author jphelan
 */
public class TitleSchemaFactoryWrapper extends SchemaFactoryWrapper {

	/**
	 * Adds writes the type as the title of the schema.
	 * 
	 * @param schema
	 *            The schema who's title to set.
	 * @param type
	 *            The type of the object represented by the schema.
	 */
	private void addTitle(JsonSchema schema, JavaType type) {
		if (!schema.isSimpleTypeSchema()) {
			throw new RuntimeException("given non simple type schema: "
					+ schema.getType());
		} else {
			schema.asSimpleTypeSchema().setTitle(type.getGenericSignature());
		}

	}

	/**
	 * create a child {@link TitleSchemaFactoryWrapper} for nested cases of
	 * visiting objects.
	 * 
	 * @return new {@link TitleSchemaFactoryWrapper}
	 */
	private TitleSchemaFactoryWrapper childWrapper() {
		TitleSchemaFactoryWrapper wrapper = new TitleSchemaFactoryWrapper();
		wrapper.setProvider(provider);
		return wrapper;
	}

	@Override
	public JsonObjectFormatVisitor expectObjectFormat(JavaType convertedType) {
		ObjectSchema objectSchema = schemaProvider.objectSchema();
		schema = objectSchema;
		ObjectVisitor visitor = new TitleObjectVisitor(provider, objectSchema);

		// could add other properties here
		addTitle(visitor.getSchema(), convertedType);
		return visitor;
	}

	@Override
	public JsonArrayFormatVisitor expectArrayFormat(JavaType convertedType) {
		ArraySchema arraySchema = schemaProvider.arraySchema();
		schema = arraySchema;
		ArrayVisitor visitor = new TitleArrayVisitor(provider, arraySchema);
		addTitle(visitor.getSchema(), convertedType);
		return visitor;
	}

	/**
	 * ObjectVisitor instantiates a new {@link SchemaFactoryWrapper} so we have
	 * to override that action with our new {@link TitleSchemaFactoryWrapper}
	 * instead.
	 * 
	 * @author jphelan
	 * 
	 */
	private class TitleObjectVisitor extends ObjectVisitor {

		public TitleObjectVisitor(SerializerProvider provider,
				ObjectSchema schema) {
			super(provider, schema);
		}

		@Override
		public void optionalProperty(BeanProperty writer)
				throws JsonMappingException {
			property(writer);
		}

		@Override
		public void optionalProperty(String name, JsonFormatVisitable handler,
				JavaType propertyTypeHint) throws JsonMappingException {
			property(name, handler, propertyTypeHint);
		}

		@Override
		protected JsonSchema propertySchema(JsonFormatVisitable handler,
				JavaType propertyTypeHint) throws JsonMappingException {
			TitleSchemaFactoryWrapper visitor = childWrapper();
			visitor.setProvider(provider);
			handler.acceptJsonFormatVisitor(visitor, propertyTypeHint);
			JsonSchema finalSchema = visitor.finalSchema();
			return finalSchema;

		}

		@Override
		protected JsonSchema propertySchema(BeanProperty writer)
				throws JsonMappingException {
			if (writer == null) {
				throw new IllegalArgumentException("Null writer");
			}
			TitleSchemaFactoryWrapper visitor = childWrapper();
			visitor.setProvider(provider);
			JsonSerializer<Object> ser = getSer(writer);
			if (ser != null) {
				JavaType type = writer.getType();
				if (type == null) {
					throw new IllegalStateException(
							"Missing type for property '" + writer.getName()
									+ "'");
				}
				ser.acceptJsonFormatVisitor(visitor, type);
			}
			return visitor.finalSchema();
		}
	}

	/**
	 * ArrayVisitor instantiates a new {@link SchemaFactoryWrapper} so we have
	 * to override that action with our new {@link TitleSchemaFactoryWrapper}
	 * instead.
	 * 
	 * @author jphelan
	 * 
	 */
	private class TitleArrayVisitor extends ArrayVisitor {

		public TitleArrayVisitor(SerializerProvider provider, ArraySchema schema) {
			super(provider, schema);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.fasterxml.jackson.module.jsonSchema.factories.ArrayVisitor#
		 * itemsFormat
		 * (com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
		 * , com.fasterxml.jackson.databind.JavaType)
		 */
		@Override
		public void itemsFormat(JsonFormatVisitable handler,
				JavaType contentType) throws JsonMappingException {
			// An array of object matches any values, thus we leave the schema
			// empty.
			if (contentType.getRawClass() != Object.class) {
				TitleSchemaFactoryWrapper visitor = childWrapper();
				visitor.setProvider(provider);
				handler.acceptJsonFormatVisitor(visitor, contentType);
				JsonSchema finalSchema = visitor.finalSchema();
				schema.setItemsSchema(finalSchema);
			}
		}

	}

}
