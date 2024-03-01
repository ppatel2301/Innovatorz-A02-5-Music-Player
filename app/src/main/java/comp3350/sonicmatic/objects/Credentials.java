package comp3350.sonicmatic.objects;

public abstract class Credentials
{
    private String username;
    private String displayName;
    private String password;

    public Credentials(String username, String displayName, String password)
    {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
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
        return password;
    }


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
