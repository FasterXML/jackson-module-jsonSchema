package com.fasterxml.jackson.module.jsonSchema.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.module.jsonSchema.factories.WrapperFactory.JsonSchemaVersion;

/**
 * This type represents an JSON reference to a {@link com.fasterxml.jackson.module.jsonSchema.JsonSchema}.
 * @author adb
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, include = JsonTypeInfo.As.PROPERTY, property = "type")
public class ReferenceSchema extends SimpleTypeSchema
{
    @JsonProperty
    protected String $ref;

    protected ReferenceSchema() {
        //jackson deserialization only
        super();
    }

    public ReferenceSchema(JsonSchemaVersion version, String ref) {
        this(version, ref, null);
    }
    
    public ReferenceSchema(JsonSchemaVersion version, String ref, ObjectSchema parent) {
        super(version);
        this.$ref = ref;
        this.parent = parent;
    }

    @Override
    public JsonFormatTypes getType() {
        return JsonFormatTypes.OBJECT;
    }
    
    @Override
    public String get$ref() {
        return $ref;
    }

    @Override
    public void set$ref(String $ref) {
        this.$ref = $ref;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof ReferenceSchema)) return false;
        return _equals((ReferenceSchema) obj);
    }

    protected boolean _equals(ReferenceSchema that)
    {
        return equals($ref, that.$ref)
                && super._equals(that);
    }
}
