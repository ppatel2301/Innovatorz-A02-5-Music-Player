package comp3350.sonicmatic.business.AccessProfile;

import java.util.ArrayList;

import comp3350.sonicmatic.interfaces.IPersistentItem;
import comp3350.sonicmatic.persistance.profile.NullProfile;
import comp3350.sonicmatic.persistance.profile.Profile;
import comp3350.sonicmatic.persistance.profile.ProfilePersistence;

public class StubProfileDB extends ProfilePersistence
{

    // ** instance variables **
    private ArrayList<Profile> stubProfiles;

    // ** constructor **
    public StubProfileDB()
    {
        super("", "");

        stubProfiles = new ArrayList<Profile>();
        stubProfiles.add(new Profile("firstUsername", "firstDisplayName", "pwd!!!", false));
        stubProfiles.add(new Profile("robG", "robert, G", "robsPassword", true));
        stubProfiles.add(new Profile("gahooey!", "pleth", "Y U C K", false));
        stubProfiles.add(new Profile("onetwothreefour", "1234", "4321lel", true));
    }

    // ** instance methods **
    private Profile getFromStub(String username)
    {
        Profile found = NullProfile.getNullProfile();

        // linear search
        for(Profile p : stubProfiles)
        {
            if (p.getUsername().equals(username))
            {
                found = new Profile(p);
                break; // learned on a co-op work term that this is okay!
            }
        }

        return found;
    }

    // method overrides to make stub
    @Override
    public Profile get(String username)
    {
        return getFromStub(username);
    }

    @Override
    public Profile update(IPersistentItem item)
    {
        Profile update_me = null;

        if (item != null && item instanceof Profile)
        {
           // update_me
        }

        return update_me;
    }

}
