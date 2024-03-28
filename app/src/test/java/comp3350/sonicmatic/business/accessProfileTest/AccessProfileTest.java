package comp3350.sonicmatic.business.accessProfileTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import comp3350.sonicmatic.business.access.AccessProfile;
import comp3350.sonicmatic.persistance.profile.GuestProfile;
import comp3350.sonicmatic.persistance.profile.Profile;

@RunWith(JUnit4.class)
public class AccessProfileTest {

    private AccessProfile accessProfile;
    private static final String FIRST_UNAME = "firstUsername";
    private static final String FIRST_DNAME = "firstDisplayName";
    private static final String FIRST_PWD ="pwd";
    private static final boolean FIRST_PROFILE_IS_ARTIST = false;

    @Before
    public void setUp()
    {
        accessProfile = new AccessProfile(new FakeProfileDB((new Profile(FIRST_UNAME, FIRST_DNAME, FIRST_PWD, FIRST_PROFILE_IS_ARTIST))));
    }

    @Test
    public void testGetDisplayName()
    {
        accessProfile.login(FIRST_UNAME, FIRST_PWD);

        String display_name = accessProfile.getDisplayName();

        assertEquals("Access Profile test: logged in display name incorrect", true, display_name.equals(FIRST_DNAME) );
    }

    @Test
    public void testGetUsername()
    {
        accessProfile.login(FIRST_UNAME, FIRST_PWD);

        String username = accessProfile.getUsername();

        assertEquals("Access Profile test: logged in user name incorrect", true, username.equals(FIRST_UNAME) );
    }

    @Test
    public void testLogin()
    {
        boolean success = accessProfile.login(FIRST_UNAME, FIRST_PWD);

        String username = accessProfile.getUsername();
        String display_name = accessProfile.getDisplayName();

        assertEquals("Access Profile Test: Unsuccessful login", true, success);

        assertEquals("Access Profile test: logged in user name incorrect", true, username.equals(FIRST_UNAME) );
        assertEquals("Access Profile test: logged in display name incorrect", true, display_name.equals(FIRST_DNAME) );
    }

    @Test
    public void testBadLogin()
    {
        boolean no_succes = !accessProfile.login("gahooey", "pleth");

        String username = accessProfile.getUsername();
        String display_name = accessProfile.getDisplayName();

        assertEquals("Access Profile Test: Unsuccessful login", true, no_succes);

        //should still be guest logged in
        assertEquals("Access Profile test: logged in user name incorrect", true, username.equals(GuestProfile.getGuestProfile().getUsername()) );
        assertEquals("Access Profile test: logged in display name incorrect", true, display_name.equals(GuestProfile.getGuestProfile().getDisplayName()) );

    }

    @Test
    public void testLogout()
    {
        // login as an actual user, then logout
        accessProfile.login(FIRST_UNAME, FIRST_PWD);
        accessProfile.logout();

        String username = accessProfile.getUsername();
        String display_name = accessProfile.getDisplayName();

        assertEquals("Access Profile test: logged in user name incorrect", true, username.equals(GuestProfile.getGuestProfile().getUsername()) );
        assertEquals("Access Profile test: logged in display name incorrect", true, display_name.equals(GuestProfile.getGuestProfile().getDisplayName()) );
    }

    @Test
    public void testChangeDisplayName()
    {
        final String CHANGED = FIRST_DNAME+"changed";

        accessProfile.login(FIRST_UNAME, FIRST_PWD);
        accessProfile.changeDisplayName(CHANGED);

        String check = accessProfile.getDisplayName();

        assertEquals("Aaccess Profile test: failed to change displayname", true, check.equals(CHANGED));
    }

    @Test
    public void testChangePassword()
    {
        accessProfile.login(FIRST_UNAME, FIRST_PWD);
        boolean success = accessProfile.changePassword(FIRST_PWD+"changed");

        assertEquals("Access Profile test: failed to change password", true, success);
    }

    @Test
    public void testBadChangePassword()
    {
        accessProfile.login(FIRST_UNAME, FIRST_PWD);
        boolean success = !accessProfile.changePassword(FIRST_PWD); // changing it to the current password is not allowed

        assertEquals("Access Profile test: changed password...when it shouldn't have", true, success);
    }

    @Test
    public void testNewProfile()
    {
        final String NEW_UNAME = "new uname!";
        final String NEW_DNAME = "new dname!";
        final String NEW_PWD= "new pwd!";
        final boolean IS_ARTIST = true;

        boolean success = accessProfile.newProfile(NEW_UNAME, NEW_DNAME, NEW_PWD, IS_ARTIST);

        assertEquals("Access Profile test: couldn't insert new profile", true, success);
    }

    @Test
    public void testNewBadProfile()
    {
        boolean success = !accessProfile.newProfile(FIRST_UNAME, FIRST_DNAME, FIRST_PWD, FIRST_PROFILE_IS_ARTIST);

        assertEquals("Access Profile test: could insert new profile already in Db", true, success);
    }

    @Test
    public void testDeleteProfile()
    {
        final String NEW_UNAME = "delete me!";
        final String NEW_DNAME = "delete this!";
        final String NEW_PWD= "delete pwd!";
        final boolean IS_ARTIST = false;

        boolean inserted = accessProfile.newProfile(NEW_UNAME, NEW_DNAME, NEW_PWD, IS_ARTIST);
        accessProfile.login(NEW_UNAME, NEW_PWD);

        boolean deleted = accessProfile.deleteProfile();

        assertEquals("Access Profile Test: couldn't delete", true, deleted);
    }

    @Test
    public void testBadDeleteProfile()
    {
        // delete guest, shound't work
        boolean not_deleted = !accessProfile.deleteProfile();

        assertEquals("Access Profile Test: deleted something I shouldn't have", true, not_deleted);
    }
}