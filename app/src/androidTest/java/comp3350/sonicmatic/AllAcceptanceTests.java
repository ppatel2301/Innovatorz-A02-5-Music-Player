package comp3350.sonicmatic;

import androidx.test.filters.LargeTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.sonicmatic.systemTests.ArtistProfileTest;
import comp3350.sonicmatic.systemTests.BrowseCatalogTest;
import comp3350.sonicmatic.systemTests.CreatePlaylistTest;
import comp3350.sonicmatic.systemTests.DarkLightModeToggleTest;
import comp3350.sonicmatic.systemTests.LeaderBoardTest;
import comp3350.sonicmatic.systemTests.MusicPlayerTest;
import comp3350.sonicmatic.systemTests.MusicTest;
import comp3350.sonicmatic.systemTests.OrganizePlaylistTest;
import comp3350.sonicmatic.systemTests.UserPlaybackHistoryTest;
import comp3350.sonicmatic.systemTests.UserProfileTest;

@LargeTest
@RunWith(Suite.class)
@Suite.SuiteClasses({
        MusicTest.class,
        UserPlaybackHistoryTest.class,
        UserProfileTest.class,
        ArtistProfileTest.class,
        CreatePlaylistTest.class,
        OrganizePlaylistTest.class,
        LeaderBoardTest.class,
        BrowseCatalogTest.class,
        DarkLightModeToggleTest.class
})

public class AllAcceptanceTests {
}