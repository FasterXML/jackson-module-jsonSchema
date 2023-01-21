package com.fasterxml.jackson.module.jsonSchema.jakarta.factories;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonNullFormatVisitor;
import com.fasterxml.jackson.module.jsonSchema.jakarta.types.NullSchema;

public class NullVisitor extends JsonNullFormatVisitor.Base
    implements JsonSchemaProducer
{
	protected final NullSchema schema;
	
	public NullVisitor(NullSchema schema) {
		this.schema = schema;
	}

	@Override
	public NullSchema getSchema() {
		return schema;
	}
}
