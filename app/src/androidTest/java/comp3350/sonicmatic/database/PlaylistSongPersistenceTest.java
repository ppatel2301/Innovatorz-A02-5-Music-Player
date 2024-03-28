package comp3350.sonicmatic.database;

import static org.junit.Assert.*;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import comp3350.sonicmatic.application.Services;
import comp3350.sonicmatic.persistance.playlist.Playlist;
import comp3350.sonicmatic.persistance.playlist.PlaylistPersistence;
import comp3350.sonicmatic.persistance.playlistSong.NullPlaylistSong;
import comp3350.sonicmatic.persistance.playlistSong.PlaylistSong;
import comp3350.sonicmatic.persistance.playlistSong.PlaylistSongPersistence;
import comp3350.sonicmatic.persistance.profile.Profile;
import comp3350.sonicmatic.persistance.profile.ProfilePersistence;

public class PlaylistSongPersistenceTest
{

    private PlaylistSongPersistence psp = null; // play station portable!

    // will need these for Foreign Key constraints
    private PlaylistPersistence playlistPersistence = null;
    private Playlist playlist = null;

    @Before
    public void setUp()
    {
        Services.setContext(InstrumentationRegistry.getInstrumentation().getTargetContext());
        psp = Services.getPlaylistSongPersistence();
        playlistPersistence = Services.getPlaylistPersistence();

        playlist = new Playlist("PeterPumpkinEater", "test playlist");

        playlistPersistence.insert(playlist);
    }

    @After
    public void teardown()
    {
        playlistPersistence.delete(playlist);
    }

    @Test
    public void testGetByOnePK()
    {
        PlaylistSong retrieved = psp.get(""); // always gives null, need two keys

        assertEquals("PSP Test: Didn't get NullPlaylistSong as expected.", true,
                retrieved.equals(NullPlaylistSong.getNullPlaylistSong()));
    }

    @Test
    public void testGet()
    {
        PlaylistSong to_get = new PlaylistSong("Archetype.mp3", 0);
        PlaylistSong retrieved;

        psp.insert(to_get);
        retrieved = psp.get(to_get.makePrimaryKey());

        assertEquals("PSP Test: Didn't get expected playlist song. Was "+retrieved.getFileNameExt(), true,
                retrieved.equals(to_get));

        psp.delete(to_get);
    }

    @Test
    public void testBadGet()
    {
        PlaylistSong retrieved = psp.get(new String[]{"not here!", "123"});

        assertEquals("PSP Test: Didn't get NullPlaylistSong as expected.", true,
                retrieved.equals(NullPlaylistSong.getNullPlaylistSong()));
    }

    @Test
    public void testGetPlaylistSongs()
    {
        PlaylistSong song1 = new PlaylistSong("Archetype.mp3", 0);
        PlaylistSong song2 = new PlaylistSong("Cyberwaste.mp3", 0);

        psp.insert(song1);
        psp.insert(song2); // woohoo

        ArrayList<PlaylistSong> songs = psp.getPlaylistSongs(0);

        assertEquals("PSP Test: array list returned had unexpected length.", 2, songs.size());

        for (PlaylistSong ps : songs)
        {
            assertEquals("PSP Test: null song in playlist songs.", true, !ps.equals(NullPlaylistSong.getNullPlaylistSong()));
        }

        psp.delete(song1);
        psp.delete(song2);
    }

    @Test
    public void testBadGetPlaylistSongs()
    {
        ArrayList<PlaylistSong> songs = psp.getPlaylistSongs(12345);

        // should get empty array list
        assertEquals("PSP Test: array list returned had unexpected length.", true, songs.isEmpty());

    }

    @Test
    public void testUpdate()
    {
        PlaylistSong updated = psp.update(null);

        assertEquals("PSP Test: Didn't get NullPlaylistSong as expected.", true,
                updated.equals(NullPlaylistSong.getNullPlaylistSong()));

    }

    @Test
    public void testInsert()
    {
        PlaylistSong new_ps = new PlaylistSong("Golden Mouth Of Ruin.mp3", 0);

        boolean success = psp.insert(new_ps);
        PlaylistSong retrieved = psp.get(new_ps.makePrimaryKey());

        assertEquals("PSP Test: unsuccessful insert.", true, success);
        assertEquals("PSP Test: song inserted and retrieved the list was unexpected.", true,
                retrieved.equals(new_ps));

        // delete now that we're done
        psp.delete(new_ps);
    }

    @Test
    public void testBadInsert()
    {
        PlaylistSong in_db = new PlaylistSong("Archetype.mp3", 0);
        boolean success;

        psp.insert(in_db);

        success = !psp.insert(in_db); // already in DB so this insert should fail

        assertEquals("PSP Test: successful insert when we wanted it to fail.", true, success);

        psp.delete(in_db);
    }

    @Test
    public void testDelete()
    {
        PlaylistSong to_delete = new PlaylistSong("Combustion.mp3", 0);

        boolean success;

        psp.insert(to_delete);
        success = psp.delete(to_delete);

        assertEquals("PSP Test: delete failed.", true, success);
    }

    @Test
    public void testNullDelete()
    {
        boolean success = !psp.delete(null);

        assertEquals("PSP Test: deleted null?", true, success);
    }
}