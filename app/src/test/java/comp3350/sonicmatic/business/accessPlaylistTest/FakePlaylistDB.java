package comp3350.sonicmatic.business.accessPlaylistTest;

import java.util.ArrayList;

import comp3350.sonicmatic.interfaces.IPersistentItem;
import comp3350.sonicmatic.persistance.playlist.Playlist;
import comp3350.sonicmatic.persistance.playlist.PlaylistPersistence;
import comp3350.sonicmatic.persistance.playlist.NullPlaylist;

public class FakePlaylistDB extends PlaylistPersistence
{

    // ** instance variables **
    private ArrayList<Playlist> fakePlaylists = new ArrayList<Playlist>();

    // ** constructor **
    public FakePlaylistDB(Playlist init)
    {
        super("", "");

        fakePlaylists.add(init);
    }

    // ** instance methods **
    private Playlist getFromStub(String id)
    {
        Playlist found = NullPlaylist.getNullPlaylist();

        // just linear search
        for(Playlist p : fakePlaylists)
        {
            if ((p.getId()+"").equals(id))
            {
                found = new Playlist(p); // hard copy
                break; // learned on a co-op work term that this is okay!
            }
        }

        return found;
    }

    private Playlist getFromStub(String name, String creatorUsername)
    {
        Playlist found = NullPlaylist.getNullPlaylist();

        // just linear search
        for(Playlist p : fakePlaylists)
        {
            if (p.getName().equals(name) && p.getCreatorUsername().equals(creatorUsername))
            {
                found = new Playlist(p); // hard copy
                break; // learned on a co-op work term that this is okay!
            }
        }

        return found;
    }

    private Playlist getPointerFromStub(String id)
    {
        Playlist p_found = NullPlaylist.getNullPlaylist();

        for(Playlist p : fakePlaylists)
        {
            if ((p.getId()+"").equals(id))
            {
                p_found = p; // soft copy
                break; // learned on a co-op work term that this is okay!
            }
        }

        return p_found;
    }

    // method overrides to make stub
    @Override
    public Playlist get(String id)
    {
        return getFromStub(id);
    }
    public Playlist get(String name, String creatorUsername)
    {
        return getFromStub(name, creatorUsername);
    }

    @Override
    public Playlist update(IPersistentItem item)
    {
        Playlist update_me = NullPlaylist.getNullPlaylist();
        Playlist update_to;

        if ( item instanceof Playlist)
        {
            update_to = ((Playlist)(item));

            update_me = getPointerFromStub(item.getPrimaryKey());

            // manipulate the data at the pointer given
            update_me.changeName(update_to.getName());

        }

        return update_me;
    }

    @Override
    public boolean insert(IPersistentItem item)
    {
        boolean inserted;
        Playlist to_insert;

        if (item instanceof Playlist)
        {
            to_insert = ((Playlist)(item));

            // if not in db already, can insert
            if (!get(to_insert.getPrimaryKey()).equals(to_insert))
            {
                fakePlaylists.add(new Playlist(to_insert));

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

        if (item instanceof Playlist && !get(item.getPrimaryKey()).equals(NullPlaylist.getNullPlaylist()))
        {
            fakePlaylists.remove(((Playlist)(item)));
            success = !get(item.getPrimaryKey()).equals(NullPlaylist.getNullPlaylist());
        }
        else
        {
            success = false;
        }

        return success;
    }
}
