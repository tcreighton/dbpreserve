package org.cr8on.dbpreserve.impl;

import org.cr8on.dbpreserve.api.readers.KeyColumnDescriptor;
import org.cr8on.dbpreserve.api.readers.KeyDescriptor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 3/18/13
 * Time: 8:20 PM
 */
public class KeyDescriptorImpl implements KeyDescriptor {

    private String name;
    private KeyType keyType;
    private List<KeyColumnDescriptor> keyColumnDescriptors;

    public KeyDescriptorImpl() {
        this.setName("");
        this.setKeyType(KeyType.UNKNOWN);
        this.keyColumnDescriptors = new ArrayList<>(1);
    }

    public KeyDescriptorImpl(String name) {
        this();
        this.setName(name);
    }

    public KeyDescriptorImpl(String name, KeyType keyType) {
        this(name);
        this.setKeyType(keyType);
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
    public KeyType getKeyType () {
        return this.keyType;
    }

    @Override
    public void setKeyType (KeyType keyType) {
        this.keyType = keyType;
    }

    @Override
    public int getColumnCount () {
        return this.keyColumnDescriptors.size();
    }

    @Override
    public void addKeyColumnDescriptor (KeyColumnDescriptor keyColumnDescriptor) {
        this.keyColumnDescriptors.add(keyColumnDescriptor);
    }

    @Override
    public KeyColumnDescriptor getKeyColumnDescriptor (int index) {
        KeyColumnDescriptor keyColumnDescriptor = null;

        if (0 <= index && index < this.keyColumnDescriptors.size()) {
            keyColumnDescriptor = this.keyColumnDescriptors.get(index);
        }

        return keyColumnDescriptor;
    }
}
