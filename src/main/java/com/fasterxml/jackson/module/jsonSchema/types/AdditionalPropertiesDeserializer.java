/*
 * Copyright 2013 FasterXML.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fasterxml.jackson.module.jsonSchema.types;

import java.io.IOException;

import com.fasterxml.jackson.core.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

/**
 * @author Ignacio del Valle Alles
 */
public class AdditionalPropertiesDeserializer
    extends JsonDeserializer<ObjectSchema.AdditionalProperties>
{
    @Override
    public ObjectSchema.AdditionalProperties deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
    {
        if (p.hasCurrentToken()) {
            switch (p.getCurrentTokenId()) {
            case JsonTokenId.ID_TRUE:
                return null; // "additionalProperties":true is the default
            case JsonTokenId.ID_FALSE:
                return ObjectSchema.NoAdditionalProperties.instance;
            case JsonTokenId.ID_START_OBJECT:
            case JsonTokenId.ID_FIELD_NAME:
            case JsonTokenId.ID_END_OBJECT:
                // 29-Dec-2015, tatu: may need/want to use property value reader in future but for now:
                JsonSchema innerSchema = ctxt.readValue(p, JsonSchema.class);
                return new ObjectSchema.SchemaAdditionalProperties(innerSchema);
            }
        }
        throw new JsonMappingException("additionalProperties nodes can only be of "
                + "type boolean or object, got token of type: "+p.getCurrentToken());
    }
}
