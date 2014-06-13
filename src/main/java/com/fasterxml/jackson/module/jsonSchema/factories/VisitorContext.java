package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.databind.JavaType;

import java.util.HashSet;

/**
 * Created by adb on 6/9/14.
 */
public class VisitorContext {

    private static HashSet<JavaType> seenSchemas = new HashSet<JavaType>();

    static public String addSeenSchemaUri(JavaType aSeenSchema)
    {
        if (aSeenSchema != null && !aSeenSchema.isPrimitive()) {
            seenSchemas.add(aSeenSchema);
            return javaTypeToUrn(aSeenSchema);
        }
        return null;
    }

    static public String getSeenSchemaUri(JavaType aSeenSchema)
    {
        return (seenSchemas.contains(aSeenSchema)) ? javaTypeToUrn(aSeenSchema) : null;
    }

    static public String javaTypeToUrn(JavaType jt)
    {
        return "urn:jsonschema:" + jt.toCanonical().replace('.', ':').replace('$', ':');
    }
}
