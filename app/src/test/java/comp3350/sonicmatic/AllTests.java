package comp3350.sonicmatic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.sonicmatic.objects.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AlbumTest.class,
        MusicTrackPlaylistTest.class,
        MusicArtistTest.class,
        MusicTrackTest.class,
        SongDurationTest.class
})

public class AllTests
{

}