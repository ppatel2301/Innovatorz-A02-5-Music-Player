package comp3350.sonicmatic.persistance.profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import comp3350.sonicmatic.objects.MusicArtist;
import comp3350.sonicmatic.persistance.Persistence;
import comp3350.sonicmatic.interfaces.IPersistentItem;

public class ProfilePersistence extends Persistence
{

    // ** class constants **
    private static final int IS_ARTIST = 1;
    public static final NullProfile NULL_PROFILE = new NullProfile();
    public static final GuestProfile GUEST_PROFILE = new GuestProfile();
    private static final String IS_ARTIST_FLAG = "1";
    private static final String IS_NOT_ARTIST_FLAG = "0";

    // ** constructors **
    public ProfilePersistence(String dbName, String dbPath)
    {
        super(dbName, dbPath);
    }

    // ** abstract method overrides **
    @Override
    protected Profile fromResultSet(ResultSet queryResult) throws SQLException
    {
        return new Profile(queryResult.getString("username"),
                queryResult.getString("display_name"),
                queryResult.getString("password"),
                queryResult.getInt("is_artist") == IS_ARTIST);
    }
    @Override
    public Profile get(String username)
    {
        Profile retrieved;

        try(final Connection c = getConnection())
        {

            final PreparedStatement statement = c.prepareStatement("SELECT * FROM Profiles WHERE username = ?");
            final ResultSet query_result;

            statement.setString(1, username);
            query_result = statement.executeQuery();

            query_result.next();
            retrieved = fromResultSet(query_result);

            query_result.close();
            statement.close();

        } catch(final SQLException sqle)
        {
            retrieved = NULL_PROFILE;
        }

        return retrieved;
    }

    @Override
    public Profile update(IPersistentItem item)
    {

        Profile updated;

        if (item instanceof Profile)
        {
            Profile profile = ((Profile)(item)); // cast it to get methods

            try(final Connection c = getConnection())
            {
                final String update = "UPDATE profiles SET display_name = ?, password = ?, is_artist = ? WHERE username = ?";
                final PreparedStatement statement = c.prepareStatement(update);

                statement.setString(1, profile.getDisplayName());
                statement.setString(2, profile.getPassword());
                statement.setString(3, profile.isArtist() ?  IS_ARTIST_FLAG :  IS_NOT_ARTIST_FLAG);
                statement.setString(4, profile.getPrimaryKey());

                statement.executeUpdate();

                updated = get(item.getPrimaryKey());

            } catch(final SQLException sqle)
            {
                updated = NULL_PROFILE;
            }
        }
        else
        {
            updated = NULL_PROFILE;
        }

        return updated;
    }

    @Override
    public boolean insert(IPersistentItem item)
    {
        boolean success = true;
        Profile profile;

        if (item instanceof Profile)
        {
            profile = ((Profile)(item));

            // if we can't get an equivalent profile from the database, this profile is not inside and we can insert
            if (!get(profile.getPrimaryKey()).equals(profile))
            {
                try(final Connection c = getConnection())
                {
                    final String insert = "INSERT INTO profiles VALUES(?, ?, ?, ?)";
                    final PreparedStatement statement = c.prepareStatement(insert);

                    statement.setString(1, profile.getUsername());
                    statement.setString(2, profile.getDisplayName());
                    statement.setString(3, profile.getPassword());
                    statement.setString(4, profile.isArtist() ?  IS_ARTIST_FLAG :  IS_NOT_ARTIST_FLAG);

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

        if (item instanceof Profile)
        {

            try(final Connection c = getConnection())
            {
            final String delete = "DELETE FROM profiles WHERE username = ?";
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

    public ArrayList<MusicArtist> getArtistLeaderboard(){
        ArrayList<MusicArtist> leaderboard = new ArrayList<>();
        try (final Connection conn = getConnection()){
            final String leaderboardQuery = "WITH artists_per_playlist as (SELECT PLAYLISTS.name as playlist_name FROM PLAYLISTS on PROFILES.username = PLAYLISTS.creator_username LEFT JOIN PLAYLIST_SONGS on PLAYLISTS.playlist_id = PLAYLIST_SONGS.playlist_id LEFT JOIN SONGS on PLAYLIST_SONGS.file_name_ext = SONGS.file_name_ext LEFT JOIN ALBUMS on SONGS.album_id = ALBUMS.album_id LEFT JOIN PROFILES on ALBUMS.artist_id = PROFILES username) SELECT PROFILES.display_name, count(playlist_name) GROUP BY ALBUMS.artist_id;";
            final Statement query = conn.createStatement();
            ResultSet rset = query.executeQuery(leaderboardQuery);
        } catch (final SQLException sqle){

        }
        return leaderboard;
    }


}
