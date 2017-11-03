package com.fasterxml.jackson.module.jsonSchema;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Map;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.annotation.JsonSchemaTitle;
import com.fasterxml.jackson.module.jsonSchema.customProperties.SchemaPropertyProcessorManagerFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.customProperties.ValidationSchemaPropertyProcessorManagerFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.property.SchemaPropertyProcessorTitle;
import com.fasterxml.jackson.module.jsonSchema.property.manager.SchemaPropertyProcessorManagerConstraint;
import com.fasterxml.jackson.module.jsonSchema.types.ArraySchema;
import com.fasterxml.jackson.module.jsonSchema.types.NumberSchema;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;
import com.fasterxml.jackson.module.jsonSchema.types.SimpleTypeSchema;
import com.fasterxml.jackson.module.jsonSchema.types.StringSchema;

/**
 * @author cponomaryov
 */
public class ValidationSchemaFactoryWrapperTest extends SchemaTestBase {
    @Size(min = 2, max = 80)
    @MyPattern(message = "")
    @Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = {})
    public @interface MemberNumber {
        @OverridesAttribute.List({ //
            @OverridesAttribute(constraint = Size.class, name = "message"), //
            @OverridesAttribute(constraint = MyPattern.class, name = "message")})
        public String message();
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    @Pattern(regexp = "[\\p{Alnum}]*")
    @Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = {})
    public @interface MyPattern {
        @OverridesAttribute(constraint = Pattern.class, name = "message")
        public String message();
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    public interface GroupA {
    }

    public interface GroupB {
    }

    public interface None {
    }

    public static class Internal {
        enum Animals {
            DOG, CAT
        };

        @NotNull(groups = {Default.class, GroupA.class})
        private Animals animal;

        @JsonSchemaTitle("This name is optional.")
        String nameOptional;

        @NotNull(groups = {Default.class, GroupB.class})
        String nameMandatory;

        public Animals getAnimal() {
            return animal;
        }

        public void setAnimal(Animals animal) {
            this.animal = animal;
        }

        public String getNameOptional() {
            return nameOptional;
        }

        public void setNameOptional(String nameOptional) {
            this.nameOptional = nameOptional;
        }

        public String getNameMandatory() {
            return nameMandatory;
        }

        public void setNameMandatory(String nameMandatory) {
            this.nameMandatory = nameMandatory;
        }
    }

    public static class ValidationBean {

        @Valid
        @NotNull
        Internal internal;

        @Valid
        @NotNull
        Internal internal2;

        @MemberNumber(message = "Invalid member num.", groups = {Default.class, GroupA.class})
        private String memberNumber;

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

        /*
        /**********************************************************
        /* Nullable and not nullable fields
        /**********************************************************
        */

        private String nullable;

        @NotNull
        private String notNullable;

        public Internal getInternal() {
            return internal;
        }

        public void setInternal(Internal internal) {
            this.internal = internal;
        }

        public Internal getInternal2() {
            return internal2;
        }

        public void setInterna2l(Internal internal2) {
            this.internal2 = internal2;
        }

        public String getMemberNumber() {
            return memberNumber;
        }

        public void setMemberNumber(String memberNumber) {
            this.memberNumber = memberNumber;
        }

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

        public String getNullable() {
            return nullable;
        }

        public void setNullable(String nullable) {
            this.nullable = nullable;
        }

        public String getNotNullable() {
            return notNullable;
        }

        public void setNotNullable(String notNullable) {
            this.notNullable = notNullable;
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
                {"numberWithMin", 5d, null},
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

    private Object[][] stringPatternTestData() {
        return new Object[][] {{"stringWithPattern", "[a-z]+"},
                {"stringWithoutConstraints", null}};
    }

    private Object[][] notNullTestData() {
        return new Object[][] {
            {"nullable", false},
                {"notNullable", true}};
    }

    /**
     * Test set validation constraints
     */
    @Test
    public void testAddingValidationConstraints() throws Exception {
        ValidationSchemaPropertyProcessorManagerFactoryWrapper visitor = new ValidationSchemaPropertyProcessorManagerFactoryWrapper(ValidationBean.class);
        ObjectMapper mapper = new ObjectMapper();

        mapper.acceptJsonFormatVisitor(ValidationBean.class, visitor);
        JsonSchema jsonSchema = visitor.finalSchema();

        printJsonSchema(jsonSchema);

        assertNotNull("schema should not be null.", jsonSchema);
        assertTrue("schema should be an objectSchema.", jsonSchema.isObjectSchema());
        ObjectSchema objectSchema = jsonSchema.asObjectSchema();
        Map<String, JsonSchema> properties = objectSchema.getProperties();
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
        for (Object[] testCase : notNullTestData()) {
            JsonSchema propertySchema = properties.get(testCase[0]);
            assertNotNull(propertySchema);
            assertTrue(propertySchema.isStringSchema());
            StringSchema stringSchema = propertySchema.asStringSchema();
            assertEquals(testCase[1], stringSchema.getParent().getRequired().contains(testCase[0]));
        }
    }

    @Test
    public void testAddingValidationConstraints_InternalRequired() throws Exception {
        ValidationSchemaPropertyProcessorManagerFactoryWrapper visitor = new ValidationSchemaPropertyProcessorManagerFactoryWrapper(ValidationBean.class);
        ObjectMapper mapper = new ObjectMapper();

        mapper.acceptJsonFormatVisitor(ValidationBean.class, visitor);
        JsonSchema jsonSchema = visitor.finalSchema();

        printJsonSchema(jsonSchema);

        assertNotNull("schema should not be null.", jsonSchema);
        assertTrue("schema should be an objectSchema.", jsonSchema.isObjectSchema());
        ObjectSchema objectSchema = jsonSchema.asObjectSchema();
        Map<String, JsonSchema> properties = objectSchema.getProperties();
        assertNotNull(properties);
        JsonSchema propertySchema = properties.get("internal");
        assertNotNull(propertySchema);
        assertTrue(propertySchema.isObjectSchema());
        ObjectSchema intObjectSchema = propertySchema.asObjectSchema();
        assertThat(intObjectSchema.getRequired(), containsInAnyOrder("animal", "nameMandatory"));
    }

    @Test
    public void testAddingValidationConstraints_CustomAnnotation() throws Exception {
        ValidationSchemaPropertyProcessorManagerFactoryWrapper visitor = new ValidationSchemaPropertyProcessorManagerFactoryWrapper(ValidationBean.class);
        ObjectMapper mapper = new ObjectMapper();

        mapper.acceptJsonFormatVisitor(ValidationBean.class, visitor);
        JsonSchema jsonSchema = visitor.finalSchema();

        assertNotNull("schema should not be null.", jsonSchema);
        assertTrue("schema should be an objectSchema.", jsonSchema.isObjectSchema());
        ObjectSchema objectSchema = jsonSchema.asObjectSchema();
        Map<String, JsonSchema> properties = objectSchema.getProperties();
        assertNotNull(properties);
        JsonSchema propertySchema = properties.get("memberNumber");
        assertNotNull(propertySchema);
        assertTrue(propertySchema.isStringSchema());
        StringSchema stringSchema = propertySchema.asStringSchema();
        assertEquals(new Integer("2"), stringSchema.getMinLength());
        assertEquals(new Integer("80"), stringSchema.getMaxLength());
        assertEquals("[\\p{Alnum}]*", stringSchema.getPattern());
    }

    @Test
    public void testAddingValidationConstraints_LimitedByGroup() throws Exception {
        ValidationSchemaPropertyProcessorManagerFactoryWrapper visitor = new ValidationSchemaPropertyProcessorManagerFactoryWrapper(ValidationBean.class, GroupB.class);
        ObjectMapper mapper = new ObjectMapper();

        mapper.acceptJsonFormatVisitor(ValidationBean.class, visitor);
        JsonSchema jsonSchema = visitor.finalSchema();

        assertNotNull("schema should not be null.", jsonSchema);
        assertTrue("schema should be an objectSchema.", jsonSchema.isObjectSchema());
        ObjectSchema objectSchema = jsonSchema.asObjectSchema();
        Map<String, JsonSchema> properties = objectSchema.getProperties();
        assertNotNull(properties);
        JsonSchema propertySchema = properties.get("memberNumber");
        assertNotNull(propertySchema);
        assertTrue(propertySchema.isStringSchema());
        StringSchema stringSchema = propertySchema.asStringSchema();
        assertNull(stringSchema.getMinLength());
        assertNull(stringSchema.getMaxLength());
        assertNull(stringSchema.getPattern());

        propertySchema = properties.get("internal");
        assertNotNull(propertySchema);
        assertTrue(propertySchema.isObjectSchema());
        ObjectSchema intObjectSchema = propertySchema.asObjectSchema();
        assertThat(intObjectSchema.getRequired(), containsInAnyOrder("nameMandatory"));
    }

    @Test
    public void testAddingValidationConstraints_NoValidation() throws Exception {
        ValidationSchemaPropertyProcessorManagerFactoryWrapper visitor = new ValidationSchemaPropertyProcessorManagerFactoryWrapper(ValidationBean.class, None.class);
        ObjectMapper mapper = new ObjectMapper();

        mapper.acceptJsonFormatVisitor(ValidationBean.class, visitor);
        JsonSchema jsonSchema = visitor.finalSchema();

        assertNotNull("schema should not be null.", jsonSchema);
        assertTrue("schema should be an objectSchema.", jsonSchema.isObjectSchema());
        ObjectSchema objectSchema = jsonSchema.asObjectSchema();
        Map<String, JsonSchema> properties = objectSchema.getProperties();
        assertNotNull(properties);
        JsonSchema propertySchema = properties.get("memberNumber");
        assertNotNull(propertySchema);
        assertTrue(propertySchema.isStringSchema());
        StringSchema stringSchema = propertySchema.asStringSchema();
        assertNull(stringSchema.getMinLength());
        assertNull(stringSchema.getMaxLength());
        assertNull(stringSchema.getPattern());

        propertySchema = properties.get("internal");
        assertNotNull(propertySchema);
        assertTrue(propertySchema.isObjectSchema());
        ObjectSchema intObjectSchema = propertySchema.asObjectSchema();
        assertThat(intObjectSchema.getRequired(), is(empty()));
    }

    @Test
    public void testAddingValidationConstraints_CustomPropertyManager() throws Exception {
        SchemaPropertyProcessorManagerConstraint customPropertyProcessorManager = new SchemaPropertyProcessorManagerConstraint(ValidationBean.class, Default.class);
        customPropertyProcessorManager.registerSchemaPropertyProcessor(new SchemaPropertyProcessorTitle());
        SchemaPropertyProcessorManagerFactoryWrapper visitor = new SchemaPropertyProcessorManagerFactoryWrapper(customPropertyProcessorManager);
        ObjectMapper mapper = new ObjectMapper();

        mapper.acceptJsonFormatVisitor(ValidationBean.class, visitor);
        JsonSchema jsonSchema = visitor.finalSchema();

        printJsonSchema(jsonSchema);

        assertNotNull("schema should not be null.", jsonSchema);
        assertTrue("schema should be an objectSchema.", jsonSchema.isObjectSchema());
        ObjectSchema objectSchema = jsonSchema.asObjectSchema();
        Map<String, JsonSchema> properties = objectSchema.getProperties();
        assertNotNull(properties);

        JsonSchema propertySchema = properties.get("internal");
        assertNotNull(propertySchema);
        assertTrue(propertySchema.isObjectSchema());
        ObjectSchema intObjectSchema = propertySchema.asObjectSchema();
        properties = intObjectSchema.getProperties();
        assertNotNull(properties);
        propertySchema = properties.get("nameOptional");
        assertTrue(propertySchema.isSimpleTypeSchema());
        SimpleTypeSchema stSchema = propertySchema.asSimpleTypeSchema();
        assertThat(stSchema.getTitle(), is(equalTo("This name is optional.")));
    }

    //TODO: see if I can make the constraint processors more generic
    //TODO: see about doing v3 and v4 of schema

    void printJsonSchema(JsonSchema jsonSchema) throws JsonProcessingException {
        System.err.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema));
    }
}
