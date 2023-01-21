package com.fasterxml.jackson.module.jsonSchema_jakarta.factories;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.fasterxml.jackson.module.jsonSchema_jakarta.types.AnySchema;

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
