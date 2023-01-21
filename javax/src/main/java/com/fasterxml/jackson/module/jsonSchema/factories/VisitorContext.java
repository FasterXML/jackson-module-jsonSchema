package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.databind.JavaType;

import java.util.HashSet;

public class VisitorContext
{
    private final HashSet<JavaType> seenSchemas = new HashSet<JavaType>();

    public String addSeenSchemaUri(JavaType aSeenSchema) {
        if (aSeenSchema != null && !aSeenSchema.isPrimitive()) {
            seenSchemas.add(aSeenSchema);
            return javaTypeToUrn(aSeenSchema);
        }
        return null;
    }

    public String getSeenSchemaUri(JavaType aSeenSchema) {
        return (seenSchemas.contains(aSeenSchema)) ? javaTypeToUrn(aSeenSchema) : null;
    }

    public String javaTypeToUrn(JavaType jt) {
        return "urn:jsonschema:" + jt.toCanonical().replace('.', ':').replace('$', ':');
    }
}
