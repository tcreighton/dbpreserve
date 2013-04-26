package org.cr8on.dbpreserve.impl;

import org.cr8on.dbpreserve.api.readers.Attribute;
import org.cr8on.dbpreserve.api.readers.AttributeDescriptor;
import org.cr8on.dbpreserve.api.readers.Entity;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 3/1/13
 * Time: 10:54 PM
 */
public class EntityImpl implements Entity {
    private Set<AttributeDescriptor> attributeDescriptors;
    private Set<Attribute> attributes = new LinkedHashSet<>();
    private String name;

    public EntityImpl(String name, Set<AttributeDescriptor> attributeDescriptors) {
        AttributeImpl attribute;
        this.setName(name);
        this.setAttributeDescriptors(attributeDescriptors);

        // Now create the attributes with default value.

        for (AttributeDescriptor attributeDescriptor : attributeDescriptors) {
            attribute = new AttributeImpl(attributeDescriptor);

            attribute.setName(attributeDescriptor.getName());
            attribute.setValue(""); // default value to be changed later explicitly
            this.getAttributes().add(attribute);
        }

    }

/*
    protected Set<AttributeDescriptor> getAttributeDescriptors () {
        return this.attributeDescriptors;
    }
*/


    protected void setAttributeDescriptors (Set<AttributeDescriptor> attributeDescriptors) {
        this.attributeDescriptors = attributeDescriptors;
    }


    @Override
    public String getName () {
        return this.name;
    }

    public void setName (String name) {
        this.name = name;
    }

    @Override
    public String getAttributeValue (String name) {
        String value = null;
        Iterator<Attribute> attributeIterator = this.getAttributes().iterator();
        Attribute attribute;

        while (null == value && attributeIterator.hasNext()) {
            attribute = attributeIterator.next();
            if (attribute.getName().equals(name)) {
                value = attribute.getValue();
            }
        }

        return value;
    }

    public void setAttributeValue (String name, String value) {
        Iterator<Attribute> attributeIterator = this.getAttributes().iterator();
        Attribute attribute;

        while (attributeIterator.hasNext()) {
            attribute = attributeIterator.next();
            if (attribute.getName().equals(name)) {
                attribute.setValue(value);
                return;
            }
        }
    }

    protected Set<Attribute> getAttributes() {
        return this.attributes;
    }
}
