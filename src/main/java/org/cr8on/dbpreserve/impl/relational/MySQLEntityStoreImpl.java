package org.cr8on.dbpreserve.impl.relational;

import org.cr8on.dbpreserve.api.readers.AttributeDescriptor;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 2/23/13
 * Time: 7:54 PM
 */
public class MySQLEntityStoreImpl extends AnsiEntityStoreImpl {

    private enum MySQLColumnType {
        // numeric types
        BIT, TINYINT, INT, INTEGER, BOOL, BOOLEAN, SMALLINT, MEDIUMINT, BIGINT, DEC, DECIMAL, FIXED, NUMERIC, FLOAT, DOUBLE,
        // date types
        DATE, DATETIME, TIME, TIMESTAMP, YEAR,
        // string types
        CHAR, VARCHAR, TEXT, TINYTEXT, MEDIUMTEXT, LONGTEXT, SET
    }

    public MySQLEntityStoreImpl (MySQLConnectorImpl connector, String name) {

        super(connector, name);
    }

    @Override
    /***
     * This maps the native types of MySQL to a canonical type.  Doing this allows multiple database types to
     * map data into our common engine.  In addition to the type mapping there may be a need to do data mapping.
     * For example, MySQL supports a date type that is year only called YEAR.  This can either be a four digit
     * or two digit year.  If two digit, it must be in the range 70-69 to represent the dates 1970-2069.  This
     * is clearly an interesting mapping and must happen at data retrieval time.
     *
     * MySQL supports three categories of data types.  These are partitioned in the code to make it clear.
     */
    protected AttributeDescriptor.AttributeType mapNativeTypeToCanonicalType (String nativeTypeName) {
        AttributeDescriptor.AttributeType attributeType = AttributeDescriptor.AttributeType.UNSUPPORTED;
        String testType = (null != nativeTypeName) ? nativeTypeName.toUpperCase() : "";

        if (! testType.contains(MySQLColumnType.SET.toString())) { // any use of set is currently unsupported
/**
 * test numbers
 *         BIT, TINYINT, INT, INTEGER, BOOL, BOOLEAN, SMALLINT, MEDIUMINT, BIGINT, DEC, DECIMAL, FIXED, NUMERIC, FLOAT, DOUBLE,

 */
            if (testType.contains(MySQLColumnType.BIT.toString()) ||
                testType.contains(MySQLColumnType.BIGINT.toString()))
                attributeType = AttributeDescriptor.AttributeType.BIGINT;
            else if (testType.contains(MySQLColumnType.BOOL.toString()) ||
                     testType.contains(MySQLColumnType.BOOLEAN.toString()) ||
                     testType.contains(MySQLColumnType.INT.toString()) ||
                     testType.contains(MySQLColumnType.MEDIUMINT.toString()) ||
                     testType.contains(MySQLColumnType.SMALLINT.toString()) ||
                     testType.contains(MySQLColumnType.TINYINT.toString()) ||
                     testType.contains(MySQLColumnType.INTEGER.toString()))
                    attributeType = AttributeDescriptor.AttributeType.INT;
            else if (testType.contains(MySQLColumnType.DEC.toString()) ||
                     testType.contains(MySQLColumnType.DECIMAL.toString()) ||
                     testType.contains(MySQLColumnType.FIXED.toString()) ||
                     testType.contains(MySQLColumnType.FLOAT.toString()))
                    attributeType = AttributeDescriptor.AttributeType.NUMBER;
            else if (testType.contains(MySQLColumnType.NUMERIC.toString()) ||
                     testType.contains(MySQLColumnType.DOUBLE.toString()))
                    attributeType = AttributeDescriptor.AttributeType.DOUBLE;
/**
 * test strings
 *           CHAR, VARCHAR, TEXT, TINYTEXT, MEDIUMTEXT, LONGTEXT, SET
 */
            else if (testType.contains(MySQLColumnType.CHAR.toString()) ||
                     testType.contains(MySQLColumnType.VARCHAR.toString()) ||
                     testType.contains(MySQLColumnType.TEXT.toString()) ||
                     testType.contains(MySQLColumnType.TINYTEXT.toString()) ||
                     testType.contains(MySQLColumnType.MEDIUMTEXT.toString()) ||
                     testType.contains(MySQLColumnType.LONGTEXT.toString()))
                    attributeType = AttributeDescriptor.AttributeType.STRING;
        }


        return attributeType;
    }


}
