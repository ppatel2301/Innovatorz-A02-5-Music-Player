package comp3350.sonicmatic.objects;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

import static org.junit.Assert.*;

import comp3350.sonicmatic.interfaces.IArtist;
import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.interfaces.ISongLength;
import comp3350.sonicmatic.objects.abstracts.MusicTrackPlaylist;
import comp3350.sonicmatic.objects.musicartist.MusicArtist;
import comp3350.sonicmatic.objects.musictrack.MusicTrack;
import comp3350.sonicmatic.objects.songduration.SongDuration;

@RunWith(JUnit4.class)
public class MusicTrackPlaylistTest {

    private MusicTrackPlaylist playlist;

    //Tracks
    private ISong musicTrack1;
    private ISong musicTrack3;

    private String songPath;

    @Before
    public void setUp()
    {
        IArtist artist = new MusicArtist("Bob");
        ISongLength length = new SongDuration("42069");
        musicTrack1 = new MusicTrack("Title1", artist, length, songPath);
        MusicTrack musicTrack2 = new MusicTrack("Title2", artist, length, songPath);
        musicTrack3 = new MusicTrack("Title3", artist, length, songPath);

        ArrayList<ISong> musicTracks = new ArrayList<>();
        musicTracks.add(musicTrack1);
        musicTracks.add(musicTrack2);
        musicTracks.add(musicTrack3);

        playlist = new MusicTrackPlaylist("Playlist1");
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
    public void testRemoveMusicTracksFromPlaylist()
    {
        System.out.println("Testing, removeMusicTracks");
        playlist.removeMusicTracks(musicTrack1);
        assertEquals(1, playlist.getPlaylist().size());
        System.out.println("Finished, Testing removing music from playlist");
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