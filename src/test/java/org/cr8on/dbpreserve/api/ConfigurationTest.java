package org.cr8on.dbpreserve.api;

import org.apache.commons.configuration.ConfigurationException;
import org.cr8on.dbpreserve.api.readers.Configuration;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 2/12/13
 * Time: 6:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigurationTest {

    @Test
    public void testXMLConfig () {
        String fileName = "XMLConfigTest-1.xml";
        URL url = null;
        File file = null;
        String s1, s2;
        Configuration config = null;

        file = new File(fileName);

        try {
            url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            System.out.println(e);
        }

        // Test config based on file name
        try {
            config = Configuration.Factory.getConfiguration(fileName);

            assertNotNull(config);

        } catch (ConfigurationException e) {
            System.out.println(e);
        }

        s2 = config.getValue("password");
        assertNotNull(s2);


        s1 = config.getPassword();
        assertNotNull(s1);

        assertTrue(s1.equals(s2));

        s2 = config.getValue("userName");
        assertNotNull(s2);

        s1 = config.getUserName();
        assertNotNull(s1);

        assertTrue(s2.equals(s1));

        s1 = config.getValue("connectionString");
        assertNotNull(s1);

        assertTrue(s1.equals("jdbc:mysql://localhost/test"));

        s2 = config.getValue("notThere", "someValue");
        assertNotNull(s2);

        assertTrue(s2.equals("someValue"));

        config = null;
        s1 = s2 = null;

        // Test config based on File
        try {
            config = Configuration.Factory.getConfiguration(file);

            assertNotNull(config);

        } catch (ConfigurationException e) {
            System.out.println(e);
        }

        s2 = config.getValue("password");
        assertNotNull(s2);


        s1 = config.getPassword();
        assertNotNull(s1);

        assertTrue(s1.equals(s2));

        s1 = config.getPassword();
        assertNotNull(s1);

        assertTrue(s1.equals(s2));

        s2 = config.getValue("userName");
        assertNotNull(s2);

        s1 = config.getUserName();
        assertNotNull(s1);

        assertTrue(s2.equals(s1));

        s1 = config.getValue("connectionString");
        assertNotNull(s1);

        assertTrue(s1.equals("jdbc:mysql://localhost/test"));

        s2 = config.getValue("notThere", "someValue");
        assertNotNull(s2);

        assertTrue(s2.equals("someValue"));

        config = null;
        s1 = s2 = null;

        // Test config based on URL

        try {
            config = Configuration.Factory.getConfiguration(url);

            assertNotNull(config);

        } catch (ConfigurationException e) {
            System.out.println(e);
        }

        s2 = config.getValue("password");
        assertNotNull(s2);


        s1 = config.getPassword();
        assertNotNull(s1);

        assertTrue(s1.equals(s2));

        s1 = config.getPassword();
        assertNotNull(s1);

        assertTrue(s1.equals(s2));

        s2 = config.getValue("userName");
        assertNotNull(s2);

        s1 = config.getUserName();
        assertNotNull(s1);

        assertTrue(s2.equals(s1));

        s1 = config.getValue("connectionString");
        assertNotNull(s1);

        assertTrue(s1.equals("jdbc:mysql://localhost/test"));

        s2 = config.getValue("notThere", "someValue");
        assertNotNull(s2);

        assertTrue(s2.equals("someValue"));
    }
}
