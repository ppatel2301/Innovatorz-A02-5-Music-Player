package comp3350.sonicmatic.persistance.playlistSong;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import comp3350.sonicmatic.interfaces.IPersistentItem;
import comp3350.sonicmatic.persistance.Persistence;

public class PlaylistSongPersistence extends Persistence
{

    // ** constructors **
    public PlaylistSongPersistence(String dbName, String dbPath)
    {
        super(dbName, dbPath);
    }

    // ** abstract method overrides **
    @Override
    protected PlaylistSong fromResultSet(ResultSet queryResult) throws SQLException
    {
        PlaylistSong created;

        String file_name_ext = queryResult.getString("file_name_ext");
        String str_id = queryResult.getString("playlist_id");

        int id;

        try
        {
            id = Integer.parseInt(str_id);

            created = new PlaylistSong(file_name_ext, id);

        } catch(NumberFormatException nfe)
        {
            created = NullPlaylistSong.getNullPlaylistSong();
        }

        return created;
    }

    @Override
    public PlaylistSong get(String primaryKey)
    {
        return NullPlaylistSong.getNullPlaylistSong();
    }

    @Override
    public PlaylistSong get(String [] primaryKey)
    {
        PlaylistSong retrieved;

        // expecting two foreign keys to make the primary key
        if (primaryKey != null && primaryKey.length == 2)
        {
            try(final Connection c = getConnection())
            {

                final PreparedStatement statement = c.prepareStatement("SELECT * FROM playlist_songs WHERE file_name_ext = ? AND playlist_id = ?");
                final ResultSet query_result;

                statement.setString(1, primaryKey[PlaylistSong.FNAME_INDEX]);
                statement.setString(2, primaryKey[PlaylistSong.ID_INDEX]);

                query_result = statement.executeQuery();

                query_result.next();
                retrieved = fromResultSet(query_result);

                query_result.close();
                statement.close();

            } catch(final SQLException sqle)
            {
                retrieved = NullPlaylistSong.getNullPlaylistSong();
            }

        }
        else
        {
            retrieved =  NullPlaylistSong.getNullPlaylistSong();
        }

        return retrieved;
    }

    @Override
    public PlaylistSong update(IPersistentItem item)
    {
        return NullPlaylistSong.getNullPlaylistSong(); // never update these rows, only insert or delete them
    }

    @Override
    public boolean insert(IPersistentItem item)
    {
        boolean success = true;;
        PlaylistSong insert_me;
        PlaylistSong in_db;

        if (item != null && item instanceof PlaylistSong)
        {
            insert_me = ((PlaylistSong)(item));

            in_db = get(insert_me.makePrimaryKey());

            // if we can't get an equivalent profile from the database, this profile is not inside and we can insert
            if (!insert_me.equals(in_db))
            {
                try(final Connection c = getConnection())
                {
                    final String insert = "INSERT INTO playlist_songs VALUES(?, ?)";
                    final PreparedStatement statement = c.prepareStatement(insert);

                    statement.setString(1, insert_me.getFileNameExt());
                    statement.setString(2, ""+insert_me.getPlaylistId());

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
        else
        {
            success = false;
        }

        return success;
    }

    @Override
    public boolean delete(IPersistentItem item)
    {
        boolean success = true;

        if (item != null && item instanceof PlaylistSong)
        {

            try(final Connection c = getConnection())
            {
                final String delete = "DELETE FROM playlist_songs WHERE file_name_ext = ? AND playlist_id = ?";
                final PreparedStatement statement = c.prepareStatement(delete);

                statement.setString(1, ((PlaylistSong)(item)).getFileNameExt());
                statement.setString(2, ""+((PlaylistSong)(item)).getPlaylistId());

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

    // ** instance methods **

    public ArrayList<PlaylistSong> getPlaylistSongs(String playlistId)
    {
        ArrayList<PlaylistSong> playlistSongs = new ArrayList<PlaylistSong>();

        try(final Connection c = getConnection())
        {

            final PreparedStatement statement = c.prepareStatement("SELECT * FROM playlist_songs WHERE playlist_id = ?");
            final ResultSet query_result;

            statement.setString(1, playlistId);
            query_result = statement.executeQuery();

            while(query_result.next())
            {
                playlistSongs.add(fromResultSet(query_result));
            }

            query_result.close();
            statement.close();

        } catch(final SQLException sqle)
        {
            playlistSongs.clear();
        }

        return playlistSongs;
    }

}
