package tools.jackson.module.jsonSchema.factories;

import tools.jackson.databind.SerializationContext;

public abstract class StructuredTypeVisitor implements JsonSchemaProducer
{
    protected SerializationContext context;

    protected StructuredTypeVisitor(SerializationContext context)
    {
        this.context = context;
    }
    
    // // // Partial implementation for visitors; handling of SerializerProvider
    
    public SerializationContext getContext() {
        return context;
    }

    public void setProvider(SerializationContext c) {
        context = c;
    }

}

