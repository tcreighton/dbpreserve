package org.cr8on.dbpreserve.api.readers;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 2/11/13
 * Time: 8:15 PM
 * Each entity will contain a list of Attribute definitions.
 * An Entity is like a Table and an Attribute is like a Column.
 * However, this might also map to a no-sql model where the
 * attributes could contain entities.
 */
public interface Entity {

    public String getName (); // retrieve the name of the Entity.  This is obviously the name of the EntityStore.

    public String getAttributeValue (String attributeName); // Null if unknown name or if unsupported type or if null value.

}
