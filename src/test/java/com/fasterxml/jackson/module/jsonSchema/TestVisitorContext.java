package com.fasterxml.jackson.module.jsonSchema;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;

public class TestVisitorContext extends SchemaTestBase
{
    static class Foo {
        private String fooProp1;
        private int fooProp2;
        private Bar fooProp3;

        public String getFooProp1() {
            return fooProp1;
        }

        public void setFooProp1(String fooProp1) {
            this.fooProp1 = fooProp1;
        }

        public int getFooProp2() {
            return fooProp2;
        }

        public void setFooProp2(int fooProp2) {
            this.fooProp2 = fooProp2;
        }

        public Bar getFooProp3() {
            return fooProp3;
        }

        public void setFooProp3(Bar fooProp3) {
            this.fooProp3 = fooProp3;
        }
    }

    static class Bar {
        private String barProp1;
        private int barProp2;

        public String getBarProp1() {
            return barProp1;
        }

        public void setBarProp1(String barProp1) {
            this.barProp1 = barProp1;
        }

        public int getBarProp2() {
            return barProp2;
        }

        public void setBarProp2(int barProp2) {
            this.barProp2 = barProp2;
        }
    }

    static class Qwer {
        private String qwerProp1;
        private Bar qwerProp2;

        public String getQwerProp1() {
            return qwerProp1;
        }

        public void setQwerProp1(String qwerProp1) {
            this.qwerProp1 = qwerProp1;
        }

        public Bar getQwerProp2() {
            return qwerProp2;
        }

        public void setQwerProp2(Bar qwerProp2) {
            this.qwerProp2 = qwerProp2;
        }
    }

    // for [jsonSchema#47]
    public void testSchemaGeneration() throws Exception {
        String schemaStr = generateSchema(Foo.class);
        schemaStr = generateSchema(Qwer.class);
        // ok, not very robust but has to do:
        if (schemaStr.indexOf("$ref") >= 0) {
            fail("Should not have $ref for type Bar (as per issue #47): "+schemaStr);
        }
    }

    private final static ObjectMapper MAPPER = new ObjectMapper();
    
    private String generateSchema(Class<?> clazz) throws Exception
    {
        MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        MAPPER.acceptJsonFormatVisitor(MAPPER.constructType(clazz), visitor);
        return MAPPER.writeValueAsString(visitor.finalSchema());
    }

}
