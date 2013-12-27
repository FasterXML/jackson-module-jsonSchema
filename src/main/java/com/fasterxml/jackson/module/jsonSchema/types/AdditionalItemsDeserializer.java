package com.fasterxml.jackson.module.jsonSchema.types;

import java.io.IOException;

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
import com.fasterxml.jackson.module.jsonSchema.types.AdditionalPropertiesDeserializer;
import com.fasterxml.jackson.module.jsonSchema.types.ArraySchema;

/**
 * @author Yoann Rodière (adapted from {@link AdditionalPropertiesDeserializer}, by Ignacio del Valle Alles)
 */
public class AdditionalItemsDeserializer extends JsonDeserializer<ArraySchema.AdditionalItems> {

	@Override
	public ArraySchema.AdditionalItems deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectMapper mapper = (ObjectMapper) jp.getCodec();
		TreeNode node = mapper.readTree(jp);
		String nodeStr = mapper.writeValueAsString(node);
		if (node instanceof ObjectNode) {
			JsonSchema innerSchema = mapper.readValue(nodeStr, JsonSchema.class);
			return new ArraySchema.SchemaAdditionalItems(innerSchema);
		} else if (node instanceof BooleanNode) {
			BooleanNode booleanNode = (BooleanNode) node;
			if (booleanNode.booleanValue()) {
				return null; // "additionalItems":true is the default
			} else {
				return new ArraySchema.NoAdditionalItems();
			}
		} else {
			throw new JsonMappingException("additionalItems nodes can only be of "
					+ "type boolean or object: " + nodeStr);
		}
	}
}
