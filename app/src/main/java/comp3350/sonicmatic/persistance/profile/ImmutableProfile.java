package comp3350.sonicmatic.persistance.profile;

public abstract class ImmutableProfile extends Profile
{

    // ** constructors **
    public ImmutableProfile(String username, String displayName, String password, boolean isArtist)
    {
        super(username, displayName, password, isArtist);
    }


    // do not allow any changes to this profile

    public ImmutableProfile(ImmutableProfile copyMe)
    {
        super(copyMe);
    }

    // ** instance method overrides - no changes allowed **
    @Override
    public void changeDisplayname(String newDisplayName)
    {
        // Do nothing - don't change this instance at all
    }

    @Override
    public void changePassword(String newPassword)
    {
        // Do nothing - don't change this instance at all
    }

}
