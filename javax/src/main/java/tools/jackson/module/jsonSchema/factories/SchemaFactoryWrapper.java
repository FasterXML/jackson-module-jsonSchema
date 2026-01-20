package tools.jackson.module.jsonSchema.factories;

import tools.jackson.databind.JavaType;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import tools.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import tools.jackson.databind.jsonFormatVisitors.JsonBooleanFormatVisitor;
import tools.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import tools.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import tools.jackson.databind.jsonFormatVisitors.JsonMapFormatVisitor;
import tools.jackson.databind.jsonFormatVisitors.JsonNullFormatVisitor;
import tools.jackson.databind.jsonFormatVisitors.JsonNumberFormatVisitor;
import tools.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import tools.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import tools.jackson.module.jsonSchema.JsonSchema;
import tools.jackson.module.jsonSchema.types.AnySchema;
import tools.jackson.module.jsonSchema.types.ArraySchema;
import tools.jackson.module.jsonSchema.types.BooleanSchema;
import tools.jackson.module.jsonSchema.types.IntegerSchema;
import tools.jackson.module.jsonSchema.types.NullSchema;
import tools.jackson.module.jsonSchema.types.NumberSchema;
import tools.jackson.module.jsonSchema.types.ObjectSchema;
import tools.jackson.module.jsonSchema.types.StringSchema;

/**
 * @author jphelan
 * @author tsaloranta
 */
public class SchemaFactoryWrapper implements JsonFormatVisitorWrapper, Visitor
{
    protected FormatVisitorFactory visitorFactory;
    protected JsonSchemaFactory schemaProvider;
    protected SerializationContext context;
    protected JsonSchema schema;
    protected VisitorContext visitorContext;

    public SchemaFactoryWrapper() {
        this(null, new WrapperFactory());
    }

    public SchemaFactoryWrapper(SerializationContext c) {
        this(c, new WrapperFactory());
    }

    protected SchemaFactoryWrapper(WrapperFactory wrapperFactory) {
        this(null, wrapperFactory);
    }

    protected SchemaFactoryWrapper(SerializationContext c, WrapperFactory wrapperFactory) {
        context = c;
        schemaProvider = new JsonSchemaFactory();
        visitorFactory = new FormatVisitorFactory(wrapperFactory);
    }

    /*
    /*********************************************************************
    /* JsonFormatVisitorWrapper implementation
    /*********************************************************************
     */

    @Override
    public SerializationContext getContext() {
        return context;
    }

    @Override
    public void setContext(SerializationContext c) {
        context = c;
    }
    
    @Override
    public JsonAnyFormatVisitor expectAnyFormat(JavaType convertedType) {
        AnySchema s = schemaProvider.anySchema();
        this.schema = s;
        return visitorFactory.anyFormatVisitor(s);
    }
	
    @Override
    public JsonArrayFormatVisitor expectArrayFormat(JavaType convertedType) {
        ArraySchema s = schemaProvider.arraySchema();
        this.schema = s;
        return visitorFactory.arrayFormatVisitor(context, s, visitorContext);
    }

    @Override
    public JsonBooleanFormatVisitor expectBooleanFormat(JavaType convertedType) {
        BooleanSchema s = schemaProvider.booleanSchema();
        this.schema = s;
        return visitorFactory.booleanFormatVisitor(s);
    }

    @Override
    public JsonIntegerFormatVisitor expectIntegerFormat(JavaType convertedType) {
        IntegerSchema s = schemaProvider.integerSchema();
        this.schema = s;
        return visitorFactory.integerFormatVisitor(s);
    }

    @Override
    public JsonNullFormatVisitor expectNullFormat(JavaType convertedType) {
        NullSchema s = schemaProvider.nullSchema();
        schema = s;
        return visitorFactory.nullFormatVisitor(s);
    }

    @Override
    public JsonNumberFormatVisitor expectNumberFormat(JavaType convertedType) {
        NumberSchema s = schemaProvider.numberSchema();
        schema = s;
        return visitorFactory.numberFormatVisitor(s);
    }
	
    @Override
    public JsonObjectFormatVisitor expectObjectFormat(JavaType convertedType) {
        ObjectSchema s = schemaProvider.objectSchema();
        schema = s;

        // if we don't already have a recursive visitor context, create one
        if (visitorContext == null) {
            visitorContext = new VisitorContext();
        }

        // give each object schema a reference id and keep track of the ones we've seen
        String schemaUri = visitorContext.addSeenSchemaUri(convertedType);
        if (schemaUri != null) {
            s.setId(schemaUri);
        }

        return visitorFactory.objectFormatVisitor(context, s, visitorContext);
    }

    @Override
    public JsonStringFormatVisitor expectStringFormat(JavaType convertedType) {
        StringSchema s = schemaProvider.stringSchema();
        schema = s;
        return visitorFactory.stringFormatVisitor(s);
    }

    @Override
    public JsonMapFormatVisitor expectMapFormat(JavaType type)
    {
        /* 22-Nov-2012, tatu: Looks as if JSON Schema did not have
         *   concept of Map (distinct from Record or Object); so best
         *   we can do is to consider it a vague kind-a Object...
         */
        ObjectSchema s = schemaProvider.objectSchema();
        schema = s;
        return visitorFactory.mapFormatVisitor(context, s, visitorContext);
    }

    @Override
    public SchemaFactoryWrapper setVisitorContext(VisitorContext rvc) {
        visitorContext = rvc;
        return this;
    }

    /*
    /*********************************************************************
    /* API
    /*********************************************************************
     */
	
    public JsonSchema finalSchema() {
        return schema;
    }
}
