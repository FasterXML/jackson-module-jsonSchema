package com.fasterxml.jackson.module.jsonSchema.customProperties;

import java.util.Arrays;

import com.fasterxml.jackson.module.jsonSchema.factories.WrapperFactory.JsonSchemaVersion;
import com.fasterxml.jackson.module.jsonSchema.property.SchemaPropertyProcessorTitle;
import com.fasterxml.jackson.module.jsonSchema.property.manager.SchemaPropertyProcessorManager;

/**
 * Adds a title to any object annotated with JsonSchemaTitle, either root level or nested. 
 * 
 * @author amerritt
 */
public class TitleSchemaPropertyProcessorManagerFactoryWrapper extends SchemaPropertyProcessorManagerFactoryWrapper
{
    public TitleSchemaPropertyProcessorManagerFactoryWrapper() {
        this(JsonSchemaVersion.DRAFT_V3);
    }

    public TitleSchemaPropertyProcessorManagerFactoryWrapper(JsonSchemaVersion version) {
        super(new SchemaPropertyProcessorManager(Arrays.asList(new SchemaPropertyProcessorTitle())), version);
    }
}
