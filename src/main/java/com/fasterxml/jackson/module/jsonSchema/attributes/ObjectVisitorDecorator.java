package com.fasterxml.jackson.module.jsonSchema.attributes;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.JsonSchemaProducer;
import com.fasterxml.jackson.module.jsonSchema.factories.ObjectVisitor;

public class ObjectVisitorDecorator implements JsonObjectFormatVisitor, JsonSchemaProducer {

	protected ObjectVisitor objectVisitor;

	public ObjectVisitorDecorator(ObjectVisitor objectVisitor) {
		this.objectVisitor = objectVisitor;
	}

	@Override
	public JsonSchema getSchema() {
		return objectVisitor.getSchema();
	}

	@Override
	public SerializerProvider getProvider() {
		return objectVisitor.getProvider();
	}

	@Override @Deprecated
	public void setProvider(SerializerProvider serializerProvider) {
		objectVisitor.setProvider(serializerProvider);
	}

	@Override
	public void optionalProperty(BeanProperty writer) throws JsonMappingException {
		objectVisitor.optionalProperty(writer);
	}

	@Override
	public void optionalProperty(String name, JsonFormatVisitable handler, JavaType propertyTypeHint)
			throws JsonMappingException {
		objectVisitor.optionalProperty(name, handler, propertyTypeHint);
	}

	@Override
	public void property(BeanProperty writer) throws JsonMappingException {
		objectVisitor.property(writer);
	}

	@Override
	public void property(String name, JsonFormatVisitable handler, JavaType propertyTypeHint)
			throws JsonMappingException {
		objectVisitor.property(name, handler, propertyTypeHint);
	}

}
