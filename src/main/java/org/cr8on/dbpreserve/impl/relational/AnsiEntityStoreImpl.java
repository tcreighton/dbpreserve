package org.cr8on.dbpreserve.impl.relational;

import org.cr8on.dbpreserve.api.readers.AttributeDescriptor;
import org.cr8on.dbpreserve.api.readers.KeyColumnDescriptor;
import org.cr8on.dbpreserve.api.readers.KeyDescriptor;
import org.cr8on.dbpreserve.impl.EntityImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 4/11/13
 * Time: 9:17 PM
 */
public abstract class AnsiEntityStoreImpl extends RelationalEntityStoreImpl {

    public AnsiEntityStoreImpl(AnsiConnectorImpl connector, String name) {

        this.setConnector(connector);

        this.setName(name);
    }


    @Override
    protected String getAttributeDescriptorQuery () {
        StringBuilder s = new StringBuilder();

        s.  append("select column_name, ordinal_position, data_type, ").
                append("character_maximum_length, numeric_precision, numeric_scale ").
                append("from information_schema.columns ").
                append("where table_schema = '").
                append(this.getConnector().getSchemaName()).
                append("' and table_name = '").
                append(this.getName()).
                append("' order by ordinal_position");

        return s.toString();
    }

    @Override
    protected String getEntityCountQuery () {
        StringBuilder s = new StringBuilder();

        s.  append("select table_rows as count ").
                append("from information_schema.tables ").
                append("where table_schema = '").
                append(this.getConnector().getSchemaName()).
                append("' and table_name = '").
                append(this.getName()).
                append("' ");

        return s.toString();
    }

    @Override
    protected String getKeyDescriptorQuery () {
        StringBuilder s = new StringBuilder();

        s.  append("select ku.table_name, ku.column_name, ku.constraint_name, ku.ordinal_position, tc.constraint_type ").
            append("from information_schema.key_column_usage ku ").
            append("inner join information_schema.table_constraints tc ").
            append("on ku.table_name = tc.table_name and ").
            append("ku.constraint_name = tc.constraint_name and ").
            append("ku.table_name = '%1' ").
            append("order by ku.table_name, tc.constraint_type desc, ku.constraint_name, ku.ordinal_position");

        return s.toString();
    }

    // Since this class understands the query, it has to extract the data from the result set
    // even though the base class executed the query.
    @Override
    protected int extractEntityCount (ResultSet resultSet) {
        int count = -1; // indicates an error.

        try {
            if (resultSet.next())
                count = Integer.decode(resultSet.getString("count"));

        } catch (SQLException e) {
            this.getConnector().setLastError(e);
        }

        return count;
    }

    /**
     * This extractor pulls out one KeyColumnDescriptor from a ResultSet.
     * Something else has to figure out how to create a KeyDescriptor from this information.
     * @param resultSet
     * @return A single KeyColumnDescriptor
     */
    @Override
    protected KeyColumnDescriptor extractKeyColumnDescriptor (ResultSet resultSet) {
        KeyColumnDescriptor keyColumnDescriptor = null;
        String s;

        if (null != resultSet) {
            keyColumnDescriptor = new KeyColumnDescriptor();

            try {
                keyColumnDescriptor.setKeyName(resultSet.getString("constraint_name"));
                keyColumnDescriptor.setColumnName(resultSet.getString("column_name"));
                keyColumnDescriptor.setOrdinalPosition(resultSet.getInt("ordinal_position"));
                s = resultSet.getString("constraint_type");
                if (s.equals("PRIMARY KEY")) {
                    keyColumnDescriptor.setKeyType(KeyDescriptor.KeyType.PRIMARY);
                } else if (s.equals("FOREIGN KEY")) {
                    keyColumnDescriptor.setKeyType((KeyDescriptor.KeyType.FOREIGN));
                } else {
                    keyColumnDescriptor.setKeyType(KeyDescriptor.KeyType.UNKNOWN);
                }
            }   catch (SQLException e) {

            }
        }

        return keyColumnDescriptor;
    }

    @Override
    public EntityImpl extractEntity (ResultSet resultSet) {
        EntityImpl entity;
        Iterator<AttributeDescriptor> attributeDescriptorIterator = this.getAttributeDescriptors().iterator();
        AttributeDescriptor attributeDescriptor;
        String attributeName;
        String attributeValue;

        entity = new EntityImpl(this.getName(), this.getAttributeDescriptors());

        /**
         * Now we have a new entity.  Next we have to
         */

        while (null != attributeDescriptorIterator && attributeDescriptorIterator.hasNext()) {
            attributeDescriptor = attributeDescriptorIterator.next();

            attributeName = "";
            attributeValue = "";

            if (attributeDescriptor.getAttributeType() != AttributeDescriptor.AttributeType.UNSUPPORTED) {
                // For a supported type, we just extract the value as a string.

                attributeName = attributeDescriptor.getName();

                try {
                    attributeValue = resultSet.getString(attributeName);
                } catch (SQLException e) {
                    this.getConnector().setLastError(e);
                    entity = null;
                    attributeDescriptorIterator = null;
                }
            }

            if (! attributeName.equals(""))
                entity.setAttributeValue(attributeName, attributeValue);
        }

        return entity;
    }

    @Override
    protected AttributeDescriptor extractAttributeDescriptor (ResultSet resultSet) {
        // extract the column name, ordinal position,data type, column type, numeric precision, numeric scale
        String columnName = "Unknown", dataType = "Unknown", columnType = "Unknown";
        int ordinalPosition = -1, maxCharacterLength = -1, numericPrecision = -1, numericScale = -1;
        AttributeDescriptor attributeDescriptor = AttributeDescriptor.Factory.getInstance();

        if (null != attributeDescriptor && null != resultSet) {
            try {

                columnName          = resultSet.getString("column_name");
                ordinalPosition     = resultSet.getInt("ordinal_position");
                dataType            = resultSet.getString("data_type");
                maxCharacterLength  = resultSet.getInt("character_maximum_length");
                numericPrecision    = resultSet.getInt("numeric_precision");
                numericScale        = resultSet.getInt("numeric_scale");

            } catch (SQLException e) {
                this.getConnector().setLastError(e);
            }

            attributeDescriptor.setName(columnName);
            attributeDescriptor.setOrdinalPosition(ordinalPosition);
            attributeDescriptor.setAttributeType(this.mapNativeTypeToCanonicalType(dataType));
            attributeDescriptor.setMaxCharacterLength(maxCharacterLength);
            attributeDescriptor.setNumericPrecision(numericPrecision);
            attributeDescriptor.setNumericScale(numericScale);
            attributeDescriptor.setNativeType(dataType);

            attributeDescriptor.setAttributeType(this.mapNativeTypeToCanonicalType(attributeDescriptor.getNativeType()));
        }

        return attributeDescriptor;
    }



}
