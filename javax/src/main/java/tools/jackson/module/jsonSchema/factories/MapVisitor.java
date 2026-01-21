package tools.jackson.module.jsonSchema.factories;

import tools.jackson.module.jsonSchema.JsonSchema;
import tools.jackson.module.jsonSchema.types.ObjectSchema;
import tools.jackson.module.jsonSchema.types.ReferenceSchema;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import tools.jackson.databind.jsonFormatVisitors.JsonMapFormatVisitor;

/**
 * While JSON Schema does not have notion of "Map" type (unlimited property
 * names), Jackson has, so the distinction is exposed. We will need
 * to handle it here, produce JSON Schema Object type.
 */
public class MapVisitor extends JsonMapFormatVisitor.Base
    implements JsonSchemaProducer, Visitor
{
    protected final ObjectSchema schema;

    private WrapperFactory wrapperFactory;

    private VisitorContext visitorContext;

    public MapVisitor(SerializationContext context, ObjectSchema schema) {
        this(context, schema, new WrapperFactory());
    }
    
    public MapVisitor(SerializationContext context, ObjectSchema schema,
            WrapperFactory wrapperFactory)
    {
        super(context);
        this.schema = schema;
        this.wrapperFactory = wrapperFactory;
    }

    /*
    /*********************************************************************
    /* JsonSchemaProducer
    /*********************************************************************
     */

    @Override
    public ObjectSchema getSchema() {
        return schema;
    }
    
    /*
    /*********************************************************************
    /* JsonMapFormatVisitor
    /*********************************************************************
     */

    @Override
    public void keyFormat(JsonFormatVisitable handler, JavaType keyType) {
        // JSON Schema only allows String types so let's not bother too much
    }

    @Override
    public void valueFormat(JsonFormatVisitable handler, JavaType valueType) {

        // ISSUE #24: https://github.com/FasterXML/jackson-module-jsonSchema/issues/24
        
        JsonSchema valueSchema = propertySchema(handler, valueType);
        ObjectSchema.AdditionalProperties ap = new ObjectSchema.SchemaAdditionalProperties(valueSchema.asSimpleTypeSchema());
        this.schema.setAdditionalProperties(ap);
    }

    protected JsonSchema propertySchema(JsonFormatVisitable handler, JavaType propertyTypeHint) {

        // check if we've seen this sub-schema already and return a reference-schema if we have
        if (visitorContext != null) {
            String seenSchemaUri = visitorContext.getSeenSchemaUri(propertyTypeHint);
            if (seenSchemaUri != null) {
                return new ReferenceSchema(seenSchemaUri);
            }
        }

        SchemaFactoryWrapper visitor = wrapperFactory.getWrapper(getContext(), visitorContext);
        handler.acceptJsonFormatVisitor(visitor, propertyTypeHint);
        return visitor.finalSchema();
    }

    @Override
    public Visitor setVisitorContext(VisitorContext rvc) {
        visitorContext = rvc;
        return this;
    }
}
