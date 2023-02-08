package com.fasterxml.jackson.module.jsonSchema;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import com.fasterxml.jackson.module.jsonSchema.types.*;

/**
 * Type id resolver needed to support polymorphic (de)serialization of all kinds of
 * {@link JsonSchema} instances.
 * Note that to support custom types, you will need to sub-class this resolver
 * and override at least {@link #idFromValue(Object)}, {@link #idFromValueAndType(Object, Class)} and
 * {@link #typeFromId} methods; as well as associate this resolver using
 * {@link com.fasterxml.jackson.annotation.JsonTypeInfo} annotation on
 * all custom {@link JsonSchema} implementation classes.
 */
public class JsonSchemaIdResolver extends TypeIdResolverBase
{
     public JsonSchemaIdResolver() { }

     @Override
     public String idFromValue(Object value) {
         if (value instanceof JsonSchema) {
             return ((JsonSchema)value).getType().value();
         }
         return null;
     }

     @Override
     public String idFromValueAndType(Object value, Class<?> suggestedType) {
         return idFromValue(value);
     }

     @Override
     public JavaType typeFromId(DatabindContext ctxt, String id)
     {
         JsonFormatTypes stdType = JsonFormatTypes.forValue(id);
         if (stdType != null) {
             switch (stdType) {
             case ARRAY:
                 return ctxt.constructType(ArraySchema.class);
             case BOOLEAN:
                 return ctxt.constructType(BooleanSchema.class);
             case INTEGER:
                 return ctxt.constructType(IntegerSchema.class);
             case NULL:
                 return ctxt.constructType(NullSchema.class);
             case NUMBER:
                 return ctxt.constructType(NumberSchema.class);
             case OBJECT:
                 return ctxt.constructType(ObjectSchema.class);
             case STRING:
                 return ctxt.constructType(StringSchema.class);
             case ANY:
             default:
                 return ctxt.constructType(AnySchema.class);
             }
         }
         // Not a standard type; should use a custom sub-type impl
         throw new IllegalArgumentException("Can not resolve JsonSchema 'type' id of \""+id
                 +"\", not recognized as one of standard values: "+Arrays.asList(JsonFormatTypes.values()));
     }

     @Override
     public Id getMechanism() {
         return Id.CUSTOM;
     }

     @Override
     public void init(JavaType baseType) { }

     @Override
     public String idFromBaseType() {
         return null;
     }
 }
