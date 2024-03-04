package comp3350.sonicmatic.business;

import comp3350.sonicmatic.application.Services;
import comp3350.sonicmatic.persistance.profile.GuestProfile;
import comp3350.sonicmatic.persistance.profile.NullProfile;
import comp3350.sonicmatic.persistance.profile.Profile;
import comp3350.sonicmatic.persistance.profile.ProfilePersistence;

public class AccessProfile
{

    // ** instance variables **
    private ProfilePersistence profilePersistence;
    private Profile loggedIn = new GuestProfile();;

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



}
