package comp3350.sonicmatic.persistance.playlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import comp3350.sonicmatic.interfaces.IPersistentItem;
import comp3350.sonicmatic.persistance.Persistence;
import comp3350.sonicmatic.persistance.playlistsong.PlaylistSong;

public class PlaylistPersistence extends Persistence
{

    // ** constructors **
    public PlaylistPersistence(String dbName, String dbPath)
    {
        super(dbName, dbPath);
    }

    // ** abstract method overrides **
    @Override
    protected Playlist fromResultSet(ResultSet queryResult) throws SQLException
    {
        Playlist created;

        String str_id = queryResult.getString("playlist_id");
        String name = queryResult.getString("name");
        String creator_username = queryResult.getString("creator_username");

        int id;

        try
        {
            id = Integer.parseInt(str_id);

            created = new Playlist(id, creator_username, name);

        } catch(NumberFormatException nfe)
        {
            created = NullPlaylist.getNullPlaylist();
        }

        return created;
    }

    @Override
    public Playlist get(String playlistId)
    {
        Playlist retrieved;

        try(final Connection c = getConnection())
        {

            final PreparedStatement statement = c.prepareStatement("SELECT * FROM playlists WHERE playlist_id = ?");
            final ResultSet query_result;

            statement.setString(1, playlistId);
            query_result = statement.executeQuery();

            query_result.next();
            retrieved = fromResultSet(query_result);

            query_result.close();
            statement.close();

        } catch(final SQLException sqle)
        {
            retrieved = NullPlaylist.getNullPlaylist();
        }

        return retrieved;
    }

    @Override
    public Playlist update(IPersistentItem item)
    {
        Playlist updated;

        if (item instanceof Playlist)
        {
            Playlist playlist = ((Playlist)(item));

            try(final Connection c = getConnection())
            {
                final String update = "UPDATE playlists SET name = ?, creator_username = ? WHERE playlist_id = ?";
                final PreparedStatement statement = c.prepareStatement(update);

                statement.setString(1, playlist.getName());
                statement.setString(2, playlist.getCreatorUsername());
                statement.setString(3, playlist.getPrimaryKey());

                statement.executeUpdate();

                updated = get(item.getPrimaryKey());

            } catch(final SQLException sqle)
            {
                updated = NullPlaylist.getNullPlaylist();
            }

        }
        else
        {
            updated = NullPlaylist.getNullPlaylist();
        }

        return updated;
    }

    @Override
    public boolean insert(IPersistentItem item)
    {
        boolean success = true;

        Playlist playlist;

        if (item instanceof Playlist)
        {
            playlist = ((Playlist)(item));

            if (!get(playlist.getPrimaryKey()).getPrimaryKey().equals(playlist.getPrimaryKey()))
            {
                try(final Connection c = getConnection())
                {
                    final String insert = "INSERT INTO playlists VALUES(?, ?, ?)";
                    final PreparedStatement statement = c.prepareStatement(insert);

                    statement.setString(1, playlist.getPrimaryKey());
                    statement.setString(2, playlist.getName());
                    statement.setString(3, playlist.getCreatorUsername());

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

        if (item instanceof Playlist)
        {
            try(final Connection c = getConnection())
            {
                final String delete = "DELETE FROM playlists WHERE playlist_id = ?";
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
