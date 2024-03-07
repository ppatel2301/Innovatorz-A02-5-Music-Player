package comp3350.sonicmatic.database;

import androidx.test.platform.app.InstrumentationRegistry;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import comp3350.sonicmatic.application.Services;
import comp3350.sonicmatic.business.AccessPlaylist;
import comp3350.sonicmatic.business.AccessProfile;
import comp3350.sonicmatic.business.AccessSong;
import comp3350.sonicmatic.interfaces.IPlaylist;
import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.persistance.playlist.Playlist;
import comp3350.sonicmatic.persistance.song.NullSong;
import comp3350.sonicmatic.persistance.song.Song;
import comp3350.sonicmatic.persistance.song.SongPersistence;

public class SongPersistenceTest extends TestCase {

    private SongPersistence songPersistence;
    final String FNAME_EXT = "Archetype.mp3"; // common song to test

    @Before
    public void setUp()
    {
        Services.setContext(InstrumentationRegistry.getInstrumentation().getTargetContext());
        songPersistence = Services.getSongPersistence();
    }

    @Test
    public void testGet()
    {
        AccessSong accessSong = new AccessSong();
        ArrayList<ISong> songs = accessSong.getAllSongs();

        AccessProfile accessProfile = new AccessProfile();
        boolean success = accessProfile.login("Profile11","comp3350");

        if (success)
        {
            AccessPlaylist accessPlaylist = new AccessPlaylist();
            boolean newPlist = accessPlaylist.newPlaylist("HELLO", accessProfile);
            ArrayList<IPlaylist> from_db = accessPlaylist.getPlaylists(accessProfile);

            boolean insertSucc =  accessPlaylist.insertIntoPlaylist("HELLO",Services.createSongFromPath("Archetype.mp3"), accessProfile);

            ArrayList<IPlaylist> playlists = accessPlaylist.getPlaylists(accessProfile);

        }
        Song song = songPersistence.get(FNAME_EXT);
        String file_name_ext = song.getFileNameExt();


        assertEquals("Song file name wasn't expected, it was "+file_name_ext, true, file_name_ext.equals(FNAME_EXT));

    }

    @Test
    public void testBadGet()
    {
        final String FNAME_EXT = "not in here .mp420";

        Song song = songPersistence.get(FNAME_EXT);
        String file_name_ext = song.getFileNameExt();

        assertEquals("Song file should have been NULL SONG, but it was "+file_name_ext, true, NullSong.getNullSong().getFileNameExt().equals(file_name_ext));

    }

    @Test
    public void testUpdate()
    {
        // since we just keep track of file paths per row, this test makes sure we can't update a path (makes more sense to just insert)
        Song song = songPersistence.update(new Song(FNAME_EXT));

        assertEquals("Wanted the null song from this update call", true, song.getFileNameExt().equals(NullSong.getNullSong().getFileNameExt()));
    }

    @Test
    public void testInsert()
    {
        Song insert_me = new Song("Cyberwaste.mp3");

        boolean success = songPersistence.insert(insert_me);

        assertEquals("Unsuccessful song insert", true, success);

        // delete now that we're done
        if (success)
        {
            songPersistence.delete(insert_me);
        }

    }

    @Test
    public void  testBadInsert()
    {
        Song dont_insert_me = new Song(FNAME_EXT); // this song is already in the DB

        boolean success = songPersistence.insert(dont_insert_me);

        assertEquals("Successful song insert...should have not been", false, success);

    }

    @Test
    public void testDelete()
    {
        boolean success;
        Song delete_me = new Song("Cyberwaste.mp3");

        songPersistence.insert(delete_me); // have to put something in here to delete
        success = songPersistence.delete(delete_me);

        assertEquals("Unsuccessful song delete", true, success);
    }

    @Test
    public void testGetAll()
    {
        ArrayList<Song> songs = songPersistence.getAll();

        boolean success = true;

        if (songs.size() == 1) // if there's only one, make sure its not the null song (indicative of failure)
        {
            success = !(songs.get(0).getFileNameExt().equals(NullSong.getNullSong().getFileNameExt()));
        }

        assertEquals("Song test failed, only got the null song", true, success);
    }

}