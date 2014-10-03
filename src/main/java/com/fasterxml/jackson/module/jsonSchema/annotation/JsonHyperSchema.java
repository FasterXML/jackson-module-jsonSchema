package com.fasterxml.jackson.module.jsonSchema.annotation;

import java.lang.annotation.*;

/**
 * Created by mavarazy on 4/21/14.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface JsonHyperSchema {

    /**
     * This attribute is a URI that defines what the instance's URI MUST
     start with in order to validate.  The value of the "pathStart"
     attribute MUST be resolved as per RFC 3986, Sec 5 [RFC3986], and is
     relative to the instance's URI.

     When multiple schemas have been referenced for an instance, the user
     agent can determine if this jsonSchema is applicable for a particular
     instance by determining if the URI of the instance begins with the
     the value of the "pathStart" attribute.  If the URI of the instance
     does not start with this URI, or if another jsonSchema specifies a
     starting URI that is longer and also matches the instance, this
     jsonSchema SHOULD NOT be applied to the instance.  Any jsonSchema that does
     not have a pathStart attribute SHOULD be considered applicable to all
     the instances for which it is referenced.
     */
    String pathStart() default "";

    /**
     * 6.2.1.  slash-delimited fragment resolution

     With the slash-delimited fragment resolution protocol, the fragment
     identifier is interpreted as a series of property reference tokens
     that start with and are delimited by the "/" character (\x2F).  Each
     property reference token is a series of unreserved or escaped URI
     characters.  Each property reference token SHOULD be interpreted,
     starting from the beginning of the fragment identifier, as a path
     reference in the target JSON structure.  The final target value of
     the fragment can be determined by starting with the root of the JSON
     structure from the representation of the resource identified by the
     pre-fragment URI.  If the target is a JSON object, then the new
     target is the value of the property with the name identified by the
     next property reference token in the fragment.  If the target is a
     JSON array, then the target is determined by finding the item in
     array the array with the index defined by the next property reference
     token (which MUST be a number).  The target is successively updated
     for each property reference token, until the entire fragment has been
     traversed.

     Property names SHOULD be URI-encoded.  In particular, any "/" in a
     property name MUST be encoded to avoid being interpreted as a
     property delimiter.

     For example, for the following JSON representation:

     {
     "foo":{
     "anArray":[
     {"prop":44}
     ],
     "another prop":{
     "baz":"A string"
     }
     }
     }

     The following fragment identifiers would be resolved:

     fragment identifier      resolution
     -------------------      ----------
     #                        self, the root of the resource itself
     #/foo                    the object referred to by the foo property
     #/foo/another%20prop     the object referred to by the "another prop"
     property of the object referred to by the
     "foo" property
     #/foo/another%20prop/baz the string referred to by the value of "baz"
     property of the "another prop" property of
     the object referred to by the "foo" property
     #/foo/anArray/0          the first object in the "anArray" array

     6.2.2.  dot-delimited fragment resolution

     The dot-delimited fragment resolution protocol is the same as slash-
     delimited fragment resolution protocol except that the "." character
     (\x2E) is used as the delimiter between property names (instead of
     "/") and the path does not need to start with a ".".  For example,
     #.foo and #foo are a valid fragment identifiers for referencing the
     value of the foo propery.
     */

    Link[] links() default {};

}
