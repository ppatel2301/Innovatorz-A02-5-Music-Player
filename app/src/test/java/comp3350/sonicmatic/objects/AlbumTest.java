package comp3350.sonicmatic.objects;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

import static org.junit.Assert.*;

import comp3350.sonicmatic.interfaces.IArtist;
import comp3350.sonicmatic.interfaces.ISongLength;

@RunWith(JUnit4.class)
public class AlbumTest {

    private Album album1;
    private Album album2;
    private Album album3;
    private ISongLength songLength;

    private ArrayList<MusicTrack> musicTracks;

    @Before
    public void setUp()
    {
        IArtist artist = new MusicArtist("John");
        album1 = new Album("Album 1", artist);
        album2 = new Album("Album 2", artist, new ArrayList<>());
        album3 = new Album("Album 3", artist, null);
        songLength = new SongDuration("3432");
    }

    @Test
    public void testGetTitle()
    {
        System.out.println("\nTesting Album getTitle()");
        assertEquals("Album 1", album1.getTitle());
        assertEquals("Album 2", album2.getTitle());
        assertEquals("Album 3", album3.getTitle());
        System.out.println("\nFinished. Testing Album getTitle()");
    }

    @Test
    public void testGetArtist()
    {
        System.out.println("\nTesting Album getArtist");
        assertEquals("John", album1.getArtist().getName());
        System.out.println("\nFinished. Testing Album getArtist()");
    }

    @Test
    public void testGetTracks()
    {
        System.out.println("\nTesting Album getTracks()");
        assertNotNull(album1.getTracks());
        assertNotNull(album2.getTracks());
        assertNull(album3.getTracks());
        System.out.println("\nFinished. Testing Album getTracks()");
    }

    @Test
    public void testUploadTpAlbums(){
        System.out.println("\nTesting Album uploadToAlbums");
        album1.uploadToAlbums(new MusicTrack("Track 1", album1.getArtist(), songLength, "Path"));
        assertEquals(1, album1.getTracks().size());
    }
}