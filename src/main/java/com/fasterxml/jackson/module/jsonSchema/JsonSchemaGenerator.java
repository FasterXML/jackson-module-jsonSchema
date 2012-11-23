package com.fasterxml.jackson.module.jsonSchema;

import com.fasterxml.jackson.databind.*;

import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;

/**
 * Convenience class that wraps JSON Schema generation functionality.
 * 
 * @author tsaloranta
 */
public class JsonSchemaGenerator
{
    protected final ObjectMapper _mapper;
    
    public JsonSchemaGenerator(ObjectMapper mapper) {
        _mapper = mapper;
    }

    public JsonSchema generateSchema(Class<?> type) throws JsonMappingException
    {
        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        _mapper.acceptJsonFormatVisitor(type, visitor);
        return visitor.finalSchema();
    }

    public JsonSchema generateSchema(JavaType type) throws JsonMappingException
    {
        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        _mapper.acceptJsonFormatVisitor(type, visitor);
        return visitor.finalSchema();
    }
}
