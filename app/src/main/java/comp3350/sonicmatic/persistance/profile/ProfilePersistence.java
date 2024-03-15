package comp3350.sonicmatic.persistance.profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import comp3350.sonicmatic.objects.MusicArtist;
import comp3350.sonicmatic.persistance.Persistence;
import comp3350.sonicmatic.interfaces.IPersistentItem;

public class ProfilePersistence extends Persistence
{

    // ** class constants **
    private static final int IS_ARTIST = 1;
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

            final PreparedStatement statement = c.prepareStatement("SELECT * FROM profiles WHERE username = ?");
            final ResultSet query_result;

            statement.setString(1, username);
            query_result = statement.executeQuery();

            query_result.next();
            retrieved = fromResultSet(query_result);

            query_result.close();
            statement.close();

        } catch(final SQLException sqle)
        {
            retrieved = NullProfile.getNullProfile();
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
                updated = NullProfile.getNullProfile();
            }
        }
        else
        {
            updated = NullProfile.getNullProfile();
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


}
