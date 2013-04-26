package org.cr8on.dbpreserve.impl.relational;

import org.cr8on.dbpreserve.api.readers.Configuration;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 4/11/13
 * Time: 10:03 PM
 */
public abstract class AnsiConnectorImpl extends RelationalConnectorImpl {

    public AnsiConnectorImpl (Configuration configuration) throws NullPointerException {

        super(configuration);

        StringBuilder s = new StringBuilder();

        if (null == configuration) {
            NullPointerException e = new NullPointerException("Null configuration.");
            throw e;
        }

        this.setConfiguration(configuration);

        this.setConnectString(configuration.getConnectionString());
        this.setUserName(configuration.getUserName());
        this.setPassword(configuration.getPassword());
        this.setDatabaseName(configuration.getDatabaseName());
        this.setSchemaName(configuration.getSchemaName());

        s.  append("select table_name from information_schema.tables ").
                append("where table_schema = '").
                append(this.getSchemaName()).
                append("' and ").
                append("table_type = 'BASE TABLE' ").
                append("order by table_name");

        this.setEntityStoreQuery(s.toString());
    }

    @Override
    protected String extractEntityStoreName (ResultSet resultSet) {
        String entityStoreName = "";

        try {
            entityStoreName = resultSet.getString("TABLE_NAME");

        } catch (SQLException e) {
            this.setLastError(e);
        }

        return entityStoreName;
    }

}
