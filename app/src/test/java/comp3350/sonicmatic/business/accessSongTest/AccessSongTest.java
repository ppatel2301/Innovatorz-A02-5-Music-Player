package comp3350.sonicmatic.business.accessSongTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

import comp3350.sonicmatic.business.access.AccessSong;
import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.persistance.song.NullSong;
import comp3350.sonicmatic.persistance.song.Song;

@RunWith(JUnit4.class)
public class AccessSongTest {

    private AccessSong accessSong;
    private final String FIRST_SONG = "Archetype.mp3"; //this will be in the assets for sure

    @Before
    public void setUp()
    {
        accessSong = new AccessSong(new FakeSongDB(new Song(FIRST_SONG, 0)));
    }

    @Test
    public void testGetAllSongs()
    {
        // let's add some more songs in
        accessSong.insertSong("Combustion.mp3", 0);
        accessSong.insertSong("Cyberwaste.mp3", 0);
        accessSong.insertSong("Drone Corpse Aviator.mp3", 0);

        ArrayList<ISong> tracks = accessSong.getAllSongs();
        int track_count = tracks.size();

        // should be 4 songs, all should not be the null song
        boolean passed = true;

        for (ISong t : tracks)
        {
            passed = !t.getPath().equals(NullSong.getNullSong().getFileNameExt());
        }

        assertEquals("Access song test: Unpexected track count", true, track_count == 4);
        assertEquals("Access song test: Null song detected", true, passed);
    }

    @Test
    public void testGetSong()
    {
        boolean success = !accessSong.getSong(FIRST_SONG).getPath().equals(NullSong.getNullSong().getFileNameExt());

        assertEquals("Access Song test: could not get a song correctly.", true, success);
    }

    @Test
    public void testBadGetSong()
    {
        boolean success = !accessSong.getSong("jkljkljkl").getPath().equals(NullSong.getNullSong().getFileNameExt());

        assertEquals("Access Song test: got a song incorrectly", true, success);
    }

    @Test
    public void testInsertSong()
    {
        final String INSERT_PATH = "Cyberwaste.mp3";

        boolean success = accessSong.insertSong(INSERT_PATH, 0);

        assertEquals("Access song test: could not insert", true, success);
    }

    @Test
    public void testBadInsertSong()
    {
        // insert a song already in the db
        boolean success = !accessSong.insertSong(FIRST_SONG, 0);

        assertEquals("Access Song test: inserted a duplicate song", true, success);

    }

    @Test
    public void testDeleteSong()
    {
        final String DELETE_ME = "Lotion.mp3";
        boolean success;

        accessSong.insertSong(DELETE_ME, 0); // going to delete this

        success = accessSong.deleteSong(DELETE_ME);

        assertEquals("Access sogn test: deletion unsuccessful", true, success);

    }

}