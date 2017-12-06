package com.fasterxml.jackson.module.jsonSchema.factories;

import java.util.Arrays;
import java.util.Optional;

import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;

/**
 * Exists to supply {@link SchemaFactoryWrapper} or its subclasses
 * to nested schema factories.
 * @author jphelan
 */
public class WrapperFactory
{
    public enum JsonSchemaVersion {
        DRAFT_V3("http://json-schema.org/draft-03/schema#"), DRAFT_V4("http://json-schema.org/draft-04/schema#");
        private String schemaString;

        JsonSchemaVersion(String schemaString) {
            this.schemaString = schemaString;
        }

        public static Optional<JsonSchemaVersion> fromSchemaString(String schemaString) {
            if (schemaString == null) {
                return Optional.empty();
            }
            return Arrays.stream(JsonSchemaVersion.values()).filter(jsv -> schemaString.equals(jsv.schemaString)).findFirst();
        }

        public String getSchemaString() {
            return schemaString;
        }
    };

    private JsonSchemaVersion version = JsonSchemaVersion.DRAFT_V4;

    public WrapperFactory(JsonSchemaVersion version) {
        this.version = version;
    }

    public SchemaFactoryWrapper getWrapper(SerializerProvider provider) {
        return new SchemaFactoryWrapper(provider, version);
    }

    public SchemaFactoryWrapper getWrapper(SerializerProvider provider, VisitorContext rvc) {
        SchemaFactoryWrapper wrapper = new SchemaFactoryWrapper(provider, version);
        wrapper.setVisitorContext(rvc);
        return wrapper;
    }

    public SchemaFactoryWrapper getWrapper(SerializerProvider provider, VisitorContext rvc, ObjectSchema parent, Class<?> type) {
        SchemaFactoryWrapper wrapper = new SchemaFactoryWrapper(provider, version);
        wrapper.setVisitorContext(rvc);
        wrapper.setParent(parent);
        wrapper.setType(type);
        return wrapper;
    }

    public JsonSchemaVersion getVersion() {
        return version;
    }
}