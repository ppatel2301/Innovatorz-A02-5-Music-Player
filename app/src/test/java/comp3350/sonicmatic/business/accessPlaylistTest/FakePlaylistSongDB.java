package comp3350.sonicmatic.business.accessPlaylistTest;

import java.util.ArrayList;

import comp3350.sonicmatic.interfaces.IPersistentItem;
import comp3350.sonicmatic.persistance.playlistSong.PlaylistSongPersistence;
import comp3350.sonicmatic.persistance.playlistSong.PlaylistSong;
import comp3350.sonicmatic.persistance.playlistSong.NullPlaylistSong;

public class FakePlaylistSongDB extends PlaylistSongPersistence
{

    private ArrayList<PlaylistSong> fakePS = new ArrayList<PlaylistSong>();

    public FakePlaylistSongDB(PlaylistSong init)
    {
        super("","");

        fakePS.add(init);
    }

    // ** instance methods **
    private PlaylistSong getFromStub(String [] pk)
    {
        PlaylistSong found = NullPlaylistSong.getNullPlaylistSong();

        // just linear search
        for(PlaylistSong p : fakePS)
        {
            if (p.getFileNameExt().equals(pk[PlaylistSong.FNAME_INDEX]) && (""+p.getPlaylistId()).equals(pk[PlaylistSong.ID_INDEX]))
            {
                found = new PlaylistSong(p); // hard copy
                break; // learned on a co-op work term that this is okay!
            }
        }

        return found;
    }

    private PlaylistSong getPointerFromStub(String [] pk)
    {
        PlaylistSong p_found = NullPlaylistSong.getNullPlaylistSong();

        for(PlaylistSong p : fakePS)
        {
            if (p.getFileNameExt().equals(pk[PlaylistSong.FNAME_INDEX]) && (""+p.getPlaylistId()).equals(pk[PlaylistSong.ID_INDEX]))
            {
                p_found = p; // soft copy
                break; // learned on a co-op work term that this is okay!
            }
        }

        return p_found;
    }

    // method overrides to make stub
    @Override
    public PlaylistSong get(String [] pk)
    {
        return getFromStub(pk);
    }

    @Override
    public PlaylistSong update(IPersistentItem item)
    {
        PlaylistSong update_me = NullPlaylistSong.getNullPlaylistSong();
        PlaylistSong update_to;

        if (item instanceof PlaylistSong)
        {
            update_to = ((PlaylistSong)(item));

            update_me = getPointerFromStub(update_me.makePrimaryKey());

            // manipulate the data at the pointer given

        }

        return update_me;
    }

    @Override
    public boolean insert(IPersistentItem item)
    {
        boolean inserted;
        PlaylistSong to_insert;

        if (item instanceof PlaylistSong)
        {
            to_insert = ((PlaylistSong)(item));

            // if not in db already, can insert
            if (!get(to_insert.getPrimaryKey()).equals(to_insert))
            {
                fakePS.add(new PlaylistSong(to_insert));

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

        if (item instanceof PlaylistSong && !get(item.getPrimaryKey()).equals(NullPlaylistSong.getNullPlaylistSong()))
        {
            fakePS.remove(((PlaylistSong)(item)));
            success = !get(item.getPrimaryKey()).equals(NullPlaylistSong.getNullPlaylistSong());
        }
        else
        {
            success = false;
        }

        return success;
    }

}
