package tools.jackson.module.jsonSchema.factories;

import tools.jackson.databind.SerializationContext;

/**
 * Exists to supply {@link SchemaFactoryWrapper} or its subclasses
 * to nested schema factories.
 * @author jphelan
 */
public class WrapperFactory
{
    public SchemaFactoryWrapper getWrapper(SerializationContext context) {
        return new SchemaFactoryWrapper(context);
    }

    public SchemaFactoryWrapper getWrapper(SerializationContext context, VisitorContext rvc) {
        SchemaFactoryWrapper wrapper = new SchemaFactoryWrapper(context);
        wrapper.setVisitorContext(rvc);
        return wrapper;
    }
}