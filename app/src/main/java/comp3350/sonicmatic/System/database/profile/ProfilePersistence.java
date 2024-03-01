package comp3350.sonicmatic.System.database.profile;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import comp3350.sonicmatic.System.database.Persistence;
import comp3350.sonicmatic.exceptions.PersistentTypeMismatchException;
import comp3350.sonicmatic.interfaces.IPersistentItem;

public class ProfilePersistence extends Persistence
{
    public ProfilePersistence()
    {
        super();
    }


    @Override
    public Profile get(String username)
    {
        Profile retrieved;

        try(final Connection c = getConnection())
        {

            final PreparedStatement statement = c.prepareStatement("SELECT * FROM Profiles");
           // statement.setString(1, username);

            final ResultSet queryResult = statement.executeQuery();

            queryResult.next();
            retrieved = new Profile(queryResult.getString("username"),
                                    queryResult.getString("display_name"),
                                    queryResult.getString("password"),
                                    queryResult.getInt("is_artist") == 1);

            queryResult.close();
            statement.close();

            return retrieved;

        } catch(final SQLException sqle)
        {
            //System.out.println(sqle);
            Log.d("ERROR", "sql exception when getting a profile:\n"+sqle.toString());
            return null;
        }
    }

    @Override
    public void update(IPersistentItem item) throws PersistentTypeMismatchException
    {
        if (item instanceof Profile)
        {
            Profile profile = ((Profile)(item));

            try(final Connection c = getConnection())
            {
                final String update = "UPDATE profiles SET displayname = ?, password = ? WHERE username = ?";
                final PreparedStatement statement = c.prepareStatement(update);

                statement.setString(1, profile.getDisplayName());
                statement.setString(2, profile.getPassword());
                statement.setString(3, profile.getUsername());

                statement.executeUpdate();

            } catch (final SQLException sqle)
            {

            }
        }
        else
        {
            throw new PersistentTypeMismatchException();
        }
    }

    @Override
    public void insert(IPersistentItem item) throws PersistentTypeMismatchException
    {
        if (item instanceof Profile)
        {
            Profile profile = ((Profile)(item));

            try(final Connection c = getConnection())
            {
                final String insert = "INSERT INTO profiles VALUES(?, ?, ?, ?)";
                final PreparedStatement statement = c.prepareStatement(insert);

                statement.setString(1, profile.getUsername());
                statement.setString(2, profile.getDisplayName());
                statement.setString(3, profile.getUsername());

                if (profile.isArtist())
                {
                    statement.setString(4, "1");
                }
                else
                {
                    statement.setString(4, "0");
                }

                statement.executeUpdate();

            } catch(final SQLException sqle)
            {

            }
        }
        else
        {
            throw new PersistentTypeMismatchException();
        }
    }

    @Override
    public void delete(IPersistentItem item) throws PersistentTypeMismatchException
    {
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

            }
        }
        else
        {
            throw new PersistentTypeMismatchException();
        }
    }

}
