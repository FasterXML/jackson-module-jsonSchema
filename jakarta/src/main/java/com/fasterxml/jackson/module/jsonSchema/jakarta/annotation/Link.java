package com.fasterxml.jackson.module.jsonSchema.jakarta.annotation;

/**
 *  A link description object is used to describe link relations.  In the
 context of a jsonSchema, it defines the link relations of the instances
 of the jsonSchema, and can be parameterized by the instance values.  The
 link description format can be used on its own in regular (non-jsonSchema
 documents), and use of this format can be declared by referencing the
 normative link description jsonSchema as the the jsonSchema for the data
 structure that uses the links.
 */
public @interface Link {

    /**
     * The value of the "href" link description property indicates the
       target URI of the related resource.  The value of the instance
       property SHOULD be resolved as a URI-Reference per RFC 3986 [RFC3986]
       and MAY be a relative URI.  The base URI to be used for relative
       resolution SHOULD be the URI used to retrieve the instance object
       (not the jsonSchema) when used within a jsonSchema.  Also, when links are
       used within a jsonSchema, the URI SHOULD be parametrized by the property
       values of the instance object, if property values exist for the
       corresponding variables in the template (otherwise they MAY be
       provided from alternate sources, like user input).

       Instance property values SHOULD be substituted into the URIs where
       matching braces ('{', '}') are found surrounding zero or more
       characters, creating an expanded URI.  Instance property value
       substitutions are resolved by using the text between the braces to
       denote the property name from the instance to get the value to
       substitute.  For example, if an href value is defined:

       http://somesite.com/{id}

       Then it would be resolved by replace the value of the "id" property
       value from the instance object.  If the value of the "id" property
       was "45", the expanded URI would be:

       http://somesite.com/45

       If matching braces are found with the string "@" (no quotes) between
       the braces, then the actual instance value SHOULD be used to replace
       the braces, rather than a property value.  This should only be used
       in situations where the instance is a scalar (string, boolean, or
       number), and not for objects or arrays.

     */
    String href();

    /**
     * The value of the "rel" property indicates the name of the relation to
       the target resource.  The relation to the target SHOULD be
       interpreted as specifically from the instance object that the jsonSchema
       (or sub-jsonSchema) applies to, not just the top level resource that
       contains the object within its hierarchy.  If a resource JSON
       representation contains a sub object with a property interpreted as a
       link, that sub-object holds the relation with the target.  A relation
       to target from the top level resource MUST be indicated with the
       jsonSchema describing the top level JSON representation.

       Relationship definitions SHOULD NOT be media type dependent, and
       users are encouraged to utilize existing accepted relation
       definitions, including those in existing relation registries (see RFC
       4287 [RFC4287]).  However, we define these relations here for clarity
       of normative interpretation within the context of JSON hyper jsonSchema
       defined relations:

       self  If the relation value is "self", when this property is
          encountered in the instance object, the object represents a
          resource and the instance object is treated as a full
          representation of the target resource identified by the specified
          URI.

       full  This indicates that the target of the link is the full
          representation for the instance object.  The object that contains
          this link possibly may not be the full representation.

       describedby  This indicates the target of the link is the jsonSchema for
          the instance object.  This MAY be used to specifically denote the
          schemas of objects within a JSON object hierarchy, facilitating
          polymorphic type data structures.

       root  This relation indicates that the target of the link SHOULD be
          treated as the root or the body of the representation for the
          purposes of user agent interaction or fragment resolution.  All
          other properties of the instance objects can be regarded as meta-
           data descriptions for the data.

       The following relations are applicable for schemas (the jsonSchema as the
       "from" resource in the relation):

       instances  This indicates the target resource that represents
          collection of instances of a jsonSchema.

       create  This indicates a target to use for creating new instances of
          a jsonSchema.  This link definition SHOULD be a submission link with a
          non-safe method (like POST).

       For example, if a jsonSchema is defined:

       {
         "links": [
           {
             "rel": "self"
             "href": "{id}"
           },
           {
             "rel": "up"
             "href": "{upId}"
           },
           {
             "rel": "children"
             "href": "?upId={id}"
           }
         ]
       }

       And if a collection of instance resource's JSON representation was
       retrieved:

       GET /Resource/

       [
         {
           "id": "thing",
           "upId": "parent"
         },
         {
           "id": "thing2",
           "upId": "parent"
         }
       ]

       This would indicate that for the first item in the collection, its
       own (self) URI would resolve to "/Resource/thing" and the first
       item's "up" relation SHOULD be resolved to the resource at
       "/Resource/parent".  The "children" collection would be located at
       "/Resource/?upId=thing".
     */
    String rel();


    /**
     * This property value is a jsonSchema that defines the expected structure
        of the JSON representation of the target of the link.
     */
    Class<?> targetSchema() default void.class;

    /**
     * This attribute defines which method can be used to access the target
       resource.  In an HTTP environment, this would be "GET" or "POST"
       (other HTTP methods such as "PUT" and "DELETE" have semantics that
       are clearly implied by accessed resources, and do not need to be
       defined here).  This defaults to "GET".
     */
    String method() default "GET";

    /**
     *  If present, this property indicates a query media type format that
       the server supports for querying or posting to the collection of
       instances at the target resource.  The query can be suffixed to the
       target URI to query the collection with property-based constraints on
       the resources that SHOULD be returned from the server or used to post
       data to the resource (depending on the method).  For example, with
       the following jsonSchema:

       {
        "links":[
          {
            "enctype":"application/x-www-form-urlencoded",
            "method":"GET",
            "href":"/Product/",
            "properties":{
               "name":{"description":"name of the product"}
            }
          }
        ]
       }
       This indicates that the client can query the server for instances
       that have a specific name:

       /Product/?name=Slinky

       If no enctype or method is specified, only the single URI specified
       by the href property is defined.  If the method is POST,
       "application/json" is the default media type.
     */
    String enctype() default "application/json";

    /**
     * This attribute contains a jsonSchema which defines the acceptable
     structure of the submitted request (for a GET request, this jsonSchema
     would define the properties for the query string and for a POST
     request, this would define the body).
     */
    Class<?> schema() default void.class;

    /**
     * This property defines a title for the link. The value must be a string.
       User agents MAY use this title when presenting the link to the user.
     */
    String title() default "";

    /**
     * The value of this property is advisory only, and represents the media type RFC 2046 [RFC2046],
       that is expected to be returned when fetching this resource.
     */
    String mediaType() default "application/json";
}
