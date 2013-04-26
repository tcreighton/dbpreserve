package org.cr8on.dbpreserve.impl.relational;

import org.cr8on.dbpreserve.api.readers.Configuration;
import org.cr8on.dbpreserve.api.readers.Connector;
import org.cr8on.dbpreserve.api.readers.EntityStore;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 2/23/13
 * Time: 1:04 AM
 */
public abstract class RelationalConnectorImpl implements Connector {

    private Configuration configuration = null;
    private Connection connection = null;
    private Exception lastError = null;

    private String connectString;
    private String userName;
    private String password;
    private String databaseName;
    private String schemaName;

    private String entityStoreQuery;

    @Override
    public String getDatabaseName() {
        return this.databaseName;
    }

    @Override
    public Exception getLastError() {
        return this.lastError;
    }

    @Override
    public Set<EntityStore> getEntityStoreSet () {
        Set<EntityStore> entityStoreSet = new LinkedHashSet<EntityStore>(); // LinkedHashSet remembers the order of insert
        Statement stmt;

        if (isConnected()) {
            try {
                stmt = this.getConnection().createStatement();
                if (null != stmt) {
                    ResultSet rs = stmt.executeQuery(this.getEntityStoreQuery());
                    RelationalEntityStoreImpl entityStore;

                    while (rs.next()) {
                        String entityStoreName = extractEntityStoreName(rs);

                        entityStore = this.getEntityStore(entityStoreName);

                        entityStore.setName(entityStoreName);

                        entityStoreSet.add(entityStore);
                    }
                    stmt.close();
                }
            } catch (SQLException e) {
                this.lastError = e;
            }
        }

        return entityStoreSet;
    }

    @Override
    public boolean connect () {

        if (this.isConnected()) {
            this.disconnect();
        }

        try {
//            Class.forName (driverName);
//            this.conn = DriverManager.getConnection(connectString, userName, password);
            this.setConnection(DriverManager.getConnection(this.getConnectString(), this.getUserName(), this.getPassword()));
        } catch (Exception e) {
            this.setLastError(e);
            this.setConnection(null);
        }

        return (null != this.getConnection());

    }

    @Override
    public void disconnect () {

        try {
            if (null != this.getConnection())
                this.getConnection().close();

        } catch (SQLException e) {
            this.setLastError(e);
        }

        this.setConnection(null);
    }

    @Override
    public boolean isConnected () {
        return (null != this.getConnection());
    }

    protected RelationalConnectorImpl (Configuration configuration) {

        this.setConfiguration(configuration);

    }

    protected Configuration getConfiguration () {
        return this.configuration;
    }

    protected  void setConfiguration (Configuration configuration) {
        this.configuration = configuration;
    }

    protected Connection getConnection () {
        return this.connection;
    }

    protected void setConnection (Connection connection) {
        this.connection = connection;
    }

    public void setLastError (Exception lastError) {
        this.lastError = lastError;
    }

    protected String getConnectString () {
        return this.connectString;
    }

    protected void setConnectString (String connectString) {
        this.connectString = connectString;
    }

    protected String getUserName () {
        return this.userName;
    }

    protected void setUserName (String userName) {
        this.userName = userName;
    }

    protected String getPassword () {
        return  this.password;
    }

    protected void setPassword (String password) {
        this.password = password;
    }

    protected void setDatabaseName (String databaseName) {
        this.databaseName = databaseName;
    }

    protected String getEntityStoreQuery () {
        return this.entityStoreQuery;
    }

    protected void setEntityStoreQuery(String entityStoreQuery) {
        this.entityStoreQuery = entityStoreQuery;
    }

    protected String getSchemaName () {
        return this.schemaName;
    }

    protected void  setSchemaName (String schemaName) {
        this.schemaName = schemaName;
    }

    // get the name of the table (entityStore)
    abstract protected String extractEntityStoreName (ResultSet resultSet);
    abstract protected RelationalEntityStoreImpl getEntityStore (String entityStoreName);


}
