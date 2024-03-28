package comp3350.sonicmatic.persistance.playlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import comp3350.sonicmatic.interfaces.IPersistentItem;
import comp3350.sonicmatic.persistance.Persistence;

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

                // don't allow the changing of the creator or playlist ID, just the name

                final String update = "UPDATE playlists SET name = ? WHERE playlist_id = ?";
                final PreparedStatement statement = c.prepareStatement(update);

                statement.setString(1, playlist.getName());
                statement.setInt(2, Integer.parseInt(playlist.getPrimaryKey()));

                statement.executeUpdate();

                updated = get(item.getPrimaryKey());

            } catch(final SQLException | NumberFormatException ex)
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

        boolean inside_already; // make sure the id and/or creator-name combo are not already inside

        Playlist playlist;

        if (item instanceof Playlist)
        {
            playlist = ((Playlist)(item));

            inside_already =
                       get(playlist.getPrimaryKey()).getPrimaryKey().equals(playlist.getPrimaryKey()) // if we can get a playlist with this ID, we're in
                    || get(playlist.getName(), playlist.getCreatorUsername()).getId() != NullPlaylist.getNullPlaylist().getId(); // if we don't get null when quering with this ID, we're in

            if (!inside_already)
            {
                try(final Connection c = getConnection())
                {
                    final String insert = "INSERT INTO playlists (playlist_id, name, creator_username) VALUES (DEFAULT, ?, ?)";//"INSERT INTO playlists VALUES(?, ?)";
                    final PreparedStatement statement = c.prepareStatement(insert);

                    statement.setString(1, playlist.getName());
                    statement.setString(2, playlist.getCreatorUsername());

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

    // ** instance methods **
    public ArrayList<Playlist> getPlaylistsOfUser(String username)
    {
        ArrayList<Playlist> playlists = new ArrayList<Playlist>();

        try(final Connection c = getConnection())
        {

            final PreparedStatement statement = c.prepareStatement("SELECT * FROM playlists WHERE creator_username = ?");
            final ResultSet query_result;

            statement.setString(1, username);
            query_result = statement.executeQuery();

            while(query_result.next())
            {
                playlists.add(fromResultSet(query_result));
            }

            query_result.close();
            statement.close();

        } catch(final SQLException sqle)
        {
            playlists.clear();
        }

        return  playlists;
    }

    public Playlist get(String name, String creatorUsername)
    {
        Playlist retrieved;

        try(final Connection c = getConnection())
        {

            final PreparedStatement statement = c.prepareStatement("SELECT * FROM playlists WHERE name = ? AND creator_username = ?");
            final ResultSet query_result;

            statement.setString(1, name);
            statement.setString(2, creatorUsername);
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
}
