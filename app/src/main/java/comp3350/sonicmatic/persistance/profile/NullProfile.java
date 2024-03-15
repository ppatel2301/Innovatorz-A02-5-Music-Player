package comp3350.sonicmatic.persistance.profile;

public class NullProfile extends ImmutableProfile
{
    private static final String NULL_PROFILE = "NULL PROFILE";
    private static NullProfile nullProfile = null;

    public static NullProfile getNullProfile()
    {
        if (nullProfile == null)
        {
            nullProfile = new NullProfile();
        }

        return nullProfile;
    }

    private NullProfile()
    {
        super(NULL_PROFILE, NULL_PROFILE, NULL_PROFILE, false);
    }

}
