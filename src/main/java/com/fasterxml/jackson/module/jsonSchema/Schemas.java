package com.fasterxml.jackson.module.jsonSchema;

import com.fasterxml.jackson.databind.JavaType;

import java.util.HashSet;

/**
 * Created by adb on 6/9/14.
 */
public class Schemas {

    private static final ThreadLocal<HashSet<JavaType>> seenSchemas = new ThreadLocal<HashSet<JavaType>>() {
        @Override
        protected HashSet<JavaType> initialValue() {
            return new HashSet<JavaType>();
        }
    };

    static public String addSeenSchemaUri(JavaType aSeenSchema)
    {
        if (aSeenSchema != null && !aSeenSchema.isPrimitive()) {
            seenSchemas.get().add(aSeenSchema);
            return "urn:" + aSeenSchema.toCanonical().replace('.', ':');
        }
        return null;
    }

    static public String getSeenSchemaUri(JavaType aSeenSchema)
    {
        return (seenSchemas.get().contains(aSeenSchema)) ? "urn:" + aSeenSchema.toCanonical().replace('.', ':') : null;
    }
}
