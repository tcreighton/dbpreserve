package org.cr8on.dbpreserve.impl.relational;

import org.cr8on.dbpreserve.impl.EntityImpl;

import java.sql.ResultSet;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 3/4/13
 * Time: 12:11 AM
 */
public interface EntityExtractor {
    public EntityImpl extractEntity (ResultSet resultSet);
}
