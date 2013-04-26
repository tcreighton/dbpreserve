package org.cr8on.dbpreserve.impl;

import org.cr8on.dbpreserve.api.readers.Attribute;
import org.cr8on.dbpreserve.api.readers.AttributeDescriptor;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 2/14/13
 * Time: 3:51 PM
 */
public class AttributeImpl implements Attribute {

    private AttributeDescriptor attributeDescriptor;
    private String value = "";

    public AttributeImpl (AttributeDescriptor attributeDescriptor)  throws NullPointerException {
        if (null == attributeDescriptor)
            throw new NullPointerException("Bad attributeDescriptor for AttributeImpl");

        this.attributeDescriptor = attributeDescriptor;
    }

    public org.cr8on.dbpreserve.api.readers.AttributeDescriptor getAttributeDescriptor () {
        return this.attributeDescriptor;
    }

    @Override
    public String getValue () {
        return this.value;
    }

    @Override
    public void setValue (String value) {
        this.value = value;
    }

    @Override
    public String getName () {
        return this.getAttributeDescriptor().getName();
    }

    @Override
    public void setName (String name) {
        this.getAttributeDescriptor().setName(name);
    }

    @Override
    public String getNativeType () {
        return this.getAttributeDescriptor().getNativeType();
    }

    @Override
    public void setNativeType (String nativeType) {
        this.getAttributeDescriptor().setNativeType(nativeType);
    }

    @Override
    public AttributeType getAttributeType() {
        return this.getAttributeDescriptor().getAttributeType();
    }

    @Override
    public void setAttributeType(AttributeType attributeType) {
        this.getAttributeDescriptor().setAttributeType(attributeType);
    }

    @Override
    public int getOrdinalPosition () {
        return this.getAttributeDescriptor().getOrdinalPosition();
    }

    @Override
    public void setOrdinalPosition (int ordinalPosition) {
        this.getAttributeDescriptor().setOrdinalPosition(ordinalPosition);
    }

    @Override
    public int getMaxCharacterLength () {
        return this.getAttributeDescriptor().getMaxCharacterLength();
    }

    @Override
    public void setMaxCharacterLength (int maxCharacterLength) {
        this.getAttributeDescriptor().setMaxCharacterLength(maxCharacterLength);
    }

    @Override
    public int getNumericPrecision () {
        return this.getAttributeDescriptor().getNumericPrecision();
    }

    @Override
    public void setNumericPrecision (int numericPrecision) {
        this.getAttributeDescriptor().setNumericPrecision(numericPrecision);
    }

    @Override
    public int getNumericScale () {
        return this.getAttributeDescriptor().getNumericScale();
    }

    @Override
    public void setNumericScale (int numericScale) {
        this.getAttributeDescriptor().setNumericScale(numericScale);
    }


}
