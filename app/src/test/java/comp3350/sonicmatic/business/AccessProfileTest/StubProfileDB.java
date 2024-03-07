package comp3350.sonicmatic.business.AccessProfileTest;

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
    public StubProfileDB(Profile init)
    {
        super("", "");

        stubProfiles = new ArrayList<Profile>();
        stubProfiles.add(init);
    }

    // ** instance methods **
    private Profile getFromStub(String username)
    {
        Profile found = NullProfile.getNullProfile();

        // just linear search
        for(Profile p : stubProfiles)
        {
            if (p.getUsername().equals(username))
            {
                found = new Profile(p); // hard copy
                break; // learned on a co-op work term that this is okay!
            }
        }

        return found;
    }

    private Profile getPointerFromStub(String username)
    {
        Profile p_found = NullProfile.getNullProfile();

        for(Profile p : stubProfiles)
        {
            if (p.getUsername().equals(username))
            {
                p_found = p; // soft copy
                break; // learned on a co-op work term that this is okay!
            }
        }

        return p_found;
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
        Profile update_to;

        if (item != null && item instanceof Profile)
        {
            update_to = ((Profile)(item));

            update_me = getPointerFromStub(item.getPrimaryKey());

            // manipulate the data at the pointer given

            update_me.changePassword(update_to.getPassword());
            update_me.changeDisplayname(update_to.getDisplayName());
            update_me.setArtist(update_to.isArtist());
        }

        return update_me;
    }

    @Override
    public boolean insert(IPersistentItem item)
    {
        boolean inserted;
        Profile to_insert;

        if (item instanceof Profile)
        {
            to_insert = ((Profile)(item));

            // if not in db already, can insert
            if (!get(to_insert.getPrimaryKey()).equals(to_insert))
            {
                stubProfiles.add(new Profile(to_insert));

                inserted = get(to_insert.getPrimaryKey()).equals(to_insert);
            }
            else
            {
                inserted = false;
            }
        }
        else
        {
            inserted = false;
        }

        return inserted;
    }

    @Override
    public boolean delete(IPersistentItem item)
    {
        boolean success = true;

        if (item instanceof Profile && !get(item.getPrimaryKey()).equals(NullProfile.getNullProfile()))
        {
            stubProfiles.remove(((Profile)(item)));
            success = !get(item.getPrimaryKey()).equals(NullProfile.getNullProfile());
        }
        else
        {
            success = false;
        }

        return success;
    }

}
