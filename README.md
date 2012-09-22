# [jackson](http://jackson.codehaus.org) [jsonSchema](http://json-schema.org/) Module
This module supports the creation of a json schema (roughly alligned with draft version 3) specifying the expected outfrom from a given jackson configured application for a given java type. This module is intended to be an upgrade path from the hardcoded jsonschema generation 
currently in jackson databind (pre 2.1), in order to allow for the generation of arbitrary formats specifying the expected output from a particular jackson enabled application. Thus, it might feasibly be extended or mirrored to produce xml or even google closure interfaces or classes in addition to the current json schema format. 


##status
The proper release of this module is pending [jackson 2.1](http://jira.codehaus.org/browse/JACKSON#selectedTab=com.atlassian.jira.plugin.system.project%3Aroadmap-panel). Currently the module has a dependency on 2.1.0-SNAPSHOT which is not available in the central maven repo.
This module is very new, and thus will probably be volatile.

##example usage (from [TestGenerateJsonSchema](https://github.com/FasterXML/jackson-module-jsonSchema/blob/master/src/test/java/com/fasterxml/jackson/databind/jsonSchema/TestGenerateJsonSchema.java#L114))

to install, you should must have a local installation of jackson-core, jackson-annotations, jackson-databind from `2.1.0-snapsshot`

cloning those three from github, and running 
```bash
maven install
```
first on core, then annotations, databind, and finally jsonschema
should work to install jsonschema in your local maven repo.

simply add a dependency (this is from my gradle config)
`"com.fasterxml.jackson.datatype:jackson-datatype-jsonSchema:2.1.0-SNAPSHOT"`
and for gradle, at least, you can simply add `mavenLocal()` to your repositories. 
Maven should resolve the dependency from its local repo transparently.

```java
ObjectMapper m = new ObjectMapper();
SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
m.acceptJsonFormatVisitor(TypeFactory.defaultInstance().constructType(SimpleBean.class), visitor);
JsonSchema jsonSchema = visitor.finalSchema();
```

This will yield a java pojo representing a json schema, which can itself easily be serialized with jackson, or configured with java. Customizing the generation should be simply a matter of locating the particular stage of generation you want to override, and replacing or extending that particular object in the dependency injection cycle in schemafactory wrapper.