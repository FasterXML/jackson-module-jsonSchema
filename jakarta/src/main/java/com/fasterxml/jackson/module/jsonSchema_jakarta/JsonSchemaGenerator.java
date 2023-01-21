package com.fasterxml.jackson.module.jsonSchema_jakarta;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.module.jsonSchema_jakarta.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema_jakarta.factories.WrapperFactory;

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

    /**
     * @since 2.8.1
     */
    private final SchemaFactoryWrapper _visitor;
    
    public JsonSchemaGenerator(ObjectMapper mapper) {
        this(mapper, (WrapperFactory) null);
    }

    public JsonSchemaGenerator(ObjectMapper mapper, WrapperFactory wrapperFactory) {
        _mapper = mapper;
        _writer = mapper.writer();
        _wrapperFactory = (wrapperFactory == null) ? new WrapperFactory() : wrapperFactory;
        _visitor = null;
    }

    /**
     * NOTE: resulting generator is NOT thread-safe, since typically {@link SchemaFactoryWrapper}
     * being passed is not thread-safe.
     *
     * @since 2.8.1
     */
    public JsonSchemaGenerator(ObjectMapper mapper, SchemaFactoryWrapper visitor) {
        this(mapper.writer(), visitor);
    }

    /**
     * @since 2.6
     */
    public JsonSchemaGenerator(ObjectWriter w) {
        this(w, (WrapperFactory) null);
    }

    /**
     * @since 2.6
     */
    public JsonSchemaGenerator(ObjectWriter w, WrapperFactory wrapperFactory) {
        _mapper = null;
        _writer = w;
        _wrapperFactory = (wrapperFactory == null) ? new WrapperFactory() : wrapperFactory;
        _visitor = null;
    }

    /**
     * @since 2.8.1
     */
    public JsonSchemaGenerator(ObjectWriter w, SchemaFactoryWrapper visitor) {
        _mapper = null;
        _writer = w;
        _wrapperFactory = null;
        if (visitor == null) {
            throw new IllegalArgumentException("Missing `visitor`");
        }
        _visitor = visitor;
    }
    
    public JsonSchema generateSchema(Class<?> type) throws JsonMappingException
    {
        SchemaFactoryWrapper visitor = _visitor;
        if (visitor == null) {
            visitor = _wrapperFactory.getWrapper(null);
        }
        _writer.acceptJsonFormatVisitor(type, visitor);
        return visitor.finalSchema();
    }

    public JsonSchema generateSchema(JavaType type) throws JsonMappingException
    {
        SchemaFactoryWrapper visitor = _visitor;
        if (visitor == null) {
            visitor = _wrapperFactory.getWrapper(null);
        }
        _writer.acceptJsonFormatVisitor(type, visitor);
        return visitor.finalSchema();
    }
}
