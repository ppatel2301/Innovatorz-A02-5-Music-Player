package comp3350.sonicmatic.integrationTests;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import comp3350.sonicmatic.application.Services;
import comp3350.sonicmatic.business.access.AccessPlaylist;
import comp3350.sonicmatic.business.access.AccessProfile;
import comp3350.sonicmatic.interfaces.IPlaylist;
import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.objects.musicArtist.MusicArtist;
import comp3350.sonicmatic.objects.musicTrack.MusicTrack;
import comp3350.sonicmatic.objects.songDuration.SongDuration;
import comp3350.sonicmatic.persistance.playlist.NullPlaylist;

public class AccessPlaylistTest
{

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
        accessProfile.login("PeterPumpkinEater", "megsux0000");
    }

    @After
    public void teardown()
    {
        accessProfile.logout();
    }

    @Test
    public void testGetPlaylists()
    {
        ArrayList<IPlaylist> playlists;

        accessPlaylist.newPlaylist("name1", accessProfile);
        accessPlaylist.newPlaylist("name2", accessProfile);
        accessPlaylist.newPlaylist("name3", accessProfile);

        playlists = accessPlaylist.getPlaylists(accessProfile);
        assertEquals("Access Playlist Test: unexpected number of user playlists.", 3, playlists.size());

        for (IPlaylist p : playlists)
        {
            assertEquals("Access Playlist Test: null playlist found", true, !p.getPlaylistName().equals(NullPlaylist.getNullPlaylist().getName()));
        }

        accessPlaylist.deletePlaylist("name1", accessProfile);
        accessPlaylist.deletePlaylist("name2", accessProfile);
        accessPlaylist.deletePlaylist("name3", accessProfile);
    }

    @Test
    public void testNewPlaylist()
    {
        boolean success = accessPlaylist.newPlaylist("my new playlist", accessProfile);

        assertEquals("Access Playlist Test: insertion was unssucessful.", true, success);

        accessPlaylist.deletePlaylist("my new playlist", accessProfile);
    }

    @Test
    public void testNewBadPlaylist()
    {
        final String DUP_ME = "duplicated";

        boolean no_dual_insert;
        boolean no_null_profile;

        accessPlaylist.newPlaylist(DUP_ME, accessProfile);

        no_dual_insert = !accessPlaylist.newPlaylist(DUP_ME, accessProfile);
        no_null_profile = !accessPlaylist.newPlaylist("nuh uh not null", null);

        assertEquals("Access Playlist Test: inserted duplicate data", true, no_dual_insert);
        assertEquals("Access Playlist Test: inserted with a null profile.", true, no_null_profile);

        accessPlaylist.deletePlaylist(DUP_ME, accessProfile);
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
        final String DELETE_ME = "to test";
        boolean success;

        // insert in here so we can be sure it was the null profile that made us not pass the test
        accessPlaylist.newPlaylist(DELETE_ME, accessProfile);

        success = !accessPlaylist.deletePlaylist(DELETE_ME, null);
        assertEquals("Access Playlist Test: deleted with null profile", true, success);

        accessPlaylist.deletePlaylist(DELETE_ME, accessProfile);
    }

    @Test
    public void testInsertIntoPlaylist()
    {
        final String PLAYLIST_NAME = "test";
        ISong insert_me = new MusicTrack("Drone Corpse Aviator", new MusicArtist("Archspire"), new SongDuration("123456"), "Drone Corpse Aviator.mp3");
        boolean success;

        accessPlaylist.newPlaylist(PLAYLIST_NAME, accessProfile);

        success = accessPlaylist.insertIntoPlaylist(PLAYLIST_NAME, insert_me, accessProfile);
        assertEquals("Access Playlist Test: failed to insert a song into a playlist.", true, success);

        accessPlaylist.deletePlaylist(PLAYLIST_NAME, accessProfile);
    }

    @Test
    public void testBadInsertIntoPlaylist()
    {
        final String PLAYLIST_NAME = "test";
        ISong duplicate_me = new MusicTrack("Drone Corpse Aviator", new MusicArtist("Archspire"), new SongDuration("123456"), "Drone Corpse Aviator.mp3");

        boolean no_dual_insert;
        boolean no_null_song;
        boolean no_null_profile;

        accessPlaylist.newPlaylist(PLAYLIST_NAME, accessProfile);
        accessPlaylist.insertIntoPlaylist(PLAYLIST_NAME, duplicate_me, accessProfile); // add duplicate

        no_dual_insert = !accessPlaylist.insertIntoPlaylist(PLAYLIST_NAME, duplicate_me, accessProfile);
        no_null_song = !accessPlaylist.insertIntoPlaylist(PLAYLIST_NAME, null, accessProfile);
        no_null_profile = !accessPlaylist.insertIntoPlaylist(PLAYLIST_NAME, duplicate_me, null);

        assertEquals("Access Playlist test: inserted duplicate data.", true, no_dual_insert);
        assertEquals("Access Playlist test: inserted null data.", true, no_null_song);
        assertEquals("Access Playlist test: inserted with null profile.", true, no_null_profile);

        accessPlaylist.deletePlaylist(PLAYLIST_NAME, accessProfile);
    }

    @Test
    public void testDeleteFromPlaylist()
    {
        final String PLAYLIST_NAME = "test";
        ISong delete_me = new MusicTrack("Drone Corpse Aviator", new MusicArtist("Archspire"), new SongDuration("123456"), "Drone Corpse Aviator.mp3");
        boolean success;

        accessPlaylist.newPlaylist(PLAYLIST_NAME, accessProfile);
        accessPlaylist.insertIntoPlaylist(PLAYLIST_NAME, delete_me, accessProfile);

        success = accessPlaylist.deleteFromPlaylist(PLAYLIST_NAME, delete_me, accessProfile);
        assertEquals("Access Playlist test: could not delete from playlist.", true, success);

        accessPlaylist.deletePlaylist(PLAYLIST_NAME, accessProfile);
    }

    @Test
    public void testDeleteFromPlaylistNullProfile()
    {
        final String PLAYLIST_NAME = "test";

        boolean null_profile;
        boolean null_song;

        ISong delete_me = new MusicTrack("Drone Corpse Aviator", new MusicArtist("Archspire"), new SongDuration("123456"), "Drone Corpse Aviator.mp3");

        // just to make sure that it was the null profile or song that causes the fail we want
        accessPlaylist.newPlaylist(PLAYLIST_NAME, accessProfile);
        accessPlaylist.insertIntoPlaylist(PLAYLIST_NAME, delete_me, accessProfile);

        null_profile = !accessPlaylist.deleteFromPlaylist(PLAYLIST_NAME, delete_me, null);
        null_song = !accessPlaylist.deleteFromPlaylist(PLAYLIST_NAME, null, accessProfile);

        assertEquals("Access Playlist Test: deleted song from playlist with null profile", true, null_profile);
        assertEquals("Access Playlist Test: deleted null song from playlist", true, null_song);

        accessPlaylist.deletePlaylist(PLAYLIST_NAME, accessProfile);
    }

}
