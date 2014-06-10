package com.fasterxml.jackson.module.jsonSchema.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

/**
 * Created by adb on 6/9/14.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class ReferenceSchema extends SimpleTypeSchema {

    public ReferenceSchema(String ref) {
        this.$ref = ref;
    }

    public String get$ref() {
        return $ref;
    }

    public void set$ref(String $ref) {
        this.$ref = $ref;
    }

    @JsonProperty
    private String $ref;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReferenceSchema that = (ReferenceSchema) o;

        if ($ref != null ? !$ref.equals(that.$ref) : that.$ref != null) return false;

        return true;
    }

    @Override
    @JsonIgnore
    public JsonFormatTypes getType() {
        return JsonFormatTypes.OBJECT;
    }
}
