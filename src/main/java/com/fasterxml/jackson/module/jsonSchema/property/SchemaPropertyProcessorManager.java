package com.fasterxml.jackson.module.jsonSchema.property;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

/**
 * @author amerritt
 * 
 * @since 4.0
 */
public class SchemaPropertyProcessorManager
{
    private List<SchemaPropertyProcessor> processors = new ArrayList<>();

    public SchemaPropertyProcessorManager() {

    }

    public SchemaPropertyProcessorManager(List<SchemaPropertyProcessor> processors) {
        this.processors = processors;
    }

    public void registerSchemaPropertyProcessor(SchemaPropertyProcessor processor) {
        getProcessors().add(processor);
    }

    public void process(JsonSchema schema, BeanProperty prop) {
        getProcessors().forEach(processor -> processor.process(schema, prop));
    }

    public SchemaPropertyProcessorManager createCopy() {
        return new SchemaPropertyProcessorManager(processors);
    }

    public List<SchemaPropertyProcessor> getProcessors() {
        return processors;
    }

    public void setProcessors(List<SchemaPropertyProcessor> processors) {
        this.processors = processors;
    }
}
