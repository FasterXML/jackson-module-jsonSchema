package com.fasterxml.jackson.module.jsonSchema;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.factories.WrapperFactory;
import com.fasterxml.jackson.module.jsonSchema.types.SimpleTypeSchema;

/**
 * Convenience class that wraps JSON Schema generation functionality.
 * 
 * @author tsaloranta
 */
public class JsonSchemaGenerator
{
    protected final ObjectMapper _mapper;
    
	private final WrapperFactory _wrapperFactory;
    
    public JsonSchemaGenerator(ObjectMapper mapper) {
        this(mapper, null);
    }
    
    public JsonSchemaGenerator(ObjectMapper mapper, WrapperFactory wrapperFactory) {
        _mapper = mapper;
    	_wrapperFactory = wrapperFactory == null ? new WrapperFactory() : wrapperFactory;
    }

    public SimpleTypeSchema generateSchema(Class<?> type) throws JsonMappingException
    {
        SchemaFactoryWrapper visitor = _wrapperFactory.getWrapper(_mapper == null ? null : _mapper.getSerializerProvider());
        _mapper.acceptJsonFormatVisitor(type, visitor);
        return visitor.finalSchema();
    }

    public SimpleTypeSchema generateSchema(JavaType type) throws JsonMappingException
    {
        SchemaFactoryWrapper visitor = _wrapperFactory.getWrapper(_mapper == null ? null : _mapper.getSerializerProvider());
        _mapper.acceptJsonFormatVisitor(type, visitor);
        return visitor.finalSchema();
    }
}
