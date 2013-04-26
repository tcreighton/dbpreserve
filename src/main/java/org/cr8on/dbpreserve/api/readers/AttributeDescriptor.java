package org.cr8on.dbpreserve.api.readers;

import org.cr8on.dbpreserve.impl.AttributeDescriptorImpl;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 2/23/13
 * Time: 12:42 AM
 *
 * The attributes or columns of a table, or fields of a document can have the
 * following characteristics.  However, not all databases will define all of
 * these characteristics.
 *
 * Name: the name of the column or field.
 * Native type: the string representation of the data type as defined by the database system.
 * Attribute type: a mapping of the native type into the set of canonical types defined in an enum.
 * Ordinal position: the column or field number based on 1.
 * Maximum character length: the maximum number of characters supported by the attribute (for character types)
 * Numeric precision: the precision of a floating point number type.
 * Numeric scale: a scale factor for numeric types.
 *
 */
public interface AttributeDescriptor {


    public enum AttributeType {STRING, NUMBER, DOUBLE, INT, BIGINT, UNSUPPORTED}

    public String getName ();
    public void setName (String name);

    public String getNativeType ();
    public void setNativeType (String nativeType);

    public AttributeType getAttributeType();
    public void setAttributeType(AttributeType attributeType);

    public int getOrdinalPosition ();
    public void setOrdinalPosition (int ordinalPosition);

    public int getMaxCharacterLength ();
    public void setMaxCharacterLength (int maxCharacterLength);

    public int getNumericPrecision ();
    public void setNumericPrecision (int numericPrecision);

    public int getNumericScale ();
    public void setNumericScale (int numericScale);

    public class Factory {
        public static AttributeDescriptor getInstance () {
            return new AttributeDescriptorImpl();
        }
    }
}
