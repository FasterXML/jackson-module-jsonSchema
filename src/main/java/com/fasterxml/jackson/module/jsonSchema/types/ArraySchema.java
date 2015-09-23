package com.fasterxml.jackson.module.jsonSchema.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

import java.io.IOException;
import java.util.Map;

/*
 * This attribute defines the allowed items in an instance array, and
   MUST be a jsonSchema or an array of jsonSchemas.  The default value is an
   empty jsonSchema which allows any value for items in the instance array.
 */
public class ArraySchema extends ContainerTypeSchema
{
	/**
	 * see {@link AdditionalItems}
	 */
	@JsonProperty
	protected ArraySchema.AdditionalItems additionalItems;
	
	/**
	 * see {@link Items}
	 */
	@JsonProperty
	@JsonDeserialize(using = ItemsDeserializer.class)
	protected ArraySchema.Items items;
	
	/**This attribute defines the maximum number of values in an array*/
	@JsonProperty
	protected Integer maxItems;
	
	/**This attribute defines the minimum number of values in an array*/
	@JsonProperty
	protected Integer minItems;
	
	/**
	 * This attribute indicates that all items in an array instance MUST be
	   unique (contains no two identical values).
	
	   Two instance are consider equal if they are both of the same type
	   and:
	
	      are null; or are booleans/numbers/strings and have the same value; or
	
	      are arrays, contains the same number of items, and each item in
	      the array is equal to the corresponding item in the other array;
	      or
	
	      are objects, contains the same property names, and each property
	      in the object is equal to the corresponding property in the other
	      object.
	 */
	@JsonProperty
	protected Boolean uniqueItems;

	@Override
	public ArraySchema asArraySchema() { return this; }

	public ArraySchema.AdditionalItems getAdditionalItems() {
	    return additionalItems;
	}

	public ArraySchema.Items getItems() {
	    return items;
	}

	public Integer getMaxItems() {
	    return maxItems;
	}

	public Integer getMinItems() {
	    return minItems;
	}
	
	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.jsonSchema.types.JsonSchema#getType()
	 */
	@Override
	public JsonFormatTypes getType() {
	    return JsonFormatTypes.ARRAY;
	}

	public Boolean getUniqueItems() {
		return uniqueItems;
	}

	@Override
	public boolean isArraySchema() { return true; }

	public void setAdditionalItems(ArraySchema.AdditionalItems additionalItems) {
		this.additionalItems = additionalItems;
	}

	public void setItems(ArraySchema.Items items) {
		this.items = items;
	}

	public void setItemsSchema(JsonSchema jsonSchema) {
		items = new SingleItems(jsonSchema);
	}

	public void setMaxItems(Integer maxItems) {
		this.maxItems = maxItems;
	}

	public void setMinItems(Integer minItems) {
		this.minItems = minItems;
	}

	public void setUniqueItems(Boolean uniqueItems) {
		this.uniqueItems = uniqueItems;
	}

     @Override
     public boolean equals(Object obj)
     {
         if (obj == this) return true;
         if (obj == null) return false;
         if (!(obj instanceof ArraySchema)) return false;
         return _equals((ArraySchema) obj);
     }

     protected boolean _equals(ArraySchema that)
     {
         return equals(getAdditionalItems(), that.getAdditionalItems())
                 && equals(getItems(), that.getItems())
                 && equals(getMaxItems(), that.getMaxItems())
                 && equals(getMinItems(), that.getMinItems())
                 && equals(getUniqueItems(), that.getUniqueItems())
                 && super._equals(that);
     }
	
	/**
	 * This provides a definition for additional items in an array instance
   when tuple definitions of the items is provided.
	 */
	@JsonDeserialize(using = AdditionalItemsDeserializer.class)
	public static abstract class AdditionalItems {
		
		@JsonCreator
		public static Items jsonCreator(Map<String,Object> props) {
			// not implemented for jsonSchema
			return null;
			//KNOWN ISSUE: pending https://github.com/FasterXML/jackson-databind/issues/43
		}
	}
	
	/**
	 * When this attribute value is an array of jsonSchemas and the instance
	   value is an array, each position in the instance array MUST conform
	   to the jsonSchema in the corresponding position for this array.  This
	   called tuple typing.  When tuple typing is used, additional items are
	   allowed, disallowed, or constrained by the "additionalItems"
	 */
	public static class ArrayItems extends ArraySchema.Items {
		@JsonProperty
		private JsonSchema[] jsonSchemas;

		public ArrayItems(JsonSchema[] jsonSchemas) {
			this.jsonSchemas = jsonSchemas;
		}

		/* (non-Javadoc)
                 * @see com.fasterxml.jackson.databind.jsonSchema.types.ArraySchema.Items#asArrayItems()
                 */
		@Override
		public ArrayItems asArrayItems() { return this; }
		
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Items) {
				ArrayItems that = (ArrayItems) obj;
				return JsonSchema.equals(getJsonSchemas(), that.getJsonSchemas());
			} else {
				return false;
			}
		}

		public JsonSchema[] getJsonSchemas() {
		    return jsonSchemas;
		}
		
		/* (non-Javadoc)
		 * @see com.fasterxml.jackson.databind.jsonSchema.types.ArraySchema.Items#isArrayItems()
		 */
		@Override
		public boolean isArrayItems() { return true; }
	}

	public static class ItemsDeserializer extends JsonDeserializer<Items> {

		@Override
		public Items deserialize(JsonParser parser,
								 DeserializationContext context) throws IOException, JsonProcessingException {
			ObjectCodec mapper = parser.getCodec();
			JsonNode node = parser.readValueAs(JsonNode.class);

			if (node.isArray()) {
				JsonSchema[] schemas = mapper.treeToValue(node, JsonSchema[].class);

				return new ArrayItems(schemas);
			}
			else
				return new SingleItems(mapper.treeToValue(node, JsonSchema.class));
		}
	}
	
	/**
	 * This attribute defines the allowed items in an instance array, and
	   MUST be a jsonSchema or an array of jsonSchemas.  The default value is an
	   empty jsonSchema which allows any value for items in the instance array.
	 */
	public static abstract class Items {
		
		@JsonIgnore
		public boolean isSingleItems() { return false; }
		
		@JsonIgnore
		public boolean isArrayItems() { return false; }
		
		public SingleItems asSingleItems() { return null; }
		public ArrayItems asArrayItems() { return null; }
	}
	
	/**
	 *  This can be false
   		to indicate additional items in the array are not allowed
	 */
	public static class NoAdditionalItems extends AdditionalItems {
		
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			return obj instanceof NoAdditionalItems;
		}
		@JsonValue
		public Boolean value() { return false; }
	}
	
	/**
	 * or it can
   		be a jsonSchema that defines the jsonSchema of the additional items.
	 */
	public static class SchemaAdditionalItems extends AdditionalItems {
		
		@JsonIgnore
		private JsonSchema jsonSchema;
		
		public SchemaAdditionalItems(JsonSchema schema) {
			jsonSchema = schema;
		}

		@Override
		public boolean equals(Object obj) {
			return obj instanceof SchemaAdditionalItems &&
					JsonSchema.equals(getJsonSchema(), ((SchemaAdditionalItems)obj).getJsonSchema());
		}
		
		@JsonValue
		public JsonSchema getJsonSchema() {
			return jsonSchema;
		}
	}
	
	/**
	 * When this attribute value is a jsonSchema and the instance value is an
	   array, then all the items in the array MUST be valid according to the
	   jsonSchema.
	 */
	public static class SingleItems extends ArraySchema.Items {
		@JsonIgnore
		private JsonSchema jsonSchema;
			
		public SingleItems(JsonSchema jsonSchema) {
			this.jsonSchema = jsonSchema;
		}

		@Override
		public boolean equals(Object obj) {
			return obj instanceof SingleItems &&
					JsonSchema.equals(getSchema(), ((SingleItems)obj).getSchema());
		}

		@JsonValue
		public JsonSchema getSchema() {
		    return jsonSchema;
		}

		public void setSchema(JsonSchema jsonSchema) {
		    this.jsonSchema = jsonSchema;
		}

		@Override
		public boolean isSingleItems() { return true; }
		
		/* (non-Javadoc)
		 * @see com.fasterxml.jackson.databind.jsonSchema.types.ArraySchema.Items#asSingleItems()
		 */
		@Override
		public SingleItems asSingleItems() { return this; }
	}
}
