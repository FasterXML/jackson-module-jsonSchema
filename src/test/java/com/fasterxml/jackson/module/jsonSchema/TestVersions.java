package com.fasterxml.jackson.module.jsonSchema;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Map;

import javax.validation.constraints.NotNull;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.customProperties.ValidationSchemaPropertyProcessorManagerFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.factories.WrapperFactory.JsonSchemaVersion;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;

/**
 * @author amerritt
 */
public class TestVersions extends SchemaTestBase {
    public static class Internal {
        @NotNull
        String nameMandatory;

        public String getNameMandatory() {
            return nameMandatory;
        }

        public void setNameMandatory(String nameMandatory) {
            this.nameMandatory = nameMandatory;
        }
    }

    @Test
    public void testAddingValidationConstraints_DefaultV4() throws Exception {
        Class<?> testType = Internal.class;

        ValidationSchemaPropertyProcessorManagerFactoryWrapper visitor = new ValidationSchemaPropertyProcessorManagerFactoryWrapper(testType);
        ObjectMapper mapper = new ObjectMapper();
        mapper.acceptJsonFormatVisitor(testType, visitor);
        JsonSchema jsonSchema = visitor.finalSchema();

        assertNotNull("schema should not be null.", jsonSchema);
        assertTrue("schema should be an objectSchema.", jsonSchema.isObjectSchema());
        ObjectSchema objectSchema = jsonSchema.asObjectSchema();
        assertThat(objectSchema.getRequired(), containsInAnyOrder("nameMandatory"));
    }

    @Test(expected = RuntimeException.class)
    public void testAddingValidationConstraints_DefaultV4_ExceptionOnGet() throws Exception {
        Class<?> testType = Internal.class;

        ValidationSchemaPropertyProcessorManagerFactoryWrapper visitor = new ValidationSchemaPropertyProcessorManagerFactoryWrapper(testType);
        ObjectMapper mapper = new ObjectMapper();
        mapper.acceptJsonFormatVisitor(testType, visitor);
        JsonSchema jsonSchema = visitor.finalSchema();

        assertNotNull("schema should not be null.", jsonSchema);
        assertTrue("schema should be an objectSchema.", jsonSchema.isObjectSchema());
        ObjectSchema objectSchema = jsonSchema.asObjectSchema();
        objectSchema.getRequiredBoolean(); //Should blow up...
    }

    @Test
    public void testAddingValidationConstraints_V4() throws Exception {
        Class<?> testType = Internal.class;

        ValidationSchemaPropertyProcessorManagerFactoryWrapper visitor = new ValidationSchemaPropertyProcessorManagerFactoryWrapper(JsonSchemaVersion.DRAFT_V4, testType);
        ObjectMapper mapper = new ObjectMapper();
        mapper.acceptJsonFormatVisitor(testType, visitor);
        JsonSchema jsonSchema = visitor.finalSchema();

        assertNotNull("schema should not be null.", jsonSchema);
        assertTrue("schema should be an objectSchema.", jsonSchema.isObjectSchema());
        ObjectSchema objectSchema = jsonSchema.asObjectSchema();
        assertThat(objectSchema.getRequired(), containsInAnyOrder("nameMandatory"));
    }

    @Test(expected = RuntimeException.class)
    public void testAddingValidationConstraints_V4_ExceptionOnGet() throws Exception {
        Class<?> testType = Internal.class;

        ValidationSchemaPropertyProcessorManagerFactoryWrapper visitor = new ValidationSchemaPropertyProcessorManagerFactoryWrapper(JsonSchemaVersion.DRAFT_V4, testType);
        ObjectMapper mapper = new ObjectMapper();
        mapper.acceptJsonFormatVisitor(testType, visitor);
        JsonSchema jsonSchema = visitor.finalSchema();

        assertNotNull("schema should not be null.", jsonSchema);
        assertTrue("schema should be an objectSchema.", jsonSchema.isObjectSchema());
        ObjectSchema objectSchema = jsonSchema.asObjectSchema();
        objectSchema.getRequiredBoolean(); //Should blow up...
    }

    @Test
    public void testAddingValidationConstraints_V3() throws Exception {
        Class<?> testType = Internal.class;

        ValidationSchemaPropertyProcessorManagerFactoryWrapper visitor = new ValidationSchemaPropertyProcessorManagerFactoryWrapper(JsonSchemaVersion.DRAFT_V3, testType);
        ObjectMapper mapper = new ObjectMapper();
        mapper.acceptJsonFormatVisitor(testType, visitor);
        JsonSchema jsonSchema = visitor.finalSchema();

        assertNotNull("schema should not be null.", jsonSchema);
        assertTrue("schema should be an objectSchema.", jsonSchema.isObjectSchema());
        ObjectSchema objectSchema = jsonSchema.asObjectSchema();
        printJsonSchema(objectSchema);
        Map<String, JsonSchema> properties = objectSchema.getProperties();
        assertNotNull(properties);

        JsonSchema propertySchema = properties.get("nameMandatory");
        assertNotNull(propertySchema);
        assertThat(propertySchema.getRequiredBoolean(), is(true));

    }

    @Test(expected = RuntimeException.class)
    public void testAddingValidationConstraints_V3_ExceptionOnGet() throws Exception {
        Class<?> testType = Internal.class;

        ValidationSchemaPropertyProcessorManagerFactoryWrapper visitor = new ValidationSchemaPropertyProcessorManagerFactoryWrapper(JsonSchemaVersion.DRAFT_V3, testType);
        ObjectMapper mapper = new ObjectMapper();
        mapper.acceptJsonFormatVisitor(testType, visitor);
        JsonSchema jsonSchema = visitor.finalSchema();

        assertNotNull("schema should not be null.", jsonSchema);
        assertTrue("schema should be an objectSchema.", jsonSchema.isObjectSchema());
        ObjectSchema objectSchema = jsonSchema.asObjectSchema();
        objectSchema.getRequired(); //should blow up...
    }


    void printJsonSchema(JsonSchema jsonSchema) throws JsonProcessingException {
        System.err.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema));
    }
}
