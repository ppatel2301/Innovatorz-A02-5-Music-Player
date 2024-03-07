package comp3350.sonicmatic.business;

import comp3350.sonicmatic.application.Services;
import comp3350.sonicmatic.persistance.Persistence;
import comp3350.sonicmatic.persistance.profile.Profile;
import comp3350.sonicmatic.persistance.profile.ProfilePersistence;

public class AccessProfile
{

    // ** instance variables **
    private ProfilePersistence profilePersistence;
    private Profile loggedIn = GuestProfile.getGuestProfile();

    // ** constructors **
    public AccessProfile()
    {
        profilePersistence = Services.getProfilePersistence();
    }

    public AccessProfile(final ProfilePersistence profilePersistence)
    {
        this.profilePersistence = profilePersistence;
    }

    // ** accessors **
    public String getDisplayName()
    {
        return loggedIn.getDisplayName();
    }

    public String getUsername()
    {
        return loggedIn.getUsername();
    }

    // ** mutators **
    public boolean login(String username, String password)
    {
        Profile from_db = profilePersistence.get(username);
        boolean success = from_db.getPassword().equals(password);

        if (success && !from_db.equals(loggedIn)) // if not already logged in and correct password
        {
            loggedIn = from_db;
        }

        return success;
    }

    public void logout()
    {
        loggedIn = GuestProfile.getGuestProfile();
    }

    public boolean changeDisplayName(String newName)
    {
        boolean success = false;
        Profile newly_named;

        // do not proceed if the new name is the current one
        if (Persistence.isStringOkay(newName) && !newName.equals(loggedIn.getDisplayName()))
        {
            newly_named = new Profile(loggedIn); // copy logged in data
            newly_named.changeDisplayname(newName); // change the name in the copy

            newly_named = profilePersistence.update(newly_named); // change it, get result

            success = newly_named.getDisplayName().equals(newName); // if it worked these should be equal

            if (success)
            {
                loggedIn.changeDisplayname(newName);
            }
        }

        return success;
    }

    public boolean changePassword(String newPassword)
    {
        // same logic as the above changeDisplayName method

        boolean success = false;
        Profile new_password_applied;

        // do not proceed if the new name is the current one, also if we're logged in as the guest or null
        if ((!(loggedIn.equals(GuestProfile.getGuestProfile()) || loggedIn.equals(NullProfile.getNullProfile())))
             &&  (Persistence.isStringOkay(newPassword) && !newPassword.equals(loggedIn.getPassword())))
        {
            new_password_applied = new Profile(loggedIn);
            new_password_applied.changePassword(newPassword);

            new_password_applied = profilePersistence.update(new_password_applied);

            success = new_password_applied.getPassword().equals(newPassword);

            if (success)
            {
                loggedIn.changePassword(newPassword);
            }
        }

        return success;
    }

    public boolean newProfile(String username, String displayName, String password, boolean isArtist)
    {
        boolean success = false;
        Profile newly_created;

        // check all parameters
        if (Persistence.isStringOkay(username) && Persistence.isStringOkay(displayName)
                && Persistence.isStringOkay(password))
        {
            newly_created = new Profile(username, displayName, password, isArtist);

            success = profilePersistence.insert(newly_created);
        }

        return success;
    }

    // removes logged in user from the database entirely
    public boolean deleteProfile()
    {
        boolean success = false;

        if (!(loggedIn.equals(GuestProfile.getGuestProfile()) || loggedIn.equals(NullProfile.getNullProfile())))
        {
            success = profilePersistence.delete(loggedIn);
        }

        return success;
    }

}
