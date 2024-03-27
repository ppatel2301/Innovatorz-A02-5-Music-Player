package comp3350.sonicmatic.integrationTests;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import comp3350.sonicmatic.application.Services;
import comp3350.sonicmatic.business.AccessPlaylist;
import comp3350.sonicmatic.business.AccessProfile;
import comp3350.sonicmatic.interfaces.IPlaylist;
import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.objects.musicArtist.MusicArtist;
import comp3350.sonicmatic.objects.musicTrack.MusicTrack;
import comp3350.sonicmatic.objects.songDuration.SongDuration;

public class AccessPlaylistTest
{

    // ** class constants **
    private static final String PLAYLIST_NAME = "My First Playlist";
    private static final String CREATOR_NAME = "Profile11";
    private static final int ID = 0;
    private static final String SONG_PATH = "Archetype.mp3"; // has to be real and in assets
    private static final String SONG_NAME = "Archetype";
    private static final String PROFILE_PWD ="comp3350";

    private Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private AccessPlaylist accessPlaylist;

    // need to "login" to this "profile"
    private AccessProfile accessProfile;

    @Before
    public void setup()
    {
        Services.setContext(context);
        accessPlaylist = new AccessPlaylist(Services.getPlaylistPersistence(), Services.getPlaylistSongPersistence());

        accessProfile = new AccessProfile(Services.getProfilePersistence());
        accessProfile.login(CREATOR_NAME, PROFILE_PWD);
    }

    @After
    public void teardown()
    {
        accessProfile.logout();
    }

    @Test
    public void testGetPlaylists()
    {
        ArrayList<IPlaylist> playlists = accessPlaylist.getPlaylists(accessProfile);

        assertEquals("Access Playlist Test: unexpected number of user playlists.", 1, playlists.size());

        if (!playlists.isEmpty())
        {
            assertEquals("Access Playlist Test: unexpected playlist creator name.", true, playlists.get(0).getPlaylistName().equals(PLAYLIST_NAME));

            // shouldn't be empty as we've linked one song to this playlist
            assertEquals("Access Playlist Test: unexpected number of songs in the playlist.", 1, playlists.get(0).getPlaylist().size());

            if (!playlists.get(0).getPlaylist().isEmpty())
            {
                assertEquals("Access Playlist Test: unexpected playlist song name.", true, playlists.get(0).getPlaylist().get(0).getTitle().equals(SONG_NAME));
            }

        }

    }

    @Test
    public void testNewPlaylist()
    {
        boolean success = accessPlaylist.newPlaylist("my new playlist", accessProfile);

        assertEquals("Access Playlist Test: insertion was unssucessful.", true, success);
    }

    @Test
    public void testNewBadPlaylist()
    {
        boolean no_dual_insert = !accessPlaylist.newPlaylist(PLAYLIST_NAME, accessProfile); // playlist already inside
        boolean no_null_profile = !accessPlaylist.newPlaylist("new name!!!", null);


        assertEquals("Access Playlist Test: inserted duplicate data", true, no_dual_insert);
        assertEquals("Access Playlist Test: inserted with a null profile.", true, no_null_profile);
    }

    @Test
    public void testDeletePlaylist()
    {
        final String DEL_NAME = "hi delete me!";
        boolean success;

        // insert a playlist to delete first
        accessPlaylist.newPlaylist(DEL_NAME, accessProfile);

        success = accessPlaylist.deletePlaylist(DEL_NAME, accessProfile);

        assertEquals("Access Playlist Test: deleteion failed", true, success);
    }

    @Test
    public void testDeletePlaylistNullProfile()
    {
        boolean success = !accessPlaylist.deletePlaylist("don't care won't delete anyway", null);

        assertEquals("Access Playlist Test: deleted with null profile", true, success);
    }

    @Test
    public void testInsertIntoPlaylist()
    {
        ISong insert_me = new MusicTrack("Drone Corpse Aviator", new MusicArtist("Archspire"), new SongDuration("123456"), "Drone Corpse Aviator.mp3");

        boolean success = accessPlaylist.insertIntoPlaylist(PLAYLIST_NAME, insert_me, accessProfile);

        assertEquals("Access Playlist Test: failed to insert a song into a playlist.", true, success);
    }

    @Test
    public void testBadInsertIntoPlaylist()
    {
        ISong in_db = new MusicTrack(SONG_NAME, new MusicArtist("SONG ARTIST"), new SongDuration("123456"), SONG_PATH);

        boolean no_dual_insert = !accessPlaylist.insertIntoPlaylist(PLAYLIST_NAME, in_db, accessProfile);
        boolean no_null_song = !accessPlaylist.insertIntoPlaylist(PLAYLIST_NAME, null, accessProfile);
        boolean no_null_profile = !accessPlaylist.insertIntoPlaylist(PLAYLIST_NAME, in_db, null);

        assertEquals("Access Playlist test: inserted duplicate data.", true, no_dual_insert);
        assertEquals("Access Playlist test: inserted null data.", true, no_null_song);
        assertEquals("Access Playlist test: inserted with null profile.", true, no_null_profile);
    }

    @Test
    public void testDeleteFromPlaylist()
    {
        ISong delete_me = new MusicTrack("Golden Mouth of Ruin", new MusicArtist("Archspire"), new SongDuration("919191"), "Golden Mouth Of Ruin.mp3");
        boolean success;

        accessPlaylist.insertIntoPlaylist(PLAYLIST_NAME, delete_me, accessProfile);

        success = !accessPlaylist.deleteFromPlaylist(PLAYLIST_NAME, delete_me, accessProfile);

        assertEquals("Access Playlist test: could not delete from playlist.", true, success);
    }

    @Test
    public void testDeleteFromPlaylistNullProfile()
    {
        boolean success = !accessPlaylist.deleteFromPlaylist(PLAYLIST_NAME, null, null);

        assertEquals("Access Playlist Test: deleted song from playlist with null profile", true, success);

    }

}
