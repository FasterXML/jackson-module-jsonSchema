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
public class AdditionalItemsDeserializer extends JsonDeserializer<ArraySchema.AdditionalItems>
{
	@Override
	public ArraySchema.AdditionalItems deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException
	{
	    TreeNode node = jp.readValueAsTree();
		
		if (node instanceof ObjectNode) {
		    // not clean, but has to do...
	          ObjectMapper mapper = (ObjectMapper) jp.getCodec();
			JsonSchema innerSchema = mapper.treeToValue(node, JsonSchema.class);
			return new ArraySchema.SchemaAdditionalItems(innerSchema);
		}
		if (node instanceof BooleanNode) {
			BooleanNode booleanNode = (BooleanNode) node;
			if (booleanNode.booleanValue()) {
				return null; // "additionalItems":true is the default
			}
			return new ArraySchema.NoAdditionalItems();
		}
		throw new JsonMappingException("additionalItems nodes can only be of "
		        + "type Boolean or Object; instead found something starting with token " + node.asToken());
	}
}
