package tools.jackson.module.jsonSchema.jakarta.factories;

import tools.jackson.module.jsonSchema.jakarta.JsonSchema;
import tools.jackson.databind.BeanProperty;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import tools.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;

/**
 * @author cponomaryov
 */
public class ObjectVisitorDecorator implements JsonObjectFormatVisitor, JsonSchemaProducer {

    protected ObjectVisitor objectVisitor;

    public ObjectVisitorDecorator(ObjectVisitor objectVisitor) {
        this.objectVisitor = objectVisitor;
    }

    @Override
    public JsonSchema getSchema() {
        return objectVisitor.getSchema();
    }

    @Override
    public SerializationContext getContext() {
        return objectVisitor.getContext();
    }

    @Override
    @Deprecated // since 2.5
    public void setContext(SerializationContext serializerProvider) {
        if (objectVisitor.getContext() == null) {
            objectVisitor.setContext(serializerProvider);
        }
    }

    @Override
    public void optionalProperty(BeanProperty writer) {
        objectVisitor.optionalProperty(writer);
    }

    @Override
    public void optionalProperty(String name, JsonFormatVisitable handler, JavaType propertyTypeHint) {
        objectVisitor.optionalProperty(name, handler, propertyTypeHint);
    }

    @Override
    public void property(BeanProperty writer) {
        objectVisitor.property(writer);
    }

    @Override
    public void property(String name, JsonFormatVisitable handler, JavaType propertyTypeHint) {
        objectVisitor.property(name, handler, propertyTypeHint);
    }

}
