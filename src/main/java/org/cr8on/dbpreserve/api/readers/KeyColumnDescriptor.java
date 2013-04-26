package org.cr8on.dbpreserve.api.readers;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 3/21/13
 * Time: 3:18 AM
 */
public class KeyColumnDescriptor {
    private String  keyName;
    private String  columnName;         // name of the column in a key descriptor
    private int     ordinalPosition;    // 1 based position of column in a key descriptor
    private KeyDescriptor.KeyType keyType;  // What type of key is this column part of

    public KeyColumnDescriptor () {
        this.setColumnName("");
        this.setOrdinalPosition(0);
    }

    public KeyColumnDescriptor (String columnName) {
        this();
        this.setColumnName(columnName);
    }

    public KeyColumnDescriptor (String columnName, int ordinalPosition) {
        this(columnName);
        this.setOrdinalPosition(ordinalPosition);
    }

    public KeyColumnDescriptor (String keyName, String columnName, int ordinalPosition) {
        this(columnName, ordinalPosition);
        this.setKeyName(keyName);
    }

    public KeyColumnDescriptor (String keyName, String columnName, int ordinalPosition, KeyDescriptor.KeyType keyType) {
        this(keyName, columnName, ordinalPosition);
        this.setKeyType(keyType);
    }

    public String getKeyName () {
        return this.keyName;
    }

    public void setKeyName (String keyName) {
        this.keyName = keyName;
    }

    public String getColumnName () {
        return this.columnName;
    }

    public void setColumnName (String columnName) {
        this.columnName = columnName;
    }

    public int getOrdinalPosition () {
        return this.ordinalPosition;
    }

    public void setOrdinalPosition (int ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    public KeyDescriptor.KeyType getKeyType () {
        return this.keyType;
    }

    public void setKeyType (KeyDescriptor.KeyType keyType) {
        this.keyType = keyType;
    }
}
