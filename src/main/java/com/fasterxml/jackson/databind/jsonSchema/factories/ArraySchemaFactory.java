package com.fasterxml.jackson.databind.jsonSchema.factories;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonSchema.factories.SchemaFactoryWrapper.SchemaFactoryWrapperProvider;
import com.fasterxml.jackson.databind.jsonSchema.types.ArraySchema;
import com.fasterxml.jackson.databind.jsonSchema.types.JsonSchema;

public class ArraySchemaFactory implements JsonArrayFormatVisitor, SchemaProducer {

	protected BeanProperty _property; 
	protected SchemaFactoryWrapperProvider factoryWrapperProvider;
	protected SchemaFactory parent;
	protected ArraySchema schema;
	
	public ArraySchemaFactory(SchemaFactory schemaFactory, ArraySchema schema) {
		this(schemaFactory, null, schema);
	}

	public ArraySchemaFactory(SchemaFactory parent, BeanProperty property, ArraySchema schema) {
		this.parent = parent;
		this.schema = schema;
	}

	public BeanProperty get_property() {
		return _property;
	}
	
	public SchemaFactoryWrapperProvider getFactoryWrapperProvider() {
		return factoryWrapperProvider;
	}

	public SchemaFactory getParent() {
		return parent;
	}

	public SerializerProvider getProvider() {
		return parent.getProvider();
	}

	public JsonSchema getSchema() {
		return schema;
	}

	public void itemsFormat(JavaType contentType) throws JsonMappingException
	{
		// An array of object matches any values, thus we leave the schema empty.
        if (contentType.getRawClass() != Object.class) {
            JsonSerializer<Object> ser;
            ser = getProvider().findValueSerializer(contentType, _property);
            if (ser instanceof JsonFormatVisitable) {
                SchemaFactoryWrapper visitor = factoryWrapperProvider.schemaFactoryWrapper();
                visitor.setProvider(parent.getProvider());
                ((JsonFormatVisitable) ser).acceptJsonFormatVisitor(visitor, contentType);
                schema.setItemsSchema(visitor.finalSchema());
            }
        }
	}

	public void itemsFormat(JsonFormatTypes format) throws JsonMappingException
	{
		schema.setItemsSchema(JsonSchema.minimalForFormat(format));
	}

	public void set_property(BeanProperty _property) {
		this._property = _property;
	}

	public void setFactoryWrapperProvider(
			SchemaFactoryWrapperProvider factoryWrapperProvider) {
		this.factoryWrapperProvider = factoryWrapperProvider;
	}

	public void setParent(SchemaFactory parent) {
		this.parent = parent;
	}

	public void setProvider(SerializerProvider provider) {
		parent.setProvider(provider);
	}

	public void setSchema(ArraySchema schema) {
		this.schema = schema;
	}
	

}
