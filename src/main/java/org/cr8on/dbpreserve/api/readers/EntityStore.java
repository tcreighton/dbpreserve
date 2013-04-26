package org.cr8on.dbpreserve.api.readers;

import java.util.Iterator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Tom Creighton
 * Date: 2/21/13
 * Time: 11:15 PM
 */
public interface EntityStore extends Iterable<Entity> {

    public String getName ();
    public Set<AttributeDescriptor> getAttributeDescriptors ();
    public Set<KeyDescriptor> getKeyDescriptors();
    public int getEntityCount ();
    public Iterator<Entity> iterator ();
}
