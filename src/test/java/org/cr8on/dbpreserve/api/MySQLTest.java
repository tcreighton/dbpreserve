package org.cr8on.dbpreserve.api;

import org.apache.commons.configuration.ConfigurationException;
import org.cr8on.dbpreserve.api.readers.Configuration;
import org.cr8on.dbpreserve.api.readers.Connector;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 3/18/13
 * Time: 1:46 PM
 */
public class MySQLTest extends RelationalTest {

    public Configuration getConfiguration () throws ConfigurationException{
        Configuration config;

        try {
            config = Configuration.Factory.getConfiguration("MySQL-Chinook.xml");

        } catch (ConfigurationException e) {
            ConfigurationException e2 = new ConfigurationException("Could not load config file MySQL-Chinook.xml.", e);
            throw e2;
        }
        return config;
    }

    public Connector getConnection (Configuration configuration) {
        return Connector.Factory.getMySQLInstance(configuration);
    }

    @Test
    @Override
    public void fullExtractionTest() {
        super.fullExtractionTest();
    }

}