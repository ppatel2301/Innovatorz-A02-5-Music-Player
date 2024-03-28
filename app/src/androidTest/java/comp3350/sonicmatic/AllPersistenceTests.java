package comp3350.sonicmatic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


import comp3350.sonicmatic.database.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PlaylistPersistenceTest.class,
        PlaylistSongPersistenceTest.class,
        ProfilePersistenceTest.class,
        SongPersistenceTest.class,
        LeaderboardPersistenceTest.class
})

public class AllPersistenceTests
{

}