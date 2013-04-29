package org.cr8on.dbpreserve.impl.relational;

import org.cr8on.dbpreserve.api.readers.AttributeDescriptor;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 4/12/13
 * Time: 1:16 AM
 */
public class PostgresEntityStoreImpl extends AnsiEntityStoreImpl {

    private enum PostgresColumnType {
        // numeric types
        BIT, TINYINT, INT, INTEGER, BOOL, BOOLEAN, SMALLINT, MEDIUMINT, BIGINT, DEC, DECIMAL, FIXED, NUMERIC, FLOAT, DOUBLE,
        // date types
        DATE, DATETIME, TIME, TIMESTAMP, YEAR,
        // string types
        CHAR, VARCHAR, TEXT, TINYTEXT, MEDIUMTEXT, LONGTEXT, SET
    }

    public PostgresEntityStoreImpl(PostgresConnectorImpl connector, String name) {

        super(connector, name);
    }

    @Override
    /***
     * This maps the native types of Postgres to a canonical type.  Doing this allows multiple database types to
     * map data into our common engine.  In addition to the type mapping there may be a need to do data mapping.
     * For example, Postgres supports a date type that is year only called YEAR.  This can either be a four digit
     * or two digit year.  If two digit, it must be in the range 70-69 to represent the dates 1970-2069.  This
     * is clearly an interesting mapping and must happen at data retrieval time.
     *
     * Postgres supports three categories of data types.  These are partitioned in the code to make it clear.
     */
    protected AttributeDescriptor.AttributeType mapNativeTypeToCanonicalType (String nativeTypeName) {
        AttributeDescriptor.AttributeType attributeType = AttributeDescriptor.AttributeType.UNSUPPORTED;
        String testType = (null != nativeTypeName) ? nativeTypeName.toUpperCase() : "";

        if (! testType.contains(PostgresColumnType.SET.toString())) { // any use of set is currently unsupported
/**
 * test numbers
 *         BIT, TINYINT, INT, INTEGER, BOOL, BOOLEAN, SMALLINT, MEDIUMINT, BIGINT, DEC, DECIMAL, FIXED, NUMERIC, FLOAT, DOUBLE,

 */
            if (testType.contains(PostgresColumnType.BIT.toString()) ||
                    testType.contains(PostgresColumnType.BIGINT.toString()))
                attributeType = AttributeDescriptor.AttributeType.BIGINT;
            else if (testType.contains(PostgresColumnType.BOOL.toString()) ||
                    testType.contains(PostgresColumnType.BOOLEAN.toString()) ||
                    testType.contains(PostgresColumnType.INT.toString()) ||
                    testType.contains(PostgresColumnType.MEDIUMINT.toString()) ||
                    testType.contains(PostgresColumnType.SMALLINT.toString()) ||
                    testType.contains(PostgresColumnType.TINYINT.toString()) ||
                    testType.contains(PostgresColumnType.INTEGER.toString()))
                attributeType = AttributeDescriptor.AttributeType.INT;
            else if (testType.contains(PostgresColumnType.DEC.toString()) ||
                    testType.contains(PostgresColumnType.DECIMAL.toString()) ||
                    testType.contains(PostgresColumnType.FIXED.toString()) ||
                    testType.contains(PostgresColumnType.FLOAT.toString()))
                attributeType = AttributeDescriptor.AttributeType.NUMBER;
            else if (testType.contains(PostgresColumnType.NUMERIC.toString()) ||
                    testType.contains(PostgresColumnType.DOUBLE.toString()))
                attributeType = AttributeDescriptor.AttributeType.DOUBLE;
/**
 * test strings
 *           CHAR, VARCHAR, TEXT, TINYTEXT, MEDIUMTEXT, LONGTEXT, SET
 */
            else if (testType.contains(PostgresColumnType.CHAR.toString()) ||
                    testType.contains(PostgresColumnType.VARCHAR.toString()) ||
                    testType.contains(PostgresColumnType.TEXT.toString()) ||
                    testType.contains(PostgresColumnType.TINYTEXT.toString()) ||
                    testType.contains(PostgresColumnType.MEDIUMTEXT.toString()) ||
                    testType.contains(PostgresColumnType.LONGTEXT.toString()))
                attributeType = AttributeDescriptor.AttributeType.STRING;

/**
 * test dates
 *          DATE, DATETIME, TIME, TIMESTAMP, YEAR
 *
 *          Just map to string for now.
 */
            else if (testType.contains(PostgresColumnType.DATE.toString()) ||
                    testType.contains(PostgresColumnType.DATETIME.toString()) ||
                    testType.contains(PostgresColumnType.TIME.toString()) ||
                    testType.contains(PostgresColumnType.TIMESTAMP.toString()) ||
                    testType.contains(PostgresColumnType.YEAR.toString()))
                attributeType = AttributeDescriptor.AttributeType.STRING;
        }

        return attributeType;
    }

    /***
     * This overrides what's in AnsiEntityStoreImpl because PostgreSQL
     * implements information_schema.tables without table_rows.
     *
     * @return count of rows in the entity store
     */
    @Override
    protected String getEntityCountQuery () {
        StringBuilder s = new StringBuilder();

        s.  append("select count (*) ").
                append("from \"").
                append(this.getName()).
                append("\" ");

        return s.toString();
    }

    @Override
    protected String getAllEntitiesQuery () {
        StringBuilder s = new StringBuilder();

        s.  append("select * ").
                append("from \"").
                append(this.getName()).
                append("\"");

        return s.toString();
    }


}
