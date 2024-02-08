package comp3350.sonicmatic;

import static org.junit.Assert.*;

import org.junit.Test;

import comp3350.sonicmatic.interfaces.IArtist;
import comp3350.sonicmatic.objects.MusicArtist;

public class MusicArtistTest {

    @Test
    public void getNameTest()
    {
        final String ARTIST_NAME = "Elan!";

        IArtist artist = new MusicArtist(ARTIST_NAME);

        String testName = artist.getName();

        assertEquals("Name invalid.", true, testName.equals(ARTIST_NAME));

    }
}