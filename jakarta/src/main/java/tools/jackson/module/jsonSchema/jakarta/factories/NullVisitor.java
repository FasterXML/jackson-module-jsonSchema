package tools.jackson.module.jsonSchema.jakarta.factories;

import tools.jackson.databind.jsonFormatVisitors.JsonNullFormatVisitor;
import tools.jackson.module.jsonSchema.jakarta.types.NullSchema;

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
