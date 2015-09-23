package com.fasterxml.jackson.module.jsonSchema.types;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;

import java.io.IOException;

public class JsonValueFormatDeserializer extends JsonDeserializer<JsonValueFormat> {
    @Override
    public JsonValueFormat deserialize(JsonParser jsonParser,
                                       DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String string = jsonParser.getValueAsString();

        for (JsonValueFormat f : JsonValueFormat.values()) {
            if (f.toString().equals(string))
                return f;
        }

        throw new JsonMappingException("Expected JsonValueFormat but found " + string);
    }
}
