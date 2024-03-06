package comp3350.sonicmatic.persistance.profile;

public class NullProfile extends ImmutableProfile
{
    private static final String NULL_PROFILE = "NULL PROFILE";

    public NullProfile()
    {
        super(NULL_PROFILE, NULL_PROFILE, NULL_PROFILE, false);
    }

    // do not allow any changes to this profile
}
