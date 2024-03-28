package comp3350.sonicmatic.integrationTests;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

import comp3350.sonicmatic.application.Services;
import comp3350.sonicmatic.business.access.AccessSong;
import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.persistance.song.NullSong;
import comp3350.sonicmatic.objects.musicTrack.NullMusicTrack;

@RunWith(JUnit4.class)
public class AccessSongTest {

    private Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private AccessSong accessSong;
    private final String FIRST_SONG = "Archetype.mp3"; //this will be in the assets for sure

    @Before
    public void setUp()
    {
        Services.setContext(context);
        accessSong = new AccessSong(Services.getSongPersistence());
    }

    @Test
    public void testGetAllSongs()
    {
        // expecting all 14 songs
        ArrayList<ISong> tracks = accessSong.getAllSongs();
        int track_count = tracks.size();

        boolean passed = true;

        for (ISong t : tracks)
        {
            passed = !t.getPath().equals(NullSong.getNullSong().getFileNameExt());
            if (!passed)
            {
                break;
            }
        }

        assertEquals("Access song test: Unpexected track count", 16, track_count);
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
        boolean success = accessSong.getSong("jkljkljkl").getPath().equals(NullMusicTrack.getNullMusicTrack().getPath());

        assertEquals("Access Song test: got a song incorrectly", true, success);
    }

    @Test
    public void testInsertSong()
    {
        final String INSERT_PATH = "Catanonia.mp3";

        boolean success = accessSong.insertSong(INSERT_PATH,0);

        assertEquals("Access song test: could not insert", true, success);

        accessSong.deleteSong("Catanonia.mp3");
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

        assertEquals("Access song test: deletion unsuccessful", true, success);

    }

}