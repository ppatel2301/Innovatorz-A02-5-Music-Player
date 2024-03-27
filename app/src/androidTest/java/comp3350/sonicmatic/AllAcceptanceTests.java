package comp3350.sonicmatic;

import androidx.test.filters.LargeTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@LargeTest
@RunWith(Suite.class)
@Suite.SuiteClasses({
        MusicPlayerTest.class,
        UserProfileTest.class,
        ArtistProfileTest.class,
        CreatePlaylistTest.class,
        OrganizePlaylistTest.class,
        LeaderBoardTest.class,
        UserPlaybackHistoryTest.class,
        BrowseCatalogTest.class,
        DarkLightModeToggleTest.class
})

public class AllAcceptanceTests {
}