package com.fasterxml.jackson.databind.jsonSchema.factories;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonSchema.types.ArraySchema;
import com.fasterxml.jackson.databind.jsonSchema.types.JsonSchema;

public class ArraySchemaFactory implements JsonArrayFormatVisitor {

	protected SchemaFactory parent; 
	protected ArraySchema schema;
	protected BeanProperty _property;
	
	public ArraySchemaFactory(SchemaFactory parent, BeanProperty property, ArraySchema schema) {
		this.parent = parent;
		this.schema = schema;
	}

	public ArraySchemaFactory(SchemaFactory schemaFactory, ArraySchema schema) {
		this(schemaFactory, null, schema);
	}

	/**
	 * @param provider
	 */
	public ArraySchemaFactory(SerializerProvider provider) {
		parent = null;
		setProvider(provider);
		schema = new ArraySchema();
	}

	public void itemsFormat(JavaType contentType) {
		// An array of object matches any values, thus we leave the schema empty.
        if (contentType.getRawClass() != Object.class) {
        	
            JsonSerializer<Object> ser;
			try {
				ser = getProvider().findValueSerializer(contentType, _property);
				if (ser instanceof JsonFormatVisitable) {
	            	SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
	            	visitor.setProvider(parent.getProvider());
	                ((JsonFormatVisitable) ser).acceptJsonFormatVisitor(visitor, contentType);
	                schema.setItemsSchema(visitor.finalSchema());
	            }
			} catch (JsonMappingException e) {
				//TODO: log error
			}   
        }
	}
	
	public void itemsFormat(JsonFormatTypes format) {
		schema.setItemsSchema(JsonSchema.minimalForFormat(format));
	}

	public JsonSchema getSchema() {
		return schema;
	}

	public SerializerProvider getProvider() {
		return parent.getProvider();
	}

	public void setProvider(SerializerProvider provider) {
		parent.setProvider(provider);
	}
	

}
