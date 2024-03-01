package comp3350.sonicmatic.database;

import static org.junit.Assert.*;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.ContentHandler;

import comp3350.sonicmatic.System.database.Persistence;
import comp3350.sonicmatic.System.database.profile.Profile;
import comp3350.sonicmatic.System.database.profile.ProfilePersistence;

public class ProfilePersistenceTest {

    private ProfilePersistence profilePersistence;

    @Before
    public void setup()
    {
        Persistence.context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        profilePersistence = new ProfilePersistence();
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void testGet()
    {
        Profile profile = profilePersistence.get("BenD1337");

        assertEquals("Profile was null", true, profile != null);

        String username = profile.getUsername();
        String displayName = profile.getDisplayName();
        String password = profile.getPassword();

        assertEquals("Get Test: username \""+username+"\" not expected", username.equals("BenD1337"),true);
        assertEquals("Get Test: display name \""+displayName+"\" not expected", displayName.equals("Benjamin Dover"), true);
        assertEquals("Get Test: password \""+password+"\" not expected", password.equals("Password1"), true);
        assertEquals("Get Test: Artist bool not true", profile.isArtist(), true);

    }

    @Test
    public void testUpdate()
    {

    }

    @Test
    public void testInsert()
    {

    }

    @Test
    public void testDelete()
    {

    }
}
