package comp3350.sonicmatic;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import comp3350.sonicmatic.interfaces.IArtist;
import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.interfaces.ISongLength;
import comp3350.sonicmatic.objects.MusicArtist;
import comp3350.sonicmatic.objects.MusicTrack;
import comp3350.sonicmatic.objects.SongDuration;

public class MusicTrackTest
{

    private final String SONG_NAME = "Sang";
    private final String ARTIST_NAME = "Kanye East";
    private final ISongLength DURATION = new SongDuration("42069");
    private final String PATH = "go/to/this/foler/to/get/song.mp3";

    private ISong track;


    @Before
    public void setup()
    {
        track = new MusicTrack(SONG_NAME, new MusicArtist(ARTIST_NAME), DURATION, PATH);
    }

    @After
    public void teardown()
    {
        track = null;
    }

    @Test
    public void getPathTest()
    {
        String path = track.getPath();

        assertEquals("Paths should be equal", true, path.equals(PATH));
    }

    @Test
    public void getTitleTest()
    {
        String title = track.getTitle();

        assertEquals("Titles should be equal", true,title.equals(SONG_NAME));
    }

    @Test
    public void getDurationTest()
    {
        ISongLength testDuration = track.getDuration();

        assertEquals("Duration returned shouldn't be null", true, testDuration != null);

        if (testDuration != null)
        {
           assertEquals("Minutes did not match", true, testDuration.getMinutes() == DURATION.getMinutes());
           assertEquals("Last seconds did not match", true, testDuration.getLastSeconds() == DURATION.getLastSeconds());
           assertEquals("Total seconds did not match", true, testDuration.getTotalSeconds() == DURATION.getTotalSeconds());
        }

    }

    @Test
    public void getArtistTest()
    {
        IArtist testArtist = track.getArtist();

        assertEquals("Artist returned shouldn't be null", true, testArtist != null);

        if (testArtist != null)
        {
            assertEquals("Artist name did not match", true, testArtist.getName().equals(ARTIST_NAME));
        }

    }
}