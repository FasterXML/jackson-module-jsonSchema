package tools.jackson.module.jsonSchema.jakarta.factories;

import java.util.Set;

import tools.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import tools.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import tools.jackson.module.jsonSchema.jakarta.types.IntegerSchema;

public class IntegerVisitor extends JsonIntegerFormatVisitor.Base
    implements JsonSchemaProducer
{
    protected final IntegerSchema schema;
	
    public IntegerVisitor(IntegerSchema schema) {
        this.schema = schema;
    }

    /*
    /*********************************************************************
    /* JsonSchemaProducer
    /*********************************************************************
     */

    @Override
    public IntegerSchema getSchema() {
        return schema;
    }

    /*
    /*********************************************************************
    /* JsonIntegerFormatVisitor
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
