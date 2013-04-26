package org.cr8on.dbpreserve.impl.relational;

import org.cr8on.dbpreserve.api.readers.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 2/12/13
 * Time: 11:09 PM
 */
public class MySQLConnectorImpl extends AnsiConnectorImpl {


    public MySQLConnectorImpl (Configuration configuration) throws NullPointerException {

        super(configuration);


    }

    @Override
    protected MySQLEntityStoreImpl getEntityStore (String entityStoreName) {
        MySQLEntityStoreImpl entityStore = new MySQLEntityStoreImpl(this, entityStoreName);

        return entityStore;
    }

}
