package comp3350.sonicmatic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


import comp3350.sonicmatic.database.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MusicPlayerTest.class,
        ProfilePersistenceTest.class,
        SongPersistenceTest.class
})

public class AllTests
{

}