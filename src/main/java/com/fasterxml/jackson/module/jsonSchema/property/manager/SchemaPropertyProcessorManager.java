package com.fasterxml.jackson.module.jsonSchema.property.manager;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.property.SchemaPropertyProcessor;

/**
 * @author amerritt
 * 
 * @since 4.0
 */
public class SchemaPropertyProcessorManager implements SchemaPropertyProcessorManagerApi
{
    private List<SchemaPropertyProcessor> processors = new ArrayList<>();

    public SchemaPropertyProcessorManager() {

    }

    public SchemaPropertyProcessorManager(List<SchemaPropertyProcessor> processors) {
        this.processors = processors;
    }

    @Override
    public void registerSchemaPropertyProcessor(SchemaPropertyProcessor processor) {
        getProcessors().add(processor);
    }

    @Override
    public void process(JsonSchema schema, BeanProperty prop) {
        getProcessors().forEach(processor -> processor.process(schema, prop));
    }

    @Override
    public void process(JsonSchema schema, JavaType type) {
        getProcessors().forEach(processor -> processor.process(schema, type));
    }

    @Override
    public List<SchemaPropertyProcessor> getProcessors() {
        return processors;
    }

    @Override
    public void setProcessors(List<SchemaPropertyProcessor> processors) {
        this.processors = processors;
    }

    /* (non-Javadoc)
     * Not doing anything special per new type for this so we just return the same instance.
     * @see com.fasterxml.jackson.module.jsonSchema.property.manager.SchemaPropertyProcessorManagerApi#createCopyForType(java.lang.Class)
     */
    @Override
    public SchemaPropertyProcessorManagerApi createCopyForType(Class<?> type) {
        return this;
    }
}
