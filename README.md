# Jackson [JSON Schema](http://json-schema.org/) Module

This module supports the creation of a JSON Schema (v3)

Note that since JSON Schema draft version 3 and 4 are incompatible, this module CAN NOT, as-is,
support v4. There is another module [mbknor-jackson-jsonSchema](https://github.com/mbknor/mbknor-jackson-jsonSchema) that does support v4, however.

It is possible that in future this repo could have 2 different modules; one for v3, another v4.
And if necessary, more if future revisions also prove incompatible.

## Status

[![Build Status](https://travis-ci.org/FasterXML/jackson-module-jsonSchema.svg)](https://travis-ci.org/FasterXML/jackson-module-jsonSchema)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.fasterxml.jackson.module/jackson-module-jsonSchema/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.fasterxml.jackson.module/jackson-module-jsonSchema/)
[![Javadoc](https://javadoc.io/badge/com.fasterxml.jackson.module/jackson-module-jsonSchema.svg)](http://www.javadoc.io/doc/com.fasterxml.jackson.module/jackson-module-jsonSchema)

Version 2.4 was considered the first stable version of the module.

## Two modules (with Jackson 2.15)

Starting with upcoming Jackson 2.15, this repository contains TWO versions of the module:

1. Old JSON Schema module `com.fasterxml.jackson.module:jackson-module-jsonSchema` (since 2.0)
2. New JSON Schema module `com.fasterxml.jackson.module:jackson-module-jsonSchema-jakarta` (2.15+)

depending on whether system needs to work with "old" `javax`/`JAXB` APIs (first module) or newer "Jakarta" APIs.

## Future plans (lack thereof)

Due to lack of support by community, this module is NOT planned to be supported beyond
Jackson 2.x -- no work has been done for it to work with future Jackson 3.0.
Users are encouraged to use more up-to-date JSON Schema support tools.

## Example Usage

(from [TestGenerateJsonSchema](https://github.com/FasterXML/jackson-module-jsonSchema/blob/master/src/test/java/com/fasterxml/jackson/module/jsonSchema/TestGenerateJsonSchema.java#L136))

simply add a dependency
`"com.fasterxml.jackson.module:jackson-module-jsonSchema:2.15.0"`
or
`"com.fasterxml.jackson.module:jackson-module-jsonSchema-jakarta:2.15.0"` for the jakarta namespace

and for gradle, at least, you can simply add `mavenLocal()` to your repositories. 
Maven should resolve the dependency from its local repo transparently.

```java
ObjectMapper mapper = new ObjectMapper();
// configure mapper, if necessary, then create schema generator
JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
JsonSchema schema = schemaGen.generateSchema(SimpleBean.class);
```

This will yield a java pojo representing a JSON Schema, which can itself easily be serialized with jackson, or configured with java. Customizing the generation should be simply a matter of locating the particular stage of generation you want to override, and replacing or extending that particular object in the dependency injection cycle in schemafactory wrapper.

## Adding Property Processing

See `com.fasterxml.jackson.module.jsonSchema.customProperties.TitleSchemaFactoryWrapper` for an example of writing custom schema properties.

## Required Fields

JSON Schema has the ability to mark fields as required. This module supports this via the `@JsonProperty(required = true)` field annotation.

## JsonSchema Hypermedia support
### Generic support
Current implementation is partial for IETF published draft v4 (http://json-schema.org/latest/json-schema-hypermedia.html).

Currently 2 aspects of IETF supported:
* pathStart - URI that defines what the instance's URI MUST start with in order to validate.
* links - associated Link Description Objects with instances.

You can enable HypermediaSupport using `com.fasterxml.jackson.module.jsonSchema.customProperties.HyperSchemaFactoryWrapper`.
Example:

```java
HyperSchemaFactoryWrapper personVisitor = new HyperSchemaFactoryWrapper();
ObjectMapper mapper = new ObjectMapper();
mapper.acceptJsonFormatVisitor(Person.class, personVisitor);
JsonSchema personSchema = personVisitor.finalSchema();
```

By default all default values for Link Description Object are ignored in the output (method = GET, enctype = application/json, mediaType = application/json), to enable default setIgnoreDefaults(true)

### Describing JSON hyper-schema

You can describe hyperlinks, using annotations @JsonHyperSchema & @Link

     public class Pet {
         public String genus;
     }

     @JsonHyperSchema(
         pathStart = "http://localhost:8080/persons/",
         links = {
             @Link(href = "{name}", rel = "self"),
             @Link(href = "{name}/pet", rel = "pet", targetSchema = Pet.class)
     })
     public class Person {
         public String name;
         public String hat;
     }

Would generate following values:

    {
      "type" : "object",
      "pathStart" : "http://localhost:8080/persons/",
      "links" : [ {
        "href" : "http://localhost:8080/persons/{name}",
        "rel" : "self"
      }, {
        "href" : "http://localhost:8080/persons/{name}/pet",
        "rel" : "pet",
        "targetSchema" : {
          "type" : "object",
          "properties" : {
            "genus" : {
              "type" : "string"
            }
          }
        }
      } ],
      "properties" : {
        "name" : {
          "type" : "string"
        },
        "hat" : {
          "type" : "string"
        }
      }
    }

## More

Check out [Project Wiki](http://github.com/FasterXML/jackson-module-jsonSchema/wiki) for more information (javadocs, downloads).
