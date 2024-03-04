package comp3350.sonicmatic.application;

import comp3350.sonicmatic.persistance.profile.ProfilePersistence;

public class Services
{

    // ** class constants **
    private static final String DB_NAME = "SMDB";
    private static final String DB_PATH = "database";

    // ** class variables **
    private static ProfilePersistence profilePersistence = null;

    // ** locked access methods **
    public static synchronized ProfilePersistence getProfilePersistence()
    {
        if (profilePersistence == null)
        {
            profilePersistence = new ProfilePersistence(DB_NAME, DB_PATH);
        }

        return profilePersistence;
    }


}
