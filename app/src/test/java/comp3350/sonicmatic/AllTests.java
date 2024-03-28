package comp3350.sonicmatic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.sonicmatic.business.accessLeaderboardTest.AccessLeaderboardTest;
import comp3350.sonicmatic.objects.*;
import comp3350.sonicmatic.business.accessProfileTest.AccessProfileTest;
import comp3350.sonicmatic.business.accessSongTest.AccessSongTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AlbumTest.class,
        MusicTrackPlaylistTest.class,
        MusicArtistTest.class,
        MusicTrackTest.class,
        SongDurationTest.class,
        AccessProfileTest.class,
        AccessSongTest.class,
        AccessLeaderboardTest.class
})

public class AllTests
{

}