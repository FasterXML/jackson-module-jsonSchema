package tools.jackson.module.jsonSchema.types;

import tools.jackson.module.jsonSchema.JsonSchema;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonTokenId;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

/**
 * @author Yoann Rodière (adapted from {@link AdditionalPropertiesDeserializer}, by Ignacio del Valle Alles)
 */
public class AdditionalItemsDeserializer extends ValueDeserializer<ArraySchema.AdditionalItems>
{
	@Override
	public ArraySchema.AdditionalItems deserialize(JsonParser p, DeserializationContext ctxt)
	{
	    if (p.hasCurrentToken()) {
	        switch (p.currentTokenId()) {
	        case JsonTokenId.ID_TRUE:
	            return null; // "additionalItems":true is the default
	        case JsonTokenId.ID_FALSE:
	            return new ArraySchema.NoAdditionalItems();
	        case JsonTokenId.ID_START_OBJECT:
	        case JsonTokenId.ID_PROPERTY_NAME:
	        case JsonTokenId.ID_END_OBJECT:
	            // 29-Dec-2015, tatu: may need/want to use property value reader in future but for now:
	            JsonSchema innerSchema = ctxt.readValue(p, JsonSchema.class);
	            return new ArraySchema.SchemaAdditionalItems(innerSchema);
	        }
	    }
	    return ctxt.reportInputMismatch(this,
"additionalItems nodes can only be of type boolean or object, got token of type: %s", p.currentToken());
	}
}
