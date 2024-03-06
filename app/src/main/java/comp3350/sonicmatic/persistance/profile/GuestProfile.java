package comp3350.sonicmatic.persistance.profile;

public class GuestProfile extends ImmutableProfile
{
    private static final String GUEST_PROFILE = "GUEST PROFILE";

    public GuestProfile()
    {
        super(GUEST_PROFILE, "Guest", "", false);
    }
}
