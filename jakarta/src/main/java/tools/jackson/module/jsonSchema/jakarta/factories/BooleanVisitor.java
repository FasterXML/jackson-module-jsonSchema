package tools.jackson.module.jsonSchema.jakarta.factories;

import tools.jackson.module.jsonSchema.jakarta.types.BooleanSchema;
import java.util.Set;

import tools.jackson.databind.jsonFormatVisitors.JsonBooleanFormatVisitor;
import tools.jackson.databind.jsonFormatVisitors.JsonValueFormat;

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
