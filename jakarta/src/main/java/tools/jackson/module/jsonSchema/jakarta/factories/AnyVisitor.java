package tools.jackson.module.jsonSchema.jakarta.factories;

import tools.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import tools.jackson.module.jsonSchema.jakarta.types.AnySchema;

public class AnyVisitor extends JsonAnyFormatVisitor.Base
    implements JsonSchemaProducer
{
    protected final AnySchema schema;
	
    public AnyVisitor(AnySchema schema) {
        this.schema = schema; 
    }

    /*
    /*********************************************************************
    /* JsonSchemaProducer
    /*********************************************************************
     */

    @Override
    public AnySchema getSchema() {
        return schema;
    }

    /*
    /*********************************************************************
    /* AnyVisitor: no additional methods...
    /*********************************************************************
     */
}
