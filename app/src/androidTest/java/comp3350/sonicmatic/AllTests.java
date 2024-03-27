package comp3350.sonicmatic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


import comp3350.sonicmatic.database.*;
import comp3350.sonicmatic.integrationTests.AccessPlaylistTest;
import comp3350.sonicmatic.integrationTests.AccessProfileTest;
import comp3350.sonicmatic.integrationTests.AccessSongTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MusicPlayerTest.class,
        ProfilePersistenceTest.class,
        SongPersistenceTest.class,
        PlaylistSongPersistenceTest.class,
        PlaylistPersistenceTest.class,
        AccessPlaylistTest.class,
        AccessProfileTest.class,
        AccessSongTest.class
})

public class AllTests
{

}