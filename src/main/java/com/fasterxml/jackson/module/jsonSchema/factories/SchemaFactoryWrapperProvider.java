package com.fasterxml.jackson.module.jsonSchema.factories;

import com.fasterxml.jackson.databind.SerializerProvider;

public class SchemaFactoryWrapperProvider {
      public SchemaFactoryWrapper schemaFactoryWrapper(SerializerProvider provider) { 
          SchemaFactoryWrapper wrapper = new SchemaFactoryWrapper();
          wrapper.setProvider(provider);
          return wrapper;
      }
 }