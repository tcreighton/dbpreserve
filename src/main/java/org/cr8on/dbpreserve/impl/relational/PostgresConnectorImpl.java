package org.cr8on.dbpreserve.impl.relational;

import org.cr8on.dbpreserve.api.readers.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 4/12/13
 * Time: 1:15 AM
 */
public class PostgresConnectorImpl extends AnsiConnectorImpl {

    public PostgresConnectorImpl (Configuration configuration) throws NullPointerException {

        super(configuration);


    }

    @Override
    protected PostgresEntityStoreImpl getEntityStore (String entityStoreName) {
        PostgresEntityStoreImpl entityStore = new PostgresEntityStoreImpl(this, entityStoreName);

        return entityStore;
    }

}
