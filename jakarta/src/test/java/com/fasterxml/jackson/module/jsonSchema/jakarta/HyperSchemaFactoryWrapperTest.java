package com.fasterxml.jackson.module.jsonSchema.jakarta;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.jakarta.types.LinkDescriptionObject;
import com.fasterxml.jackson.module.jsonSchema.jakarta.annotation.JsonHyperSchema;
import com.fasterxml.jackson.module.jsonSchema.jakarta.annotation.Link;
import com.fasterxml.jackson.module.jsonSchema.jakarta.customProperties.HyperSchemaFactoryWrapper;

/**
 * Created by mavarazy on 4/21/14.
 */
public class HyperSchemaFactoryWrapperTest extends SchemaTestBase {

    public class Pet {
        public String genus;
    }

    @JsonHyperSchema(pathStart = "/persons/", links = {
        @Link(href = "{name}", rel = "self"),
        @Link(href = "{name}/pet", rel = "pet", targetSchema = Pet.class)
    })
    public class Person {
        public String name;
        public String hat;
    }

    public void testSimpleHyperWithDefaultSchema() throws Exception {
        HyperSchemaFactoryWrapper personVisitor = new HyperSchemaFactoryWrapper();
        personVisitor.setIgnoreDefaults(false);
        ObjectMapper mapper = new ObjectMapper();

        mapper.acceptJsonFormatVisitor(Person.class, personVisitor);
        JsonSchema personSchema = personVisitor.finalSchema();

        HyperSchemaFactoryWrapper petVisitor = new HyperSchemaFactoryWrapper();
        mapper.acceptJsonFormatVisitor(Pet.class, petVisitor);
        JsonSchema petSchema = petVisitor.finalSchema();

        assertTrue("schema should be an objectSchema.", personSchema.isObjectSchema());
        LinkDescriptionObject[] links = personSchema.asObjectSchema().getLinks();
        assertNotNull(links);
        assertEquals(links.length, 2);
        LinkDescriptionObject selfLink = links[0];
        assertEquals("/persons/{name}", selfLink.getHref());
        assertEquals("self", selfLink.getRel());
        assertEquals("application/json", selfLink.getEnctype());
        assertEquals("GET", selfLink.getMethod());

        LinkDescriptionObject petLink = links[1];
        assertEquals("/persons/{name}/pet", petLink.getHref());
        assertEquals("pet", petLink.getRel());
        assertEquals("application/json", petLink.getEnctype());
        assertEquals("GET", petLink.getMethod());
        assertEquals(petSchema, petLink.getTargetSchema());
    }

    public void testSimpleHyperWithoutDefaultSchema() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();

        /*
        HyperSchemaFactoryWrapper personVisitor = new HyperSchemaFactoryWrapper();

        mapper.acceptJsonFormatVisitor(Person.class, personVisitor);
        JsonSchema personSchema = personVisitor.finalSchema();
        */
        JsonSchema personSchema = new JsonSchemaGenerator(mapper,
                new HyperSchemaFactoryWrapper())
            .generateSchema(Person.class);

        HyperSchemaFactoryWrapper petVisitor = new HyperSchemaFactoryWrapper();
        mapper.acceptJsonFormatVisitor(Pet.class, petVisitor);
        JsonSchema petSchema = petVisitor.finalSchema();

        assertTrue("schema should be an objectSchema.", personSchema.isObjectSchema());
        LinkDescriptionObject[] links = personSchema.asObjectSchema().getLinks();
        assertNotNull(links);
        assertEquals(links.length, 2);
        LinkDescriptionObject selfLink = links[0];
        assertEquals("/persons/{name}", selfLink.getHref());
        assertEquals("self", selfLink.getRel());
        assertEquals(null, selfLink.getEnctype());
        assertEquals(null, selfLink.getMethod());

        LinkDescriptionObject petLink = links[1];
        assertEquals("/persons/{name}/pet", petLink.getHref());
        assertEquals("pet", petLink.getRel());
        assertEquals(null, petLink.getEnctype());
        assertEquals(null, petLink.getMethod());
        assertEquals(petSchema, petLink.getTargetSchema());
    }

}
