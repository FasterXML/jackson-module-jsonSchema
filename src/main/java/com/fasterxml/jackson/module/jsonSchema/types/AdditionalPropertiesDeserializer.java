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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import java.io.IOException;

/**
 *
 * @author Ignacio del Valle Alles
 */
public class AdditionalPropertiesDeserializer extends JsonDeserializer<ObjectSchema.AdditionalProperties> {

    @Override
    public ObjectSchema.AdditionalProperties deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        TreeNode node = mapper.readTree(jp);
        String nodeStr = mapper.writeValueAsString(node);
        if (node instanceof ObjectNode) {
            JsonSchema innerSchema = mapper.readValue(nodeStr, JsonSchema.class);
            return new ObjectSchema.SchemaAdditionalProperties(innerSchema);
        } else if (node instanceof BooleanNode) {
            BooleanNode booleanNode = (BooleanNode) node;
            if (booleanNode.booleanValue()) {
                return null; // "additionalProperties":true is the default
            } else {
                return ObjectSchema.NoAdditionalProperties.instance;
            }
        } else {
            throw new JsonMappingException("additionalProperties nodes can only be of "
                    + "type boolean or object: " + nodeStr);
        }
    }
}
