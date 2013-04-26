package org.cr8on.dbpreserve.api.readers;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 2/14/13
 * Time: 3:38 PM
 * An Attribute contains the data corresponding to a field in a row.
 *
 */
public interface Attribute extends AttributeDescriptor{

    public String getValue ();  // all types map to string or are unsupported
    public void setValue (String value);


}
