package comp3350.sonicmatic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.sonicmatic.integrationTests.AccessPlaylistTest;
import comp3350.sonicmatic.integrationTests.AccessProfileTest;
import comp3350.sonicmatic.integrationTests.AccessSongTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessPlaylistTest.class,
        AccessProfileTest.class,
        AccessSongTest.class
})

public class AllIntegrationTests
{

}
