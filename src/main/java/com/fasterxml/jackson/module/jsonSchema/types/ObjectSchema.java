package com.fasterxml.jackson.module.jsonSchema.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import java.util.LinkedHashMap;

/**
 * This type represents a {@link JsonSchema} as an object type
 * @author jphelan
 */
public class ObjectSchema extends ContainerTypeSchema
{
	/**
	 * This attribute defines a jsonSchema for all properties that are not
	 * explicitly defined in an object type definition. If specified, the value
	 * MUST be a jsonSchema or a boolean. If false is provided, no additional
	 * properties are allowed beyond the properties defined in the jsonSchema. The
	 * default value is an empty jsonSchema which allows any value for additional
	 * properties.
	 */
	@JsonProperty
	private AdditionalProperties additionalProperties;

	/**
	 * This attribute is an object that defines the requirements of a property
	 * on an instance object. If an object instance has a property with the same
	 * name as a property in this attribute's object, then the instance must be
	 * valid against the attribute's property value
	 */
	@JsonProperty
	private List<Dependency> dependencies;

	/**
	 * 
	 This attribute is an object that defines the jsonSchema for a set of property
	 * names of an object instance. The name of each property of this
	 * attribute's object is a regular expression pattern in the ECMA 262/Perl 5
	 * format, while the value is a jsonSchema. If the pattern matches the name of a
	 * property on the instance object, the value of the instance's property
	 * MUST be valid against the pattern name's jsonSchema value.
	 */
	@JsonProperty
	private Map<String, JsonSchema> patternProperties;

	/**
	 * This attribute is an object with property definitions that define the
	 * valid values of instance object property values. When the instance value
	 * is an object, the property values of the instance object MUST conform to
	 * the property definitions in this object. In this object, each property
	 * definition's value MUST be a jsonSchema, and the property's name MUST be the
	 * name of the instance property that it defines. The instance property
	 * value MUST be valid according to the jsonSchema from the property definition.
	 * Properties are considered unordered, the order of the instance properties
	 * MAY be in any order.
	 */
	@JsonProperty
	private Map<String, JsonSchema> properties;

	public ObjectSchema()
	{
		dependencies = new ArrayList<Dependency>();
		patternProperties = new LinkedHashMap<String, JsonSchema>();
		properties = new LinkedHashMap<String, JsonSchema>();
	}

	public boolean addSchemaDependency(String depender, JsonSchema parentMustMatch) {
		return dependencies
				.add(new SchemaDependency(depender, parentMustMatch));
	}

	public boolean addSimpleDependency(String depender, String dependsOn) {
		return dependencies.add(new SimpleDependency(depender, dependsOn));
	}

     @Override
     public JsonFormatTypes getType() {
           return JsonFormatTypes.OBJECT;
     }

     @Override
     public boolean isObjectSchema() {
          return true;
     }

	@Override
	public ObjectSchema asObjectSchema() {
		return this;
	}

	public AdditionalProperties getAdditionalProperties() {
	    return additionalProperties;
	}

	public List<Dependency> getDependencies() {
	    return dependencies;
	}

	public Map<String, JsonSchema> getPatternProperties() {
	    return patternProperties;
	}

	public Map<String, JsonSchema> getProperties() {
	    return properties;
	}

	public void putOptionalProperty(BeanProperty property, JsonSchema jsonSchema) {
		jsonSchema.enrichWithBeanProperty(property);
		properties.put(property.getName(), jsonSchema);
	}
	
	public void putOptionalProperty(String name, JsonSchema jsonSchema) {
		properties.put(name, jsonSchema);
	}

	public JsonSchema putPatternProperty(String regex, JsonSchema value) {
		return patternProperties.put(regex, value);
	}
	
	public JsonSchema putProperty(BeanProperty property, JsonSchema value) {
		value.setRequired(true);
		value.enrichWithBeanProperty(property);
		return properties.put(property.getName(), value);		
	}

	public JsonSchema putProperty(String name, JsonSchema value) {
		value.setRequired(true);
		return properties.put(name, value);
	}

	public void rejectAdditionalProperties() {
		additionalProperties = NoAdditionalProperties.instance;
	}

	public void setAdditionalProperties(
	        AdditionalProperties additionalProperties) {
	    this.additionalProperties = additionalProperties;
	}

	public void setDependencies(List<Dependency> dependencies) {
	    this.dependencies = dependencies;
	}

	public void setPatternProperties(Map<String, JsonSchema> patternProperties) {
	    this.patternProperties = patternProperties;
	}

	public void setProperties(Map<String, JsonSchema> properties) {
	    this.properties = properties;
	}

     @Override
     public boolean equals(Object obj)
     {
         if (obj == this) return true;
         if (obj == null) return false;
         if (!(obj instanceof ObjectSchema)) return false;
         return _equals((ObjectSchema) obj);
     }

     protected boolean _equals(ObjectSchema that)
     {
         return equals(getAdditionalProperties(), that.getAdditionalProperties())
                 && equals(getDependencies(), that.getDependencies())
                 && equals(getPatternProperties(), that.getPatternProperties())
                 && equals(getProperties(), that.getProperties()) 
                 && super._equals(that);
     }
	
	@JsonDeserialize(using = AdditionalPropertiesDeserializer.class)
	public static abstract class AdditionalProperties {
		@JsonCreator
		public AdditionalProperties jsonCreator() {
			//KNOWN ISSUE: pending https://github.com/FasterXML/jackson-databind/issues/43
			return null;
		}
	}

	public static abstract class Dependency {
		@JsonCreator
		public Dependency jsonCreator() {
			//KNOWN ISSUE: pending https://github.com/FasterXML/jackson-databind/issues/43
			return null;
		}
	}

	public static class NoAdditionalProperties extends AdditionalProperties {
		public final Boolean schema = false;

		protected NoAdditionalProperties() {
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			return obj instanceof NoAdditionalProperties;
		}

		@JsonValue
		public Boolean value() {
			return schema;
		}

		public static final NoAdditionalProperties instance = new NoAdditionalProperties();
	}

        
	public static class SchemaAdditionalProperties extends AdditionalProperties {

		@JsonProperty
		private JsonSchema jsonSchema;

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			return obj instanceof SchemaAdditionalProperties &&
					JsonSchema.equals(getJsonSchema(), ((SchemaAdditionalProperties)obj).getJsonSchema());
		}

		@JsonValue
		public JsonSchema getJsonSchema() {
		    return jsonSchema;
		}
		
		public SchemaAdditionalProperties(JsonSchema jsonSchema) {
		    this.jsonSchema = jsonSchema;
		}
	}

	/**
	 * JsonSchema Dependency If the dependency value is a jsonSchema, then the instance
	 * object MUST be valid against the jsonSchema.
	 */
	public static class SchemaDependency extends Dependency {

		@JsonProperty(required = true)
		private String depender;

		@JsonProperty(required = true)
		private JsonSchema parentMustMatch;

		public SchemaDependency(String depender, JsonSchema parentMustMatch) {
			this.depender = depender;
			this.parentMustMatch = parentMustMatch;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof SchemaDependency) {
				SchemaDependency that = (SchemaDependency) obj;
				return JsonSchema.equals(getDepender(), that.getDepender()) &&
					JsonSchema.equals(getParentMustMatch(), that.getParentMustMatch());
			} else {
				return false;
			}
		}

		public String getDepender() {
		    return depender;
		}

		public JsonSchema getParentMustMatch() {
		    return parentMustMatch;
		}
	}

	/**
	 * Simple Dependency If the dependency value is a string, then the instance
	 * object MUST have a property with the same name as the dependency value.
	 * If the dependency value is an array of strings, then the instance object
	 * MUST have a property with the same name as each string in the dependency
	 * value's array.
	 */
	public static class SimpleDependency extends Dependency {

		@JsonProperty(required = true)
		private String depender;

		@JsonProperty(required = true)
		private String dependsOn;

		public SimpleDependency(String depender, String dependsOn) {
			this.depender = depender;
			this.dependsOn = dependsOn;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof SchemaDependency) {
				SimpleDependency that = (SimpleDependency) obj;
				return JsonSchema.equals(getDepender(), that.getDepender()) &&
					JsonSchema.equals(getDependsOn(), that.getDependsOn());
			} else {
				return false;
			}
		}

		public String getDepender() {
		    return depender;
		}

		public String getDependsOn() {
		    return dependsOn;
		}
	}

}
