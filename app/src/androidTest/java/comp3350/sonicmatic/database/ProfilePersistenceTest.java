package comp3350.sonicmatic.database;

import static org.junit.Assert.*;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import comp3350.sonicmatic.persistance.profile.NullProfile;
import comp3350.sonicmatic.persistance.profile.Profile;
import comp3350.sonicmatic.persistance.profile.ProfilePersistence;
import comp3350.sonicmatic.application.Services;

public class ProfilePersistenceTest {

    private ProfilePersistence profilePersistence;

    @Before
    public void setup()
    {
        Services.setContext(InstrumentationRegistry.getInstrumentation().getTargetContext());
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

        // get another
        profile = profilePersistence.get("Profile11");

        username = profile.getUsername();
        display_name = profile.getDisplayName();
        password = profile.getPassword();

        assertEquals("Get Test: username \""+username+"\" not expected", username.equals("Profile11"),true);
        assertEquals("Get Test: display name \""+display_name+"\" not expected", display_name.equals("Ronald Gooderian"), true);
        assertEquals("Get Test: password \""+password+"\" not expected", password.equals("comp3350"), true);
        assertEquals("Get Test: Artist bool not true", profile.isArtist(), false);

    }

    @Test
    public void testBadGet()
    {
        // test the NullProfile object

        Profile profile = profilePersistence.get("I am not in this table!!!!");

        String username = profile.getUsername();
        String displayName = profile.getDisplayName();
        String password = profile.getPassword();

        assertEquals("Profile Get Test: username \""+username+"\" should have been \""+ NullProfile.getNullProfile().getUsername() +"\"", true, username.equals(NullProfile.getNullProfile().getUsername()));
        assertEquals("Profile Get Test: display name \""+displayName+"\"should have been \""+ NullProfile.getNullProfile().getDisplayName() +"\"", true, displayName.equals(NullProfile.getNullProfile().getDisplayName()));
        assertEquals("Profile Get Test: password \""+password+"\" should have been \""+ NullProfile.getNullProfile().getPassword() +"\"", true, password.equals(NullProfile.getNullProfile().getPassword()));
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
    public void testBadUpdate()
    {
        Profile should_be_null = profilePersistence.update(null);

        assertEquals("Profile Update Test: Profile wasn't null when it should have been", true, should_be_null.equals(NullProfile.getNullProfile()));
    }

    @Test
    public void testInsert()
    {
        Profile insert_me = new Profile("31an", "Elan", "ElansPassword", false);
        boolean success = profilePersistence.insert(insert_me);

        assertEquals("Profile Insert Test: Inserting a profile was unsuccesful", true, success);

        // now reset, get rid of it
        profilePersistence.delete(insert_me);

    }

    @Test
    public void testBadInsert()
    {
        Profile username_taken = new Profile("BenD1337", "bad username for this display name", "bad username for this password", false);
        boolean no_success = !profilePersistence.insert(username_taken);

        assertEquals("Profile Insert Test: Inserting a profile was succesful...but we didn't want it to be", no_success, true);

        boolean null_not_inserted = !profilePersistence.insert(null);

        assertEquals("Profile Insert Test: I inserted null", true, null_not_inserted);

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

            assertEquals("Profile Delete Test: Unsuccesful delete", true, success);
        }
    }

    // do we want to return false if the item isn't in the DB?
    /*
    @Test
    public void testBadDelete()
    {
        Profile not_in_db = new Profile("im not here!", "nuh uh", "nope", false);
        boolean success = !profilePersistence.delete(not_in_db);

        assertEquals("Profile delete test: Deleted something that wasn't in the database?", true, success);

    }
    */

    @Test
    public void testNullDelete()
    {
        boolean null_not_deleted = !profilePersistence.delete(null);

        assertEquals("Profile Delte Test: deleted null?", true, null_not_deleted);
    }

}
