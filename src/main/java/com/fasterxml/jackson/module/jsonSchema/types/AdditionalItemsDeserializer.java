package com.fasterxml.jackson.module.jsonSchema.types;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

import com.fasterxml.jackson.module.jsonSchema.types.AdditionalPropertiesDeserializer;
import com.fasterxml.jackson.module.jsonSchema.types.ArraySchema;

/**
 * @author Yoann Rodière (adapted from {@link AdditionalPropertiesDeserializer}, by Ignacio del Valle Alles)
 */
public class AdditionalItemsDeserializer extends JsonDeserializer<ArraySchema.AdditionalItems>
{
	@Override
	public ArraySchema.AdditionalItems deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
	{
	    if (p.hasCurrentToken()) {
	        switch (p.getCurrentTokenId()) {
	        case JsonTokenId.ID_TRUE:
	            return null; // "additionalItems":true is the default
	        case JsonTokenId.ID_FALSE:
	            return new ArraySchema.NoAdditionalItems();
	        case JsonTokenId.ID_START_OBJECT:
	        case JsonTokenId.ID_FIELD_NAME:
	        case JsonTokenId.ID_END_OBJECT:
	            // 29-Dec-2015, tatu: may need/want to use property value reader in future but for now:
	            JsonSchema innerSchema = ctxt.readValue(p, JsonSchema.class);
	            return new ArraySchema.SchemaAdditionalItems(innerSchema);
	        }
	    }
	    throw new JsonMappingException("additionalItems nodes can only be of "
	            + "type boolean or object, got token of type: "+p.getCurrentToken());
	}
}
