package comp3350.sonicmatic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


import comp3350.sonicmatic.database.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ProfilePersistenceTest.class,
        SongPersistenceTest.class,
        PlaylistSongPersistenceTest.class,
        PlaylistPersistenceTest.class
})

public class AllTests
{

}