package comp3350.sonicmatic.persistance.playlist;

import java.sql.ResultSet;
import java.sql.SQLException;

import comp3350.sonicmatic.interfaces.IPersistentItem;
import comp3350.sonicmatic.persistance.Persistence;

public class PlaylistPersistence extends Persistence
{

    // ** constructors **
    public PlaylistPersistence(String dbName, String dbPath) {

        super(dbName, dbPath);
    }

    // ** abstract method overrides **
    @Override
    protected IPersistentItem fromResultSet(ResultSet rs) throws SQLException
    {
        return null;
    }

    @Override
    public IPersistentItem get(String primaryKey)
    {
        return null;
    }

    @Override
    public IPersistentItem update(IPersistentItem item)
    {
        return null;
    }

    @Override
    public boolean insert(IPersistentItem item)
    {
        return false;
    }

    @Override
    public boolean delete(IPersistentItem item)
    {
        return false;
    }
}
