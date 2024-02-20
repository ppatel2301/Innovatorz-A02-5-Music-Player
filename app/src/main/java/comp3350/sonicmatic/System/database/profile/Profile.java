package comp3350.sonicmatic.System.database.profile;

import comp3350.sonicmatic.interfaces.IPersistentItem;

public class Profile implements IPersistentItem
{

    // ** instance variables **
    private String username;
    private String displayName;
    private String password;
    boolean isArtist;

    // ** constructors ***
    public Profile(String username, String displayName, String password, boolean isArtist)
    {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.isArtist = isArtist;
    }

    // ** accessors **
    @Override
    public String getPrimaryKey()
    {
        return getUsername();
    }

    public String getUsername()
    {
        return username;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public String getPassword()
    {
        return getPassword();
    }

    public boolean isArtist()
    {
        return isArtist;
    }

    // ** mutators **
    public void changeDisplayname(String newDisplayName)
    {
        if (newDisplayName != null && newDisplayName != "")
        {
            displayName = newDisplayName;
        }
    }

    public void changePassword(String newPassword)
    {
        if (newPassword != null && newPassword != "")
        {
            password = newPassword;
        }
    }

}
