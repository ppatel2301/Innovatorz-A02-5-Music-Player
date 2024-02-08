package comp3350.sonicmatic.objects;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class PlaylistTest {

    private Playlist playlist;
    private ArrayList<MusicTrack> musicTracks;

    //Tracks
    private MusicTrack musicTrack1;
    private MusicTrack musicTrack2;
    private MusicTrack musicTrack3;
    private Artist artist;

    @Before
    public void setUp()
    {
        artist = new Artist("Bob");
//        musicTrack1 = new MusicTrack("Title1", artist)
//        musicTrack2 = new MusicTrack("Title2", artist);
//        musicTrack3 = new MusicTrack("Title3", artist);

        musicTracks.add(musicTrack1);
        musicTracks.add(musicTrack2);
        musicTracks.add(musicTrack3);

        playlist = new Playlist("Playlist1");
        playlist.addMusicTracks(musicTrack1);
        playlist.addMusicTracks(musicTrack2);
    }

    @Test
    public void testGetPlaylistName()
    {
        System.out.println("Testins, getPlaylistName");
        assertEquals("Playlist1", playlist.getPlaylistName());
        System.out.println("Finished. Testing getPlaylistName");
    }

    @Test
    public void testAddMusicTracks()
    {
        System.out.println("Testing, addition of music tracks");
        assertEquals(2, playlist.getPlaylist().size());
        playlist.addMusicTracks(musicTrack3);
        assertEquals(3, playlist.getPlaylist().size());
        System.out.println("Finished, Testing addition of music tracks");
    }

    @Test
    public void testRemovePlaylist()
    {
        System.out.println("Testing, removePlaylists");
        playlist.removePlaylist();
        assertNull(playlist.getPlaylist());
        System.out.println("Finished. Testing removePlaylists()");
    }
}