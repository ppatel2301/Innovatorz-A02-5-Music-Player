package comp3350.sonicmatic;

import androidx.test.filters.LargeTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@LargeTest
@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserPlaybackHistoryTest.class,
        MusicPlayerTest.class,
        UserProfileTest.class,
        ArtistProfileTest.class,
        CreatePlaylistTest.class,
        OrganizePlaylistTest.class,
        LeaderBoardTest.class,
        BrowseCatalogTest.class,
        DarkLightModeToggleTest.class,
        MusicTest.class
})

public class AllAcceptanceTests {
}