package com.fasterxml.jackson.databind.jsonSchema.factories;


import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.jsonSchema.types.JsonSchema;
import com.fasterxml.jackson.databind.jsonSchema.types.ObjectSchema;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;

public class ObjectSchemaFactory extends SchemaFactory 
	implements JsonObjectFormatVisitor {

	protected SchemaFactory parent;
	protected ObjectSchema objectSchema;
	
	public ObjectSchemaFactory(SchemaFactory parent) {
		this.parent = parent;
		setProvider(parent.getProvider());
		objectSchema = new ObjectSchema();
	}
	
	/**
	 * @param provider
	 */
	public ObjectSchemaFactory(SerializerProvider provider) {
		parent = null;
		setProvider(provider);
		objectSchema = new ObjectSchema();
	}

	public JsonSchema getSchema() {
		return objectSchema;
	}

	private JsonSerializer<Object> getSer(BeanProperty writer) {
		JsonSerializer<Object> ser = null;
		if (writer instanceof BeanPropertyWriter) {
			ser = ((BeanPropertyWriter)writer).getSerializer();
		}
		if (ser == null) {
			Class<?>	serType = writer.getType().getRawClass();
			try {
				return getProvider().findValueSerializer(serType, writer);
			} catch (JsonMappingException e) {
				// TODO: log error
			}
		}
		return ser;
	}	
	
	protected JsonSchema propertySchema(BeanProperty writer) {
		SchemaFactoryProvider visitor = new SchemaFactoryProvider();
		visitor.setProvider(provider);
		JsonSerializer<Object> ser = getSer(writer);
		if (ser != null && ser instanceof JsonFormatVisitable) {
			((JsonFormatVisitable)ser).acceptJsonFormatVisitor(visitor, writer.getType());
		} else {
			visitor.expectAnyFormat(writer.getType());
		}
		return visitor.finalSchema();
	}
	
	protected JsonSchema propertySchema(JsonFormatVisitable handler, JavaType propertyTypeHint) {
		SchemaFactoryProvider visitor = new SchemaFactoryProvider();
		visitor.setProvider(provider);
		handler.acceptJsonFormatVisitor(visitor, propertyTypeHint);
		return visitor.finalSchema();
	}
	
	public void property(BeanProperty writer) {
		objectSchema.putProperty(writer.getName(), propertySchema(writer));
	}

	public void optionalProperty(BeanProperty writer) {
		objectSchema.putOptionalProperty(writer.getName(), propertySchema(writer));
	}
	
	public void property(String name, JsonFormatVisitable handler, JavaType propertyTypeHint) {
		objectSchema.putProperty(name, propertySchema(handler, propertyTypeHint));
	}
	
	public void optionalProperty(String name, JsonFormatVisitable handler, JavaType propertyTypeHint) {
		objectSchema.putOptionalProperty(name, propertySchema(handler, propertyTypeHint));
	}
	
	public void property(String name) {
		objectSchema.putProperty(name, JsonSchema.minimalForFormat(JsonFormatTypes.ANY));
	}
	
	public void optionalProperty(String name) {
		objectSchema.putOptionalProperty(name, JsonSchema.minimalForFormat(JsonFormatTypes.ANY));
	}

}
