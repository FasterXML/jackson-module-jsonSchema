package com.fasterxml.jackson.module.jsonSchema;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.factories.WrapperFactory;

/**
 * Convenience class that wraps JSON Schema generation functionality.
 * 
 * @author tsaloranta
 */
public class JsonSchemaGenerator
{
    /**
     * @deprecated Since 2.6
     */
    @Deprecated
    protected final ObjectMapper _mapper;

    /**
     * @since 2.6
     */
    protected final ObjectWriter _writer;

    private final WrapperFactory _wrapperFactory;

    public JsonSchemaGenerator(ObjectMapper mapper) {
        this(mapper, null);
    }

    public JsonSchemaGenerator(ObjectMapper mapper, WrapperFactory wrapperFactory) {
        _mapper = mapper;
        _writer = mapper.writer();
        _wrapperFactory = (wrapperFactory == null) ? new WrapperFactory() : wrapperFactory;
    }

    /**
     * @since 2.6
     */
    public JsonSchemaGenerator(ObjectWriter w) {
        this(w, null);
    }

    /**
     * @since 2.6
     */
    public JsonSchemaGenerator(ObjectWriter w, WrapperFactory wrapperFactory) {
        _mapper = null;
        _writer = w;
        _wrapperFactory = (wrapperFactory == null) ? new WrapperFactory() : wrapperFactory;
    }
    
    public JsonSchema generateSchema(Class<?> type) throws JsonMappingException
    {
        SchemaFactoryWrapper visitor = _wrapperFactory.getWrapper(null);
        _writer.acceptJsonFormatVisitor(type, visitor);
        return visitor.finalSchema();
    }

    public JsonSchema generateSchema(JavaType type) throws JsonMappingException
    {
        SchemaFactoryWrapper visitor = _wrapperFactory.getWrapper(null);
        _writer.acceptJsonFormatVisitor(type, visitor);
        return visitor.finalSchema();
    }
}
