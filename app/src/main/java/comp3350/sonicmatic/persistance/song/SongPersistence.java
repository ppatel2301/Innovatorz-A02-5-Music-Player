package comp3350.sonicmatic.persistance.song;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import comp3350.sonicmatic.interfaces.IPersistentItem;
import comp3350.sonicmatic.persistance.Persistence;

public class SongPersistence extends Persistence
{

    // ** constructors **
    public SongPersistence(String dbName, String dbPath)
    {
        super(dbName, dbPath);
    }

    // ** abstract method overrides **
    @Override
    protected Song fromResultSet(ResultSet queryResult) throws SQLException
    {
        return new Song(queryResult.getString("file_name_ext"));
    }

    public ArrayList<Song> getAll()
    {
        ArrayList<Song> retrieved = new ArrayList<Song>();

        try(final Connection c = getConnection())
        {

            final PreparedStatement statement = c.prepareStatement("SELECT * FROM Songs");
            final ResultSet query_result;

            query_result = statement.executeQuery();

            while(query_result.next())
            {
                retrieved.add(fromResultSet(query_result));
            }


            query_result.close();
            statement.close();

        } catch(final SQLException sqle)
        {
            retrieved.clear();
            retrieved.add(NullSong.getNullSong());
        }

        return retrieved;
    }

    @Override
    public Song get(String fileNameExt) // queries for if a filename of a song is in the db
    {
        Song retrieved;

        try(final Connection c = getConnection())
        {

            final PreparedStatement statement = c.prepareStatement("SELECT * FROM Songs WHERE file_name_ext = ?");
            final ResultSet query_result;

            statement.setString(1, fileNameExt);
            query_result = statement.executeQuery();

            query_result.next();
            retrieved = fromResultSet(query_result);

            query_result.close();
            statement.close();

        } catch(final SQLException sqle)
        {
            retrieved = NullSong.getNullSong();
        }

        return retrieved;
    }

    @Override
    public Song update(IPersistentItem item)
    {
       return NullSong.getNullSong(); // song file paths shouldn't be updated, they should just be removed and then a new one inserted
    }

    @Override
    public boolean insert(IPersistentItem item)
    {
        boolean success = true;
        Song to_insert;

        if (item instanceof Song)
        {
            to_insert = ((Song)(item));

            // if we can't get an equivalent song from the database, this song is not inside and we can insert
            if (!get(to_insert.getPrimaryKey()).getPrimaryKey().equals(to_insert.getPrimaryKey()))
            {
                try(final Connection c = getConnection())
                {
                    final String insert = "INSERT INTO songs VALUES(?)";
                    final PreparedStatement statement = c.prepareStatement(insert);

                    statement.setString(1, to_insert.getPrimaryKey());

                    statement.executeUpdate();

                } catch(final SQLException sqle)
                {
                    success = false;
                }
            }
            else
            {
                success = false;
            }
        }

        return success;
    }

    @Override
    public boolean delete(IPersistentItem item)
    {
        boolean success = true;

        if (item instanceof Song)
        {

            try(final Connection c = getConnection())
            {
                final String delete = "DELETE FROM songs WHERE file_name_ext = ?";
                final PreparedStatement statement = c.prepareStatement(delete);

                statement.setString(1,item.getPrimaryKey());

                statement.executeUpdate();

            } catch(final SQLException sqle)
            {
                success = false;
            }
        }
        else
        {
            success = false;
        }

        return success;
    }
}
