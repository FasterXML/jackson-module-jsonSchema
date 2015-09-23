package com.fasterxml.jackson.module.jsonSchema;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.customProperties.ValidationSchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.types.ArraySchema;
import com.fasterxml.jackson.module.jsonSchema.types.NumberSchema;
import com.fasterxml.jackson.module.jsonSchema.types.StringSchema;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author cponomaryov
 */
public class ValidationSchemaFactoryWrapperTest extends SchemaTestBase {

    public static class ValidationBean {

        /*
        /**********************************************************
        /* Array fields
        /**********************************************************
        */

        private List<String> listWithoutConstraints;

        @Size(min = 1)
        private List<String> listWithMinSize;

        @Size(max = 2)
        private List<String> listWithMaxSize;

        @Size(min = 3, max = 4)
        private List<String> listWithMinAndMaxSize;

        /*
        /**********************************************************
        /* Number fields
        /**********************************************************
        */

        private int numberWithoutConstraints;

        @Min(5)
        private int numberWithMin;

        @DecimalMin("5.5")
        private int numberWithDecimalMin;

        @Max(6)
        private int numberWithMax;

        @DecimalMax("6.5")
        private int numberWithDecimalMax;

        @Min(7)
        @Max(8)
        private int numberWithMinAndMax;

        @Min(9)
        @DecimalMax("9.5")
        private int numberWithMinAndDecimalMax;

        @DecimalMin("10.5")
        @Max(11)
        private int numberWithDecimalMinAndMax;

        @DecimalMin("11.5")
        @DecimalMax("12.5")
        private int numberWithDecimalMinAndDecimalMax;

        /*
        /**********************************************************
        /* String fields
        /**********************************************************
        */

        private String stringWithoutConstraints;

        @Size(min = 13)
        private String stringWithMinSize;

        @Size(max = 14)
        private String stringWithMaxSize;

        @Size(min = 15, max = 16)
        private String stringWithMinAndMaxSize;

        @Pattern(regexp = "[a-z]+")
        private String stringWithPattern;

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

        public String getStringWithPattern() {
            return stringWithPattern;
        }

        public void setStringWithPattern(final String stringWithPattern) {
            this.stringWithPattern = stringWithPattern;
        }
    }

    /*
    /**********************************************************
    /* Unit tests, success
    /**********************************************************
     */

    private Object[][] listTestData() {
        return new Object[][] {{"listWithoutConstraints", null, null},
                {"listWithMinSize", 1, null},
                {"listWithMaxSize", null, 2},
                {"listWithMinAndMaxSize", 3, 4}};
    }

    private Object[][] numberTestData() {
        return new Object[][] {{"numberWithoutConstraints", null, null},
                {"numberWithMin", new BigDecimal(5d), null},
                {"numberWithDecimalMin", new BigDecimal(5.5), null},
                {"numberWithMax", null, new BigDecimal(6d)},
                {"numberWithDecimalMax", null, new BigDecimal(6.5)},
                {"numberWithMinAndMax", new BigDecimal(7d), new BigDecimal(8d)},
                {"numberWithMinAndDecimalMax", new BigDecimal(9d), new BigDecimal(9.5)},
                {"numberWithDecimalMinAndMax", new BigDecimal(10.5), new BigDecimal(11d)},
                {"numberWithDecimalMinAndDecimalMax", new BigDecimal(11.5), new BigDecimal(12.5)}};
    }

    private Object[][] stringTestData() {
        return new Object[][] {{"stringWithoutConstraints", null, null},
                {"stringWithMinSize", 13, null},
                {"stringWithMaxSize", null, 14},
                {"stringWithMinAndMaxSize", 15, 16}};
    }

    private Object[][] stringPatternTestData() {
        return new Object[][] {{"stringWithPattern", "[a-z]+"},
                {"stringWithoutConstraints", null}};
    }

    /**
     * Test set validation constraints
     */
    public void testAddingValidationConstraints() throws Exception {
        ValidationSchemaFactoryWrapper visitor = new ValidationSchemaFactoryWrapper();
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
            assertEquals(testCase[1], numberSchema.getMinimum());
            assertEquals(testCase[2], numberSchema.getMaximum());
        }
        for (Object[] testCase : stringTestData()) {
            JsonSchema propertySchema = properties.get(testCase[0]);
            assertNotNull(propertySchema);
            assertTrue(propertySchema.isStringSchema());
            StringSchema stringSchema = propertySchema.asStringSchema();
            assertEquals(testCase[1], stringSchema.getMinLength());
            assertEquals(testCase[2], stringSchema.getMaxLength());
        }
        for (Object[] testCase : stringPatternTestData()) {
            JsonSchema propertySchema = properties.get(testCase[0]);
            assertNotNull(propertySchema);
            assertTrue(propertySchema.isStringSchema());
            StringSchema stringSchema = propertySchema.asStringSchema();
            assertEquals(testCase[1], stringSchema.getPattern());
        }
    }

}
