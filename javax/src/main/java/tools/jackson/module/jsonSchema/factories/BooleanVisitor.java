package tools.jackson.module.jsonSchema.factories;

import java.util.Set;

import tools.jackson.databind.jsonFormatVisitors.JsonBooleanFormatVisitor;
import tools.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import tools.jackson.module.jsonSchema.types.BooleanSchema;

public class BooleanVisitor extends JsonBooleanFormatVisitor.Base
    implements JsonSchemaProducer
{
    protected final BooleanSchema schema;
	
    public BooleanVisitor(BooleanSchema schema) {
        this.schema = schema;
    }

    /*
    /*********************************************************************
    /* JsonSchemaProducer
    /*********************************************************************
     */
    
    @Override
    public BooleanSchema getSchema() {
        return schema;
    }
    
    /*
    /*********************************************************************
    /* JsonBooleanFormatVisitor impl
    /*********************************************************************
     */
	
    @Override
    public void enumTypes(Set<String> enums) {
        schema.setEnums(enums);
    }

    @Override
    public void format(JsonValueFormat format) {
        schema.setFormat(format);
    }
}
