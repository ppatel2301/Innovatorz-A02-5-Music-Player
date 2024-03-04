package comp3350.sonicmatic.persistance.profile;

public class NullProfile extends Profile
{
    public static final String NULL_PROFILE = "NULL PROFILE";

    public NullProfile()
    {
        super(NULL_PROFILE, NULL_PROFILE, NULL_PROFILE, false);
    }
}
