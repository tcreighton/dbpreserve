package org.cr8on.dbpreserve.api.readers;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 3/21/13
 * Time: 3:43 AM
 */
public interface KeyDescriptor {

    public enum KeyType {PRIMARY, FOREIGN, UNKNOWN};

    public String getName ();
    public void setName (String name);
    public KeyType getKeyType ();
    public void setKeyType (KeyType keyType);
    public int getColumnCount ();
    public void addKeyColumnDescriptor (KeyColumnDescriptor keyColumnDescriptor);
    public KeyColumnDescriptor getKeyColumnDescriptor (int index);

}
