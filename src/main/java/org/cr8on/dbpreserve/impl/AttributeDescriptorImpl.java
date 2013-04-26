package org.cr8on.dbpreserve.impl;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 2/25/13
 * Time: 10:58 PM
 */
public class AttributeDescriptorImpl implements org.cr8on.dbpreserve.api.readers.AttributeDescriptor {

    private String name, nativeType;
    private int ordinalPosition, maxCharacterLength, numericPrecision, numericScale;
    private AttributeType attributeType;

    public AttributeDescriptorImpl() {
        this.setAttributeType(AttributeType.UNSUPPORTED);
        this.setName("Unknown");
        this.setNativeType("Unknown");
        this.setOrdinalPosition(-1);
        this.setMaxCharacterLength(-1);
        this.setNumericPrecision(-1);
        this.setNumericScale(-1);
    }

    @Override
    public String getName () {
        return this.name;
    }

    @Override
    public void setName (String name) {
        this.name = name;
    }

    @Override
    public String getNativeType () {
        return this.nativeType;
    }

    @Override
    public void setNativeType (String nativeType) {
        this.nativeType = nativeType;
    }

    @Override
    public AttributeType getAttributeType() {
        return this.attributeType;
    }

    @Override
    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    @Override
    public int getOrdinalPosition () {
        return this.ordinalPosition;
    }

    @Override
    public void setOrdinalPosition (int ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    @Override
    public int getMaxCharacterLength () {
        return this.maxCharacterLength;
    }

    @Override
    public void setMaxCharacterLength (int maxCharacterLength) {
        this.maxCharacterLength = maxCharacterLength;
    }

    @Override
    public int getNumericPrecision () {
        return this.numericPrecision;
    }

    @Override
    public void setNumericPrecision (int numericPrecision) {
        this.numericPrecision = numericPrecision;
    }

    @Override
    public int getNumericScale () {
        return this.numericScale;
    }

    @Override
    public void setNumericScale (int numericScale) {
        this.numericScale = numericScale;
    }

}
