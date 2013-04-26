package org.cr8on.dbpreserve.api;

import org.apache.commons.configuration.ConfigurationException;
import org.cr8on.dbpreserve.api.readers.*;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 2/13/13
 * Time: 2:55 AM
 */
public abstract class RelationalTest {

    public abstract Configuration getConfiguration () throws ConfigurationException;
    public abstract Connector getConnection (Configuration configuration);

    @Test
    public void fullExtractionTest() {
        Configuration configuration = null;
        Connector connector;
        String outputFileName;
        PrintStream outputStream;

        outputFileName = System.getenv("OUTPUT");

        if (null != outputFileName) {
            try {
                outputStream = new PrintStream(outputFileName);
            } catch (FileNotFoundException e){
                System.out.println("Error opening file.  Using standard out.");
                System.out.println("Error message: " + e.toString());
                outputStream = System.out;
            }
        } else {
            outputStream = System.out;
        }


        try {
            configuration = this.getConfiguration();

        } catch (ConfigurationException e) {
            outputStream.println(e);
            assertTrue(false);
        }

        connector = this.getConnection(configuration);

        assertNotNull(connector);

        if (! connector.isConnected()) {
            assertTrue(connector.connect());
        }

        assertNull(connector.getLastError());

        if (connector.isConnected()) {
            Set<EntityStore> entityStores = connector.getEntityStoreSet();

            assertNull(connector.getLastError());
            assertNotNull(entityStores);


            if (System.getenv("DISPLAY").equals("TRUE")) {
                Set<AttributeDescriptor> attributeDescriptorSet;
                Set<KeyDescriptor> keyDescriptorSet;

                outputStream.println("Tables:\n");

                for (EntityStore entityStore : entityStores) {

                    assertNotNull(entityStore);

                    attributeDescriptorSet = entityStore.getAttributeDescriptors();

                    assertNotNull(attributeDescriptorSet);

                    assertNull(connector.getLastError());

                    keyDescriptorSet = entityStore.getKeyDescriptors();

                    outputStream.print(entityStore.getName());
                    outputStream.print(" has ");
                    outputStream.print(entityStore.getEntityCount());
                    outputStream.println(" rows.");

                    for (AttributeDescriptor a : attributeDescriptorSet) {
                        outputStream.print("\tattribute: ");
                        outputStream.print("name: " + a.getName());
                        for (int i = 0; i < 20 - a.getName().length(); i++) {
                            outputStream.print(" ");
                        }
                        outputStream.print("position: " + a.getOrdinalPosition());
                        outputStream.print(a.getOrdinalPosition() < 10 ? "  " : " ");
                        outputStream.print("type: " + a.getAttributeType());
                        for (int i = 0; i < 12 - a.getAttributeType().toString().length(); i++) {
                            outputStream.print(" ");
                        }
                        outputStream.print("native type: " + a.getNativeType());
                        for (int i = 0; i < 15 - a.getNativeType().length(); i++) {
                            outputStream.print(" ");
                        }
                        outputStream.print("max chars: " + a.getMaxCharacterLength() + "\t");
                        outputStream.print("numeric precision: " + a.getNumericPrecision() + "\t");
                        outputStream.print("numeric scale: " + a.getNumericScale() + "\t");
                        outputStream.println(" ");
                    }

                    outputStream.print("\nKey count: ");
                    outputStream.println(keyDescriptorSet.size());
                    for (KeyDescriptor kd : keyDescriptorSet) {
                        outputStream.print("Name: " + kd.getName());
                        outputStream.println(" Type: " + kd.getKeyType());
                        outputStream.println("Attributes:");
                        for (int i = 0; i < kd.getColumnCount(); i++) {
                            outputStream.println("\t" + kd.getKeyColumnDescriptor(i).getColumnName());
                        }
                        outputStream.println("");
                    }

                    if (System.getenv("DISPLAY_DATA").equals("TRUE")) {
                        Iterator<Entity> entityIterator = entityStore.iterator();

                        assertNotNull(entityIterator);

                        outputStream.println("\nData:\n");
                        outputStream.println("<entityStore name=\"" + entityStore.getName() +
                                                "\" count=\"" + entityStore.getEntityCount() + "\">");
                        outputStream.println("  <entities>");

                        while (entityIterator.hasNext()) {
                            Entity entity = entityIterator.next();
                            Set<AttributeDescriptor> attributeDescriptors = entityStore.getAttributeDescriptors();

                            if (null != entity) {
                                outputStream.println("    <entity name=\"" + entity.getName() + "\">");


                                for (AttributeDescriptor a : attributeDescriptors) {
                                    outputStream.print("      <attribute name=\"" + a.getName() +"\" type=\"" +
                                                                    a.getAttributeType().toString() + "\">");
                                    outputStream.print(entity.getAttributeValue(a.getName()));
                                    outputStream.println("</attribute>");
                                }

                                outputStream.println("    </entity>");
                            }
                        }
                        outputStream.println("  </entities>");
                        outputStream.println("</entityStore>");
                        outputStream.println("\n\n");
                    }

                }
            }
        }
    }
}
