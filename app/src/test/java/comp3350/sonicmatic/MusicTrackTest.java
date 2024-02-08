package comp3350.sonicmatic;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import comp3350.sonicmatic.interfaces.Artist;
import comp3350.sonicmatic.interfaces.Song;
import comp3350.sonicmatic.objects.MusicArtist;
import comp3350.sonicmatic.objects.MusicTrack;
import comp3350.sonicmatic.objects.SongDuration;

public class MusicTrackTest
{

    private final String SONG_NAME = "Sang";
    private final String ARTIST_NAME = "Kanye East";
    private final String MILLIS = "42069";
    private final String PATH = "go/to/this/foler/to/get/song.mp3";

    private Song track;


    @Before
    public void setup()
    {
        track = new MusicTrack(SONG_NAME, new MusicArtist(ARTIST_NAME), new SongDuration(MILLIS), PATH);
    }

    @After
    public void teardown()
    {
        track = null;
    }

    @Test
    public void getPath()
    {

    }

    @Test
    public void getTitle() {
    }

    @Test
    public void getDuration() {
    }

    @Test
    public void getArtist() {
    }
}