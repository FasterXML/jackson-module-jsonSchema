# Jackson [JSON Schema](http://json-schema.org/) Module

This module supports the creation of a json schema (roughly aligned with draft version 3) specifying the expected output from a given jackson configured application for a given java type.
This module is intended to be an upgrade path from the hardcoded JSON Schema generation 
currently in jackson databind (pre 2.1), in order to allow for the generation of arbitrary formats specifying the expected output from a particular jackson enabled application. Thus, it might feasibly be extended or mirrored to produce xml or even google closure interfaces or classes in addition to the current json schema format. 

## Status

[![Build Status](https://travis-ci.org/FasterXML/jackson-module-jsonSchema.svg)](https://travis-ci.org/FasterXML/jackson-module-jsonSchema)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.fasterxml.jackson.module/jackson-module-jsonSchema/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.fasterxml.jackson.module/jackson-module-jsonSchema/)
[![Javadoc](https://javadoc-emblem.rhcloud.com/doc/com.fasterxml.jackson.module/jackson-module-jsonSchema/badge.svg)](http://www.javadoc.io/doc/com.fasterxml.jackson.module/jackson-module-jsonSchema)

Version 2.1 of this module is the first functioning version; but significant changes are possible for 2.2.
This module is very new, and 2.2 is expected to be the first fully stable release.

## Example Usage (from [TestGenerateJsonSchema](https://github.com/FasterXML/jackson-module-jsonSchema/blob/master/src/test/java/com/fasterxml/jackson/module/jsonSchema/TestGenerateJsonSchema.java#L136))

simply add a dependency (this is from my gradle config)
`"com.fasterxml.jackson.module:jackson-module-jsonSchema:2.5.2"`
and for gradle, at least, you can simply add `mavenLocal()` to your repositories. 
Maven should resolve the dependency from its local repo transparently.

```java
ObjectMapper m = new ObjectMapper();
SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
m.acceptJsonFormatVisitor(m.constructType(SimpleBean.class), visitor);
JsonSchema jsonSchema = visitor.finalSchema();
```

This will yield a java pojo representing a json schema, which can itself easily be serialized with jackson, or configured with java. Customizing the generation should be simply a matter of locating the particular stage of generation you want to override, and replacing or extending that particular object in the dependency injection cycle in schemafactory wrapper.

## Adding Property Processing

See com.fasterxml.jackson.module.jsonSchema.customProperties.TitleSchemaFactoryWrapper for an example of writing custom schema properties.

## Required Fields

JSON Schema has the ability to mark fields as required. This module supports this via the `@JsonProperty(required = true)` field annotation.

## JsonSchema Hypermedia support
### Generic support
Current implementation is partial for IETF published draft v4 (http://json-schema.org/latest/json-schema-hypermedia.html).

Currently 2 aspects of IETF supported:
* pathStart - URI that defines what the instance's URI MUST start with in order to validate.
* links - associated Link Description Objects with instances.

You can enable HypermediaSupport using _com.fasterxml.jackson.module.jsonSchema.customProperties.HyperSchemaFactoryWrapper_.
Example:

         HyperSchemaFactoryWrapper personVisitor = new HyperSchemaFactoryWrapper();
         ObjectMapper mapper = new ObjectMapper();
         mapper.acceptJsonFormatVisitor(Person.class, personVisitor);
         JsonSchema personSchema = personVisitor.finalSchema();

By default all default values for Link Description Object are ignored in the output (method = GET, enctype = application/json, mediaType = application/json), to enable default setIgnoreDefaults(true)



### Describing json hyper schema

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
