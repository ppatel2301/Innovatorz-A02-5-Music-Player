package comp3350.sonicmatic;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


import comp3350.sonicmatic.objects.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AlbumTest.class,
        PlaylistTest.class,
        MusicArtistTest.class,
        MusicTrackTest.class,
        SongDurationTest.class
})

public class AllTests
{

}