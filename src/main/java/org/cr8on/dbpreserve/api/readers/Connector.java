package org.cr8on.dbpreserve.api.readers;

import org.cr8on.dbpreserve.impl.relational.MySQLConnectorImpl;
import org.cr8on.dbpreserve.impl.relational.PostgresConnectorImpl;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 2/11/13
 * Time: 8:14 PM
 * The Connector interface abstracts how dbpreserve connects to a particular database.
 * A given database might be relational or No-SQL, or just files, but the Connector
 * provides a common way for impl code to talk to the database.  Any implementation
 * of Connector might be specific to a particular database, DBMS, or set of files.
 * When it is instantiated, the Connector implementation receives a configuration
 * object from which that implementation derives whatever information it needs to
 * do its job.  So that means that a Connector implementation might be generic
 * enough to provide access to any relational DBMS, a particular DBMS, or a particular
 * database hosted within a DBMS instance.
 */
public interface Connector {

    public boolean connect ();
    public void disconnect ();
    public Exception getLastError ();
    public boolean isConnected ();
    public String getDatabaseName ();
    public Set<EntityStore> getEntityStoreSet ();

    public class Factory {
        public static Connector getMySQLInstance (Configuration config) {
            return new MySQLConnectorImpl(config);
        }
        public static Connector getPostgresInstance (Configuration config) {
            return new PostgresConnectorImpl(config);
        }
    }
}
