package com.fasterxml.jackson.module.jsonSchema.types;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;

import java.io.IOException;

public class JsonValueFormatSerializer extends JsonSerializer<JsonValueFormat> {

    @Override
    public void serialize(JsonValueFormat jsonValueFormat,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeString(jsonValueFormat.toString());
    }
}
