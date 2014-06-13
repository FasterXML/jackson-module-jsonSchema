package com.fasterxml.jackson.module.jsonSchema;

import junit.framework.TestCase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.customProperties.TitleSchemaFactoryWrapper;

public class TitleSchemaFactoryWrapperTest extends TestCase{

	public class Pet {
		public String genus;
	}
	
	public class Person {
		public String name;
		public String hat;
		public Pet pet;
	}
	
	public void testAddingTitle() throws Exception
	{
		TitleSchemaFactoryWrapper visitor = new TitleSchemaFactoryWrapper();
		ObjectMapper mapper = new ObjectMapper();

		mapper.acceptJsonFormatVisitor(Person.class, visitor);
		JsonSchema schema = visitor.finalSchema();

        System.out.println("schema: " + mapper.writeValueAsString(schema));
        System.out.println("title1: " + schema.asObjectSchema().getTitle());
        System.out.println("title2: " + schema.asObjectSchema().getProperties().get("pet").asObjectSchema().getTitle());

		assertTrue("schema should be an objectSchema.", schema.isObjectSchema());
		String title = schema.asObjectSchema().getTitle();
		assertNotNull(title);
		assertTrue("schema should have a title", title.indexOf("Person") != -1);
		JsonSchema schema2 = schema.asObjectSchema().getProperties().get("pet");
		assertTrue("schema should be an objectSchema.", schema2.isObjectSchema());
		String title2 = schema2.asObjectSchema().getTitle();
		assertNotNull(title2);
		assertTrue("schema should have a title", title2.indexOf("Pet") != -1);
	}
}
