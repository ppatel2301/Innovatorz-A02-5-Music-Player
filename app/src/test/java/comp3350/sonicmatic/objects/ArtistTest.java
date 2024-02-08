package comp3350.sonicmatic.objects;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ArtistTest {

    private Artist artist1;
    private Artist artist2;
    private Artist artist3;

    private Album album;

    @Before
    public void setUp()
    {
        artist1 = new Artist("John");
        artist2 = new Artist("Bob");
        artist3 = new Artist("Artist 3");

        album = new Album("Album 1", artist1);
    }

    @Test
    public void testUploadMusic()
    {
        System.out.println("\nTesting testUploadMusic");
//        artist1.uploadMusic(new MusicTrack("Track 1", artist1));
//        artist1.uploadMusic(new MusicTrack("Track 2", artist1));

//        assertEquals(2, artist1.getTracks().size());
        System.out.println("\nFinished. Testing testUploadMusic");
    }

    @Test
    public void testUploadToAlbums()
    {
        System.out.println("\nTesting testUploadToAlbums");
        artist1.uploadToAlbum(album);
        assertEquals(1, artist1.getAlbums().size());
        System.out.println(("\nFinished. Testing testUploadToAlbums()"));
    }

    @Test
    public void testGetArtistName()
    {
        System.out.println("\nTesting getArtistName");
        assertEquals("John", artist1.getName());
        assertEquals("Bob", artist2.getName());
        assertEquals("Artist 3", artist3.getName());
        System.out.println("\nFinished. Testing getArtistName");
    }
}