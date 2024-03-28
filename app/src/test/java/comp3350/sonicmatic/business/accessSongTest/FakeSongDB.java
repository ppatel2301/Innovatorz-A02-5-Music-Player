package comp3350.sonicmatic.business.accessSongTest;

import java.util.ArrayList;

import comp3350.sonicmatic.interfaces.IPersistentItem;
import comp3350.sonicmatic.persistance.song.NullSong;
import comp3350.sonicmatic.persistance.song.Song;
import comp3350.sonicmatic.persistance.song.SongPersistence;

public class FakeSongDB extends SongPersistence
{
    // ** instance variables **
    private ArrayList<Song> fakeSongs;

    // ** constructor **
    public FakeSongDB(Song init)
    {
        super("", "");

        fakeSongs = new ArrayList<Song>();
        fakeSongs.add(init);
    }

    // ** instance methods **
    private Song getFromStub(String path)
    {
        Song found = NullSong.getNullSong();

        // just linear search
        for(Song s : fakeSongs)
        {
            if (s.getFileNameExt().equals(path))
            {
                found = new Song(s.getFileNameExt(), 0); // hard copy
                break; // learned on a co-op work term that this is okay!
            }
        }

        return found;
    }

    // method overrides to make stub
    @Override
    public ArrayList<Song> getAll()
    {
        ArrayList<Song> retrieved = new ArrayList<Song>();
        retrieved.addAll(fakeSongs);


        return  retrieved;
    }

    @Override
    public Song get(String path)
    {
        return getFromStub(path);
    }

    @Override
    public Song update(IPersistentItem item)
    {
        return NullSong.getNullSong(); // don't allow updates, its just one column
    }

    @Override
    public boolean insert(IPersistentItem item)
    {
        boolean inserted;
        Song to_insert;

        if (item instanceof Song)
        {
            to_insert = ((Song)(item));

            // if not in db already, can insert
            if (!get(to_insert.getPrimaryKey()).getFileNameExt().equals(to_insert.getFileNameExt()))
            {
                fakeSongs.add(new Song(to_insert.getFileNameExt(), 0));

                inserted = get(to_insert.getPrimaryKey()).getFileNameExt().equals(to_insert.getFileNameExt());
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

        if (item instanceof Song && !get(item.getPrimaryKey()).getFileNameExt().equals(NullSong.getNullSong().getPrimaryKey()))
        {
            fakeSongs.remove(((Song)(item)));
            success = !get(item.getPrimaryKey()).equals(NullSong.getNullSong());
        }
        else
        {
            success = false;
        }

        return success;
    }
}
