package comp3350.sonicmatic.database;

import static org.junit.Assert.*;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import comp3350.sonicmatic.application.Services;
import comp3350.sonicmatic.persistance.playlistSong.NullPlaylistSong;
import comp3350.sonicmatic.persistance.playlistSong.PlaylistSong;
import comp3350.sonicmatic.persistance.playlistSong.PlaylistSongPersistence;

public class PlaylistSongPersistenceTest {

    private PlaylistSongPersistence psp = null; // play station portable!

    @Before
    public void setUp()
    {
        Services.setContext(InstrumentationRegistry.getInstrumentation().getTargetContext());
        psp = Services.getPlaylistSongPersistence();
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
        PlaylistSong in_db = new PlaylistSong("Archetype.mp3", 0);
        PlaylistSong retrieved = psp.get(in_db.makePrimaryKey());

        assertEquals("PSP Test: Didn't get expected playlist song. Was "+retrieved.getFileNameExt(), true,
                retrieved.equals(in_db));
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
        PlaylistSong in_db = new PlaylistSong("Archetype.mp3", 0);

        ArrayList<PlaylistSong> songs = psp.getPlaylistSongs(in_db.getPlaylistId()+"");
        PlaylistSong retrieved;
        // should get only one song, that being the test playlist song

        assertEquals("PSP Test: array list returned had unexpected length.", 1, songs.size());
        if (!songs.isEmpty())
        {
            retrieved = songs.get(0);
            assertEquals("PSP Test: song in the list was unexpected.", true,
                    retrieved.equals(in_db));
        }
    }

    @Test
    public void testBadGetPlaylistSongs()
    {
        ArrayList<PlaylistSong> songs = psp.getPlaylistSongs("1235");

        // should get empty array list
        assertEquals("PSP Test: array list returned had unexpected length.", true, songs.isEmpty());

    }

    @Test
    public void testUpdate()
    {
        PlaylistSong in_db = new PlaylistSong("Archetype.mp3", 0);
        PlaylistSong updated = new PlaylistSong(in_db.getFileNameExt()+"!", in_db.getPlaylistId());

        updated = psp.update(updated);

        assertEquals("PSP Test: Didn't get NullPlaylistSong as expected.", true,
                updated.equals(NullPlaylistSong.getNullPlaylistSong()));

    }

    @Test
    public void testInsert()
    {
        PlaylistSong newPS = new PlaylistSong("insert me into the DB", 1021);

        boolean success = psp.insert(newPS);
        PlaylistSong retrieved = psp.get(newPS.makePrimaryKey());

        assertEquals("PSP Test: unsuccessful insert.", true, success);
        assertEquals("PSP Test: song inserted and retrieved the list was unexpected.", true,
                retrieved.equals(newPS));
    }

    @Test
    public void testBadInsert()
    {
        PlaylistSong in_db = new PlaylistSong("Archetype.mp3", 0);
        boolean success = !psp.insert(in_db); // already in DB so this insert should fail

        assertEquals("PSP Test: successful insert when we wanted it to fail.", true, success);
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

    // do we want to return false if the item isn't in the DB?
    /*
    @Test
    public void testBadDelete()
    {
        PlaylistSong not_in_db = new PlaylistSong("not here", 10101);
        boolean success = !psp.delete(not_in_db);

        assertEquals("PSP Test: delete succeeded when it should have failed.", true, success);
    }
    */

    @Test
    public void testNullDelete()
    {
        boolean success = !psp.delete(null);

        assertEquals("PSP Test: deleted null?", true, success);
    }
}