package comp3350.sonicmatic.business.AccessProfile;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import comp3350.sonicmatic.business.AccessProfile;
import comp3350.sonicmatic.persistance.profile.GuestProfile;
import comp3350.sonicmatic.persistance.profile.Profile;

public class AccessProfileTest {

    private AccessProfile accessProfile;

    private static final String FIRST_UNAME = "firstUsername";
    private static final String FIRST_DNAME = "firstDisplayName";
    private static final String FIRST_PWD ="pwd";
    private static final boolean FIRST_PROFILE_IS_ARTIST = false;

    @Before
    public void setUp()
    {
        accessProfile = new AccessProfile(new StubProfileDB((new Profile(FIRST_UNAME, FIRST_DNAME, FIRST_PWD, FIRST_PROFILE_IS_ARTIST))));
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
    void testBadChangePassword()
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

      //  assertEquals("Access Profile test: ");
    }

    @Test
    public void testDeleteProfile()
    {

    }
}