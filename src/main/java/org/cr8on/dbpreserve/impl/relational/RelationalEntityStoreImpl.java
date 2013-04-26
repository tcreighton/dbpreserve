package org.cr8on.dbpreserve.impl.relational;

import org.cr8on.dbpreserve.api.readers.*;
import org.cr8on.dbpreserve.impl.KeyDescriptorImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 2/23/13
 * Time: 9:00 PM
 */
public abstract class RelationalEntityStoreImpl  implements EntityStore, EntityExtractor {

    private String name;
    private RelationalConnectorImpl connector;
    private Set<AttributeDescriptor> attributeDescriptors = null;

    protected String getAllEntitiesQuery () {
        StringBuilder s = new StringBuilder();

        s.  append("select * ").
            append("from ").
            append(this.getName()).
            append("");

        return s.toString();
    }

    @Override
    public String getName () {
        return this.name;
    }

    protected void setName (String name) {
        this.name = name;
    }

    @Override
    public Set<KeyDescriptor> getKeyDescriptors() {

        Statement statement;
        KeyColumnDescriptor keyColumnDescriptor;
        Set<KeyDescriptor> keyDescriptors = new HashSet<>(1);   // not uncommon to have just one key definition
        String currentConstraintName = "";
        KeyDescriptorImpl currentKeyDescriptor = null;
        KeyDescriptorImpl.KeyType currentKeyType = KeyDescriptor.KeyType.UNKNOWN;
        String keyQuery;


        if (this.getConnector().isConnected()) {

            try {
                statement = this.getConnector().getConnection().createStatement();
                if (null != statement) {
                    keyQuery = this.substValue(this.getKeyDescriptorQuery(), this.getName(), "%", 1);
                    ResultSet resultSet = statement.executeQuery(keyQuery);
                    // Each result in the rs represents a single column of a key.
                    // So we have to go through all the rows and pick out the various key declarations
                    // along with the columns that comprise that key
                    while (resultSet.next()) {
                        keyColumnDescriptor = extractKeyColumnDescriptor(resultSet);
                        if (! currentConstraintName.equalsIgnoreCase(keyColumnDescriptor.getKeyName())) {
                            // This means we are now handling a new key definition.

                            currentConstraintName = keyColumnDescriptor.getKeyName();

                            currentKeyDescriptor = new KeyDescriptorImpl(   keyColumnDescriptor.getKeyName(),
                                                                            keyColumnDescriptor.getKeyType());
                            keyDescriptors.add(currentKeyDescriptor);
                        }
                        currentKeyDescriptor.addKeyColumnDescriptor(keyColumnDescriptor);

                    }

                    resultSet.close();
                    statement.close();
                }

            } catch (SQLException e) {
                this.getConnector().setLastError(e);
            }
        }

        return keyDescriptors;
    }

    protected RelationalConnectorImpl getConnector () {
        return this.connector;
    }

    protected void setConnector (RelationalConnectorImpl connector) {
        this.connector = connector;
    }

    abstract protected String getAttributeDescriptorQuery ();
    abstract protected String getEntityCountQuery ();
    abstract protected String getKeyDescriptorQuery ();
    abstract protected int extractEntityCount (ResultSet resultSet);
    abstract protected AttributeDescriptor extractAttributeDescriptor (ResultSet resultSet);
    abstract protected AttributeDescriptor.AttributeType mapNativeTypeToCanonicalType (String nativeTypeName);
    abstract protected KeyColumnDescriptor extractKeyColumnDescriptor (ResultSet resultSet);

    @Override
    public int getEntityCount () {
        int count = -1;
        Statement statement = null;

        if (this.getConnector().isConnected())
            try {
                statement = this.getConnector().getConnection().createStatement();
                if (null != statement) {
                    ResultSet resultSet = statement.executeQuery(this.getEntityCountQuery());
                    count = this.extractEntityCount(resultSet);
                    statement.close();
                }
            } catch (SQLException e) {
                this.getConnector().setLastError(e);
                try {
                    if (null != statement)
                        statement.close();
                } catch (SQLException e2) {
                    this.getConnector().setLastError(e2);
                }
            }


        return count;
    }

    @Override
    public Set<AttributeDescriptor> getAttributeDescriptors() {


        if (null == this.attributeDescriptors) {

            this.attributeDescriptors = new LinkedHashSet<>();

            Statement statement;

            if (this.getConnector().isConnected()) {

                try {
                    statement = this.getConnector().getConnection().createStatement();
                    if (null != statement) {
                        ResultSet resultSet = statement.executeQuery(this.getAttributeDescriptorQuery());
                        while (resultSet.next()) {
                            this.attributeDescriptors.add(extractAttributeDescriptor(resultSet));
                        }

                        resultSet.close();
                        statement.close();
                    }

                } catch (SQLException e) {
                    this.getConnector().setLastError(e);
                }
            }

        }

        return this.attributeDescriptors;
    }

/*
    protected void setAttributeDescriptors (Set<AttributeDescriptor> attributeDescriptors) {
        this.attributeDescriptors = attributeDescriptors;
    }
*/

    public Iterator<Entity> iterator () {
        EntityIterator entityIterator;

        entityIterator = new EntityIterator(this.getConnector(), this, this.getAllEntitiesQuery());
        return entityIterator;
    }

    protected class EntityIterator implements Iterator<Entity> {
        private EntityExtractor entityExtractor;
        private String allEntitiesQuery;
        private RelationalConnectorImpl connector;
        private ResultSet resultSet;
        private Entity nextEntity = null;
        private Statement statement;

        protected EntityExtractor getEntityExtractor () {
            return this.entityExtractor;
        }

        protected void setEntityExtractor (EntityExtractor entityExtractor) {
            this.entityExtractor = entityExtractor;
        }

        protected String getAllEntitiesQuery () {
            return this.allEntitiesQuery;
        }

        protected void setAllEntitiesQuery (String allEntitiesQuery) {
            this.allEntitiesQuery = allEntitiesQuery;
        }

        protected RelationalConnectorImpl getConnector () {
            return this.connector;
        }

        protected void setConnector (RelationalConnectorImpl connector) {
            this.connector = connector;
            if (null == connector) {
                throw new NullPointerException("EntityIterator requires a valid Connector!");
            }
        }

        protected Statement getStatement () {
            return this.statement;
        }

        protected void setStatement (Statement statement) {
            this.statement = statement;
        }

        public EntityIterator (RelationalConnectorImpl connector, EntityExtractor entityExtractor, String allEntitiesQuery) {
            this.setEntityExtractor(entityExtractor);
            this.setAllEntitiesQuery(allEntitiesQuery);
            this.setConnector(connector);

            if (this.getConnector().isConnected()) {
                try {
                    statement = this.getConnector().getConnection().createStatement();
                    if (null != statement) {
                        resultSet = statement.executeQuery(this.getAllEntitiesQuery());
                        if (resultSet.next())
                            this.setNextEntity(extractEntity(resultSet));
                        else {
                            this.setNextEntity(null);
                            this.close();
                        }
                    }
                } catch (SQLException e) {
                    this.close();
                    this.getConnector().setLastError(e);
                }
            }
        }

        private void close () {
            ResultSet resultSet;
            Statement statement;

            if (this.getConnector().isConnected() && null == this.getConnector().getLastError()) {
                try {
                    if (null != (resultSet = this.getResultSet())) {
                        this.setResultSet(null);
                        resultSet.close();
                    }

                    if (null != (statement = this.getStatement())) {
                        this.setStatement(null);
                        statement.close();
                    }


                } catch (SQLException e) {
                    this.close();
                    if (null == this.getConnector().getLastError()) {
                        this.getConnector().setLastError(e);
                    }
                } finally {
                    this.setNextEntity(null);
                }
            }
        }

        protected Entity getNextEntity () {
            return this.nextEntity;
        }

        protected void setNextEntity (Entity nextEntity) {
            this.nextEntity = nextEntity;
        }

        private ResultSet getResultSet () {
            return this.resultSet;
        }

        private void setResultSet (ResultSet resultSet) {
            this.resultSet = resultSet;
        }

        public boolean hasNext () {
            return (null != this.getNextEntity());
        }

        public Entity next () {
            Entity entity = this.getNextEntity();

            try {
                if (this.getResultSet().next()) {
                    this.setNextEntity(this.extractEntity(this.getResultSet()));
                }
                else {
                    this.setNextEntity(null);
                    this.close();
                }
            } catch (SQLException e) {
                this.getConnector().setLastError(e);
                this.close();
            }

            return entity;
        }

        public void remove () {
            // can't remove from this set!
        }

        private Entity extractEntity (ResultSet resultSet) {
            Entity entity;

            entity = this.getEntityExtractor().extractEntity(resultSet);

            return entity;
        }
    }

    protected String substValue (String s, String q, String mask, int ordinalPosition) {
        String retVal = s;

        if (null != s && null != q && null != mask && ordinalPosition > 0) {
            retVal = s.replace(mask + ordinalPosition, q);
        }

        return retVal;
    }
}
