package comp3350.sonicmatic;

import androidx.test.filters.LargeTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@LargeTest
@RunWith(Suite.class)
@Suite.SuiteClasses({
        MusicPlayerTest.class,
        UserProfileTest.class,
        CreatePlaylistTest.class,
        OrganizePlaylistTest.class,
        LeaderBoardTest.class,
        DarkLightModeToggleTest.class,
        UserPlaybackHistoryTest.class
})

public class AllAcceptanceTests {
}

