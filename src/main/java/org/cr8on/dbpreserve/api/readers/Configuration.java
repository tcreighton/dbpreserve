package org.cr8on.dbpreserve.api.readers;

import org.apache.commons.configuration.ConfigurationException;
import org.cr8on.dbpreserve.impl.XMLConfigurationImpl;

import java.io.File;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 2/11/13
 * Time: 8:48 PM
 * Configuration is a much simplified view of a list of name:value pairs.
 * Any Connector can receive a Configuration and pull out what it needs.
 * Only a few names are defined for all Connectors.  These are actually
 * identified by values of an Enum.
 *
 * Configuration differs from some common configuration types in that
 * there is no public method to set values.  The expectation is that
 * value setting is a private function of the implementation.  Typically
 * it just means that the implementation will read a config file and
 * create a structure to hold the values it passes back.
 */
public interface Configuration {

    public enum Items {USER_NAME, PASSWORD, CONNECTION_STRING, DATABASE_NAME, SCHEMA_NAME}

    public String getValue (String name);
    public String getValue (String name, String defaultValue);

    public String getValue (Items item);
    public String getValue (Items item, String defaultValue);

    public String getUserName ();
    public String getPassword ();
    public String getConnectionString ();
    public String getDatabaseName ();
    public String getSchemaName ();

    public class Factory {
        public static Configuration getConfiguration (String fileName) throws ConfigurationException {
            return new XMLConfigurationImpl(fileName);
        }

        public static Configuration getConfiguration (File file) throws ConfigurationException {
            return new XMLConfigurationImpl(file);
        }

        public static Configuration getConfiguration (URL url) throws ConfigurationException {
            return new XMLConfigurationImpl(url);
        }
    }

}
