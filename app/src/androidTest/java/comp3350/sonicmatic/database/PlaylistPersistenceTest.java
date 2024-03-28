package comp3350.sonicmatic.database;

import static org.junit.Assert.assertEquals;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import comp3350.sonicmatic.application.Services;
import comp3350.sonicmatic.persistance.playlist.NullPlaylist;
import comp3350.sonicmatic.persistance.playlist.Playlist;
import comp3350.sonicmatic.persistance.playlist.PlaylistPersistence;

public class PlaylistPersistenceTest
{

    private PlaylistPersistence playlistPersistence = null;

    @Before
    public void setUp()
    {
        Services.setContext(InstrumentationRegistry.getInstrumentation().getTargetContext());
        playlistPersistence = Services.getPlaylistPersistence();
    }

    @Test
    public void testGetByPlaylistNameUser()
    {
        Playlist playlist = new Playlist("PeterPumpkinEater", "new playlist name");
        Playlist retrieved;

        playlistPersistence.insert(playlist);

        retrieved = playlistPersistence.get(playlist.getName(), playlist.getCreatorUsername());

        assertEquals("Playlist Test: NullPlaylist returned.", true,
                retrieved.getCreatorUsername().equals(playlist.getCreatorUsername())
                   && retrieved.getName().equals(playlist.getName()));

        playlistPersistence.delete(playlist);
    }

    @Test
    public void badTestGetByPlaylistNameUser()
    {
        Playlist retrieved = playlistPersistence.get("not in db", "also not in db");

        assertEquals("Playlist Test: NullPlaylist wasn't returned.", true,
                retrieved.getCreatorUsername().equals(NullPlaylist.getNullPlaylist().getCreatorUsername())
                        && retrieved.getName().equals(NullPlaylist.getNullPlaylist().getName()));
    }

    @Test
    public void testGetUserPlaylists()
    {
        Playlist playlist1 = new Playlist("PeterPumpkinEater", "new playlist name1");
        Playlist playlist2 = new Playlist("PeterPumpkinEater", "new playlist name2");

        playlistPersistence.insert(playlist1);
        playlistPersistence.insert(playlist2);

        ArrayList<Playlist> userPlaylists = playlistPersistence.getPlaylistsOfUser("PeterPumpkinEater");

        // expecting two non-null playlists
        assertEquals("Playlist Test: unexpected amount of user playlists.", 2, userPlaylists.size());
        for (Playlist p : userPlaylists)
        {
            assertEquals("Playlist Test: NullPlaylist inside user playlists.", true, !p.getName().equals(NullPlaylist.getNullPlaylist().getName()));
        }

        playlistPersistence.delete(playlist1);
        playlistPersistence.delete(playlist2);
    }

    @Test
    public void testBadGetUserPlaylists()
    {
        // username not associated with any playlist? Shouldn't return any playlists then
        ArrayList<Playlist> userPlaylists = playlistPersistence.getPlaylistsOfUser("not here");

        assertEquals("Playlist test: the list of playlists wasn't empty when it should have been", true, userPlaylists.isEmpty());
    }
    @Test
    public void testUpdate()
    {
        final String NEW_NAME = "new name123";

        Playlist updated;
        Playlist change_me = new Playlist("Gary", "new playlist to change");

        playlistPersistence.insert(change_me);

        // pointer reassignment, get the playlist with the ID
        change_me = playlistPersistence.get("new playlist to change", "Gary");

        // change the name here in memory, then put it back into storage
        change_me.changeName(NEW_NAME);
        updated = playlistPersistence.update(change_me);

        assertEquals("Playlist test: the playlist failed to update. New name was "+updated.getName(), true, updated.getName().equals(NEW_NAME));

        playlistPersistence.delete(change_me);
    }

    @Test
    public void testInsert()
    {
        Playlist to_insert = new Playlist("PeterPumpkinEater", "newest playlist");

        boolean success = playlistPersistence.insert(to_insert);

        assertEquals("Playlist test: playlist failed to insert.", true, success);

        // delete now that we're done
        playlistPersistence.delete(to_insert);
    }

    @Test
    public void testBadInsert()
    {
        boolean success1 = !playlistPersistence.insert(new Playlist(0, "My First Playlist", "Profile11")); // should already be inside
        boolean success2 = !playlistPersistence.insert(new Playlist(121, "My First Playlist", "Profile11")); // different id but same creator-name combo

        assertEquals("Playlist test: clone playlist inserted successfully when it shouldn't have.", true, success1);
        assertEquals("Playlist test: clone playlist (different id) inserted successfully when it shouldn't have.", true, success2);
    }

    @Test
    public void testDelete()
    {
        // insert a playlist to delete
        Playlist insert_me = new Playlist("Gary", "The newest playlist");
        boolean success;

        playlistPersistence.insert(insert_me);
        success = playlistPersistence.delete(insert_me);

        assertEquals("Playlist Test: Playlist could not be deleted.", true, success);
    }

    @Test
    public void testNullDelete()
    {
        boolean success = !playlistPersistence.delete(null);

        assertEquals("Playlist Test: deleted null?", true, success);
    }
}
