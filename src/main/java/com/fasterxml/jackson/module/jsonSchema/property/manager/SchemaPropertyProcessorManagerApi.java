package com.fasterxml.jackson.module.jsonSchema.property.manager;

import java.util.List;

import com.fasterxml.jackson.module.jsonSchema.property.SchemaPropertyProcessor;

public interface SchemaPropertyProcessorManagerApi extends SchemaPropertyProcessor {

    void registerSchemaPropertyProcessor(SchemaPropertyProcessor processor);

    List<SchemaPropertyProcessor> getProcessors();

    void setProcessors(List<SchemaPropertyProcessor> processors);

    SchemaPropertyProcessorManagerApi createCopyForType(Class<?> type);

}