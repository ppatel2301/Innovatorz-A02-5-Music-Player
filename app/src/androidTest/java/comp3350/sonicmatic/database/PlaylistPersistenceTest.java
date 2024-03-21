package comp3350.sonicmatic.database;

import static org.junit.Assert.assertEquals;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import comp3350.sonicmatic.application.Services;
import comp3350.sonicmatic.persistance.playlist.NullPlaylist;
import comp3350.sonicmatic.persistance.playlist.Playlist;
import comp3350.sonicmatic.persistance.playlist.PlaylistPersistence;

public class PlaylistPersistenceTest {


    private PlaylistPersistence playlistPersistence = null;

    private static final String USER_1 = "kreator";
    private static final String USER_2 = "other guy";

    private static Playlist playlist1 = new Playlist(USER_1, "myfirstplist");
    private static Playlist playlist2 = new Playlist(USER_1, "my2ndplist");

    @Before
    public void setUp()
    {
        Services.setContext(InstrumentationRegistry.getInstrumentation().getTargetContext());
        playlistPersistence = Services.getPlaylistPersistence();

        playlistPersistence.insert(playlist1);
        playlistPersistence.insert(playlist2);
    }

    @After
    public void teardown()
    {
        if (playlistPersistence != null)
        {
            playlistPersistence.delete(playlist1);
            playlistPersistence.delete(playlist2);
        }
    }

    @Test
    public void testGetByPlaylistNameUser()
    {
        Playlist retrieved = playlistPersistence.get(playlist1.getName(), playlist1.getCreatorUsername());

        assertEquals("Playlist Test: NullPlaylist returned.", true,
                retrieved.getCreatorUsername().equals(playlist1.getCreatorUsername())
                   && retrieved.getName().equals(playlist1.getName()));
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
        ArrayList<Playlist> userPlaylists = playlistPersistence.getPlaylistsOfUser(USER_1);

        // expecting two non-null playlists
        assertEquals("Playlist Test: unexpected amount of user playlists.", true, userPlaylists.size() == 2);
        if (userPlaylists.size() == 2)
        {
            assertEquals("Playlist test: first playlist of a user was NullPlaylist.", true, userPlaylists.get(0).getCreatorUsername().equals(playlist1.getCreatorUsername())
                    && userPlaylists.get(0).getName().equals(playlist1.getName()));
            assertEquals("Playlist test: second playlist of a user was NullPlaylist.", true, userPlaylists.get(1).getCreatorUsername().equals(playlist2.getCreatorUsername())
                    && userPlaylists.get(1).getName().equals(playlist2.getName()));
        }
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
        final String new_name = "new name123";

        // just change a playlist  name given a playlist ID (will have to access db to get this)
        Playlist withID = playlistPersistence.get(playlist1.getName(), playlist1.getCreatorUsername());
        Playlist updated = new Playlist(withID.getId(), withID.getCreatorUsername(), new_name);
        Playlist updatedDB = playlistPersistence.update(updated);

        assertEquals("Playlist test: the playlist failed to update.", true, updatedDB.getName().equals(new_name));
    }

    @Test
    public void testInsert()
    {
        Playlist to_insert = new Playlist(USER_1, "newest playlist");

        boolean success = playlistPersistence.insert(to_insert);

        assertEquals("Playlist test: playlist failed to insert.", true, success);
    }

    @Test
    public void testBadInsert()
    {
        boolean success1 = !playlistPersistence.insert(playlist1); // should already be inside
        boolean success2 = !playlistPersistence.insert(new Playlist(playlist1.getCreatorUsername(), playlist1.getName())); // different id but same creator-name combo

        assertEquals("Playlist test: clone playlist inserted successfully when it shouldn't have.", true, success1);
        assertEquals("Playlist test: clone playlist (different id) inserted successfully when it shouldn't have.", true, success2);
    }

    @Test
    public void testDelete()
    {
        boolean success = playlistPersistence.delete(playlist1);

        assertEquals("Playlist Test: Playlist could not be deleted.", true, success);
    }

    @Test
    public void testBadDelete()
    {
        boolean success = !playlistPersistence.delete(new Playlist(69, "not here", "not here!"));

        assertEquals("Playlist Test: Playlist was deleted when it wasn't inside to begin with.", true, success);
    }

    @Test
    public void getPlaylistsOfUser()
    {
        ArrayList<Playlist> playlists = playlistPersistence.getPlaylistsOfUser(USER_1);

        boolean expected_count = playlists.size() == 2;
        boolean success = false;

        if (expected_count)
        {
            success = playlists.get(0).getCreatorUsername().equals(USER_1) && playlists.get(0).getName().equals(playlist1.getName())
                  &&  playlists.get(1).getCreatorUsername().equals(USER_1) && playlists.get(1).getName().equals(playlist2.getName());
        }

        assertEquals("Playlist Test: Unexpected number of playlists.", true, expected_count);
        assertEquals("Playlist Test: Unexpected playlist data encountered", true, success);
    }

    @Test
    public void testNullDelete()
    {
        boolean success = !playlistPersistence.delete(null);

        assertEquals("Playlist Test: deleted null?", true, success);
    }
}
