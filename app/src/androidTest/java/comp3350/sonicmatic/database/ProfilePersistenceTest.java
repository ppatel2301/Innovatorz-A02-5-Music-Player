package comp3350.sonicmatic.database;

import static org.junit.Assert.*;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import comp3350.sonicmatic.persistance.Persistence;
import comp3350.sonicmatic.persistance.profile.NullProfile;
import comp3350.sonicmatic.persistance.profile.Profile;
import comp3350.sonicmatic.persistance.profile.ProfilePersistence;
import comp3350.sonicmatic.application.Services;

public class ProfilePersistenceTest {

    private ProfilePersistence profilePersistence;

    @Before
    public void setup()
    {
        Persistence.context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        profilePersistence = Services.getProfilePersistence();
    }

    @Test
    public void testGet()
    {
        // tests an instantiated profile object

        Profile profile = profilePersistence.get("BenD1337");

        String username = profile.getUsername();
        String display_name = profile.getDisplayName();
        String password = profile.getPassword();

        assertEquals("Get Test: username \""+username+"\" not expected", username.equals("BenD1337"),true);
        assertEquals("Get Test: display name \""+display_name+"\" not expected", display_name.equals("Benjamin Dover"), true);
        assertEquals("Get Test: password \""+password+"\" not expected", password.equals("Password1"), true);
        assertEquals("Get Test: Artist bool not true", profile.isArtist(), true);

    }

    @Test
    public void testBadGet()
    {
        // test the NullProfile object

        Profile profile = profilePersistence.get("I am not in this table!!!!");

        String username = profile.getUsername();
        String displayName = profile.getDisplayName();
        String password = profile.getPassword();

        assertEquals("Profile Get Test: username \""+username+"\" should have been \""+ NullProfile.NULL_PROFILE +"\"", username.equals(NullProfile.NULL_PROFILE),true);
        assertEquals("Profile Get Test: display name \""+displayName+"\"should have been \""+ NullProfile.NULL_PROFILE +"\"", displayName.equals(NullProfile.NULL_PROFILE), true);
        assertEquals("Profile Get Test: password \""+password+"\" should have been \""+ NullProfile.NULL_PROFILE +"\"", password.equals(NullProfile.NULL_PROFILE), true);
    }

    @Test
    public void testUpdate()
    {
        final String  USERNAME= "BenD1337";
        Profile reset_to_me = profilePersistence.get(USERNAME);

        // update by adding exlcamation marks to some data, flipping the artist bit

        Profile updated_profile = new Profile(USERNAME, reset_to_me.getDisplayName()+"!", reset_to_me.getPassword()+"!", !reset_to_me.isArtist());
        Profile updated_from_DB;

        updated_from_DB = profilePersistence.update(updated_profile);

        // make sure the update went through
        assertEquals("Profile Update Test: The updated profile display name was incorrect. Expected \""+updated_profile.getDisplayName()+"\" but got \""+updated_from_DB.getDisplayName()+"\"",
                    true, updated_profile.getDisplayName().equals(updated_from_DB.getDisplayName()));

        assertEquals("Profile Update Test: The updated profile password was incorrect. Expected \""+updated_profile.getPassword()+"\" but got \""+updated_from_DB.getPassword()+"\"",
                true, updated_profile.getPassword().equals(updated_from_DB.getPassword()));

        assertEquals("Profile Update Test: The updated profile artist flag was incorrect. Expected \""+updated_profile.isArtist()+"\" but got \""+updated_from_DB.isArtist()+"\"",
                true, updated_profile.isArtist() == updated_from_DB.isArtist());

        // reset now that we're done testing
        updated_from_DB = profilePersistence.update(reset_to_me);

    }

    @Test
    public void testInsert()
    {
        Profile insert_me = new Profile("31an", "Elan", "ElansPassword", false);
        boolean success = profilePersistence.insert(insert_me);

        assertEquals("Profile Insert Test: Inserting a profile was unsuccesful", success, true);

        // now reset, get rid of it
        profilePersistence.delete(insert_me);

    }

    @Test
    public void testBadInsert()
    {
        Profile username_taken = new Profile("BenD1337", "bad username for this display name", "bad username for this password", false);
        boolean no_success = !profilePersistence.insert(username_taken);

        assertEquals("Profile Insert Test: Inserting a profile was succesful...but we didn't want it to be", no_success, true);

    }

    @Test
    public void testDelete()
    {
        final String USERNAME = "delete_me";
        Profile delete_me = new Profile(USERNAME, "delete this guy", "delete this guy with this password", false);

        boolean insert_success = profilePersistence.insert(delete_me); // have to give it something to delete
        boolean success;

        assertEquals("Profile Delete Test: Insertion of the row to delete failed", insert_success, true);

        if (insert_success)
        {
            success = profilePersistence.delete(delete_me); // now delete it

            assertEquals("Profile Delete Test: Unsuccesful delete", success, true);
        }
    }

}
