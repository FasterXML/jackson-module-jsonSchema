package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.fasterxml.jackson.module.jsonSchema.types.AnySchema;

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

    public AnySchema getSchema() {
        return schema;
    }

    /*
    /*********************************************************************
    /* JsonAnyFormatVisitor: no methods...
    /*********************************************************************
     */
}
