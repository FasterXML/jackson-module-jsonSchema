# Jackson [JSON Schema](http://json-schema.org/) Module

This module supports the creation of a json schema (roughly alligned with draft version 3) specifying the expected outfrom from a given jackson configured application for a given java type. This module is intended to be an upgrade path from the hardcoded jsonschema generation 
currently in jackson databind (pre 2.1), in order to allow for the generation of arbitrary formats specifying the expected output from a particular jackson enabled application. Thus, it might feasibly be extended or mirrored to produce xml or even google closure interfaces or classes in addition to the current json schema format. 

## Status

Version 2.1 of this module is the first functioning version; but significant changes are possible for 2.2.
This module is very new, and 2.2 is expected to be the first fully stable release.

## Example Usage (from [TestGenerateJsonSchema](https://github.com/FasterXML/jackson-module-jsonSchema/blob/master/src/test/java/com/fasterxml/jackson/module/jsonSchema/TestGenerateJsonSchema.java#L136))

simply add a dependency (this is from my gradle config)
`"com.fasterxml.jackson.datatype:jackson-datatype-jsonSchema:2.1.0"`
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

## More

Check out [Project Wiki](http://github.com/FasterXML/jackson-module-jsonSchema/wiki) for more information (javadocs, downloads).
