package comp3350.sonicmatic.persistance.profile;

public class GuestProfile extends ImmutableProfile
{
    private static final String GUEST_PROFILE = "GUEST PROFILE";
    private static GuestProfile guestProfile = null;

    public static GuestProfile getGuestProfile()
    {
        if (guestProfile == null)
        {
            guestProfile = new GuestProfile();
        }

        return guestProfile;
    }
    private GuestProfile()
    {
        super(GUEST_PROFILE, "Guest", "", false);
    }
}
