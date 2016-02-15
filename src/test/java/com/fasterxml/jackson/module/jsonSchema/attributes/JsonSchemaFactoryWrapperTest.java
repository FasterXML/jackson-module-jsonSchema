package com.fasterxml.jackson.module.jsonSchema.attributes;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.attributes.JsonSchemaAttributes;
import com.fasterxml.jackson.module.jsonSchema.attributes.JsonSchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.types.ArraySchema;
import com.fasterxml.jackson.module.jsonSchema.types.NumberSchema;
import com.fasterxml.jackson.module.jsonSchema.types.StringSchema;

/**
 * @author cponomaryov
 */
public class JsonSchemaFactoryWrapperTest extends SchemaTestBase {

    public static class ValidationBean {

        /*
        /**********************************************************
        /* Array fields
        /**********************************************************
        */

        private List<String> listWithoutConstraints;

        @JsonSchemaAttributes(minItems = 1)
        private List<String> listWithMinSize;

        @JsonSchemaAttributes(maxItems = 2)
        private List<String> listWithMaxSize;

        @JsonSchemaAttributes(minItems = 3, maxItems = 4)
        private List<String> listWithMinAndMaxSize;

        /*
        /**********************************************************
        /* Number fields
        /**********************************************************
        */

        private int numberWithoutConstraints;
        
        @JsonSchemaAttributes(minimum = 5)
        private int numberWithMin;

        @JsonSchemaAttributes(minimum = 5.5)
        private int numberWithDecimalMin;

        @JsonSchemaAttributes(maximum = 6)
        private int numberWithMax;

        @JsonSchemaAttributes(maximum = 6.5)
        private int numberWithDecimalMax;

        @JsonSchemaAttributes(minimum = 7, maximum = 8)
        private int numberWithMinAndMax;

        @JsonSchemaAttributes(minimum = 9, maximum = 9.5)
        private int numberWithMinAndDecimalMax;

        @JsonSchemaAttributes(minimum = 10.5, maximum = 11)
        private int numberWithDecimalMinAndMax;

        @JsonSchemaAttributes(minimum = 11.5, maximum = 12.5)
        private int numberWithDecimalMinAndDecimalMax;

        /*
        /**********************************************************
        /* String fields
        /**********************************************************
        */

        private String stringWithoutConstraints;

        @JsonSchemaAttributes(minLength = 13)
        private String stringWithMinSize;

        @JsonSchemaAttributes(maxLength = 14)
        private String stringWithMaxSize;

        @JsonSchemaAttributes(minLength = 15, maxLength = 16)
        private String stringWithMinAndMaxSize;

        public List<String> getListWithoutConstraints() {
            return listWithoutConstraints;
        }

        public void setListWithoutConstraints(List<String> listWithoutConstraints) {
            this.listWithoutConstraints = listWithoutConstraints;
        }

        public List<String> getListWithMinSize() {
            return listWithMinSize;
        }

        public void setListWithMinSize(List<String> listWithMinSize) {
            this.listWithMinSize = listWithMinSize;
        }

        public List<String> getListWithMaxSize() {
            return listWithMaxSize;
        }

        public void setListWithMaxSize(List<String> listWithMaxSize) {
            this.listWithMaxSize = listWithMaxSize;
        }

        public List<String> getListWithMinAndMaxSize() {
            return listWithMinAndMaxSize;
        }

        public void setListWithMinAndMaxSize(List<String> listWithMinAndMaxSize) {
            this.listWithMinAndMaxSize = listWithMinAndMaxSize;
        }

        public int getNumberWithoutConstraints() {
            return numberWithoutConstraints;
        }

        public void setNumberWithoutConstraints(int numberWithoutConstraints) {
            this.numberWithoutConstraints = numberWithoutConstraints;
        }

        public int getNumberWithMin() {
            return numberWithMin;
        }

        public void setNumberWithMin(int numberWithMin) {
            this.numberWithMin = numberWithMin;
        }

        public int getNumberWithDecimalMin() {
            return numberWithDecimalMin;
        }

        public void setNumberWithDecimalMin(int numberWithDecimalMin) {
            this.numberWithDecimalMin = numberWithDecimalMin;
        }

        public int getNumberWithMax() {
            return numberWithMax;
        }

        public void setNumberWithMax(int numberWithMax) {
            this.numberWithMax = numberWithMax;
        }

        public int getNumberWithDecimalMax() {
            return numberWithDecimalMax;
        }

        public void setNumberWithDecimalMax(int numberWithDecimalMax) {
            this.numberWithDecimalMax = numberWithDecimalMax;
        }

        public int getNumberWithMinAndMax() {
            return numberWithMinAndMax;
        }

        public void setNumberWithMinAndMax(int numberWithMinAndMax) {
            this.numberWithMinAndMax = numberWithMinAndMax;
        }

        public int getNumberWithMinAndDecimalMax() {
            return numberWithMinAndDecimalMax;
        }

        public void setNumberWithMinAndDecimalMax(int numberWithMinAndDecimalMax) {
            this.numberWithMinAndDecimalMax = numberWithMinAndDecimalMax;
        }

        public int getNumberWithDecimalMinAndMax() {
            return numberWithDecimalMinAndMax;
        }

        public void setNumberWithDecimalMinAndMax(int numberWithDecimalMinAndMax) {
            this.numberWithDecimalMinAndMax = numberWithDecimalMinAndMax;
        }

        public int getNumberWithDecimalMinAndDecimalMax() {
            return numberWithDecimalMinAndDecimalMax;
        }

        public void setNumberWithDecimalMinAndDecimalMax(int numberWithDecimalMinAndDecimalMax) {
            this.numberWithDecimalMinAndDecimalMax = numberWithDecimalMinAndDecimalMax;
        }

        public String getStringWithoutConstraints() {
            return stringWithoutConstraints;
        }

        public void setStringWithoutConstraints(String stringWithoutConstraints) {
            this.stringWithoutConstraints = stringWithoutConstraints;
        }

        public String getStringWithMinSize() {
            return stringWithMinSize;
        }

        public void setStringWithMinSize(String stringWithMinSize) {
            this.stringWithMinSize = stringWithMinSize;
        }

        public String getStringWithMaxSize() {
            return stringWithMaxSize;
        }

        public void setStringWithMaxSize(String stringWithMaxSize) {
            this.stringWithMaxSize = stringWithMaxSize;
        }

        public String getStringWithMinAndMaxSize() {
            return stringWithMinAndMaxSize;
        }

        public void setStringWithMinAndMaxSize(String stringWithMinAndMaxSize) {
            this.stringWithMinAndMaxSize = stringWithMinAndMaxSize;
        }
    }

    /*
    /**********************************************************
    /* Unit tests, success
    /**********************************************************
     */

    private final ObjectMapper MAPPER = new ObjectMapper();

    private Object[][] listTestData() {
        return new Object[][] {{"listWithoutConstraints", null, null},
                {"listWithMinSize", 1, null},
                {"listWithMaxSize", null, 2},
                {"listWithMinAndMaxSize", 3, 4}};
    }

    private Object[][] numberTestData() {
        return new Object[][] {{"numberWithoutConstraints", null, null},
                {"numberWithMin", 5, null},
                {"numberWithDecimalMin", 5.5, null},
                {"numberWithMax", null, 6d},
                {"numberWithDecimalMax", null, 6.5},
                {"numberWithMinAndMax", 7d, 8d},
                {"numberWithMinAndDecimalMax", 9d, 9.5},
                {"numberWithDecimalMinAndMax", 10.5, 11d},
                {"numberWithDecimalMinAndDecimalMax", 11.5, 12.5}};
    }

    private Object[][] stringTestData() {
        return new Object[][] {{"stringWithoutConstraints", null, null},
                {"stringWithMinSize", 13, null},
                {"stringWithMaxSize", null, 14},
                {"stringWithMinAndMaxSize", 15, 16}};
    }

    /**
     * Test set validation constraints
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    public void testAddingAttributeConstraints() throws Exception {
        JsonSchemaFactoryWrapper visitor = new JsonSchemaFactoryWrapper();
        ObjectMapper mapper = new ObjectMapper();

        mapper.acceptJsonFormatVisitor(ValidationBean.class, visitor);
        JsonSchema jsonSchema = visitor.finalSchema();

        assertNotNull("schema should not be null.", jsonSchema);
        assertTrue("schema should be an objectSchema.", jsonSchema.isObjectSchema());
        Map<String, JsonSchema> properties = jsonSchema.asObjectSchema().getProperties();
        assertNotNull(properties);
        for (Object[] testCase : listTestData()) {
            JsonSchema propertySchema = properties.get(testCase[0]);
            assertNotNull(propertySchema);
            assertTrue(propertySchema.isArraySchema());
            ArraySchema arraySchema = propertySchema.asArraySchema();
            assertEquals(testCase[1], arraySchema.getMinItems());
            assertEquals(testCase[2], arraySchema.getMaxItems());
        }
        for (Object[] testCase : numberTestData()) {
            JsonSchema propertySchema = properties.get(testCase[0]);
            assertNotNull(propertySchema);
            assertTrue(propertySchema.isNumberSchema());
            NumberSchema numberSchema = propertySchema.asNumberSchema();
            assertEquals(testCase[1] == null ? null : Double.valueOf("" + testCase[1]), (Object)numberSchema.getMinimum());
            assertEquals(testCase[2], (Object)numberSchema.getMaximum());
        }
        for (Object[] testCase : stringTestData()) {
            JsonSchema propertySchema = properties.get(testCase[0]);
            assertNotNull(propertySchema);
            assertTrue(propertySchema.isStringSchema());
            StringSchema stringSchema = propertySchema.asStringSchema();
            assertEquals(testCase[1], (Object)stringSchema.getMinLength());
            assertEquals(testCase[2], (Object)stringSchema.getMaxLength());
        }
    }

}
