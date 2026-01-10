package tools.jackson.module.jsonSchema.factories;

import tools.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import tools.jackson.module.jsonSchema.types.AnySchema;

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
