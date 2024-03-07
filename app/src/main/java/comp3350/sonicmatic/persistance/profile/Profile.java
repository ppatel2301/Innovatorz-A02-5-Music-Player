package comp3350.sonicmatic.persistance.profile;

import comp3350.sonicmatic.interfaces.IPersistentItem;
import comp3350.sonicmatic.objects.Credentials;

public class Profile extends Credentials implements IPersistentItem
{

    // ** instance variables **
    private boolean isArtist;

    // ** constructors ***
    public Profile(String username, String displayName, String password, boolean isArtist)
    {
        super(username, displayName, password);
        this.isArtist = isArtist;
    }

    public Profile(Profile copyMe)
    {
        super(copyMe.getUsername(), copyMe.getDisplayName(), copyMe.getPassword());
        this.isArtist = copyMe.isArtist();
    }

    // ** accessors **
    @Override
    public String getPrimaryKey()
    {
        return getUsername();
    }
    public boolean isArtist()
    {
        return isArtist;
    }
    public void setArtist(boolean isArtist)
    {
        this.isArtist = isArtist;
    }

}
