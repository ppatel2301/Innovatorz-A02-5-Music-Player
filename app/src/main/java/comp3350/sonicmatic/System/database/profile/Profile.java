package comp3350.sonicmatic.System.database.profile;

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

    // ** mutators **

}
