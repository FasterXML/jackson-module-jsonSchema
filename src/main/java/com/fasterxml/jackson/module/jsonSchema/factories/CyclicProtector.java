package com.fasterxml.jackson.module.jsonSchema.factories;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * This class allows the monitoring of visited properties during schema generation to avoid infinite loops on cyclic references.
 * 
 * @author Morten Haraldsen
 */
public class CyclicProtector
{
    private static final ThreadLocal<Set<BeanProperty>> visited = new ThreadLocal<Set<BeanProperty>>()
    {
        @Override 
        protected Set<BeanProperty> initialValue()
        {
            return new HashSet<BeanProperty>();
        }
    };

    public static void optionalProperty(ObjectVisitor visitor, BeanProperty prop) throws JsonMappingException
    {
        final Set<BeanProperty> visitedTypes = visited.get();
        if (! visitedTypes.contains(prop))
        {
            visitedTypes.add(prop);
            visitor.getSchema().putOptionalProperty(prop, visitor.propertySchema(prop));
        }
    }

    public static void property(ObjectVisitor visitor, BeanProperty prop) throws JsonMappingException
    {
        final Set<BeanProperty> visitedTypes = visited.get();
        if (! visitedTypes.contains(prop))
        {
            visitedTypes.add(prop);
            visitor.getSchema().putProperty(prop, visitor.propertySchema(prop));
        }
    }

    public static void reset()
    {
        visited.remove();
    }
}
