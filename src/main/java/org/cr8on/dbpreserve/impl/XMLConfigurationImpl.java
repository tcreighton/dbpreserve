
package org.cr8on.dbpreserve.impl;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 2/11/13
 * Time: 8:58 PM
 * Primary Configuration implementation.
 */


import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLPropertiesConfiguration;
import org.cr8on.dbpreserve.api.readers.Configuration;

import java.io.File;
import java.net.URL;



public class XMLConfigurationImpl implements Configuration {

    private XMLPropertiesConfiguration xmlProps;


    public XMLConfigurationImpl(String fileName)  throws ConfigurationException {
        xmlProps = new XMLPropertiesConfiguration(fileName);
    }

    public XMLConfigurationImpl(File file)  throws ConfigurationException {
        xmlProps = new XMLPropertiesConfiguration(file);
    }

    public XMLConfigurationImpl(URL url)  throws ConfigurationException {
        xmlProps = new XMLPropertiesConfiguration(url);
    }

    public String getValue (String name) {
        return (xmlProps != null) ? xmlProps.getString(name) : null;
    }

    public String getValue (String name, String defaultValue) {
        return (xmlProps != null) ? xmlProps.getString(name, defaultValue) : null;
    }

    public String getValue (Configuration.Items item) {
        switch (item) {
            case USER_NAME:
                return this.getValue("userName");

            case PASSWORD:
                return this.getValue("password");

            case CONNECTION_STRING:
                return this.getValue("connectionString");

            case DATABASE_NAME:
                return this.getValue("databaseName");

            case SCHEMA_NAME:
                return this.getValue("schemaName");

            default:
                return null;
        }
    }

    public String getValue (Configuration.Items item, String defaultValue) {
        String s = this.getValue(item);

        return (null != s) ? s : defaultValue;
    }

    public String getUserName () {
        return this.getValue(Items.USER_NAME);
    }

    public String getPassword () {
        return this.getValue(Items.PASSWORD);
    }

    public String getConnectionString () {
        return this.getValue(Items.CONNECTION_STRING);
    }

    public String getDatabaseName () {
        return this.getValue(Items.DATABASE_NAME);
    }

    public String getSchemaName () {
        return this.getValue(Items.SCHEMA_NAME);
    }
}
