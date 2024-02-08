package comp3350.sonicmatic;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.sonicmatic.objects.AlbumTest;
import comp3350.sonicmatic.objects.PlaylistTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AlbumTest.class,
        PlaylistTest.class
})

public class AllTests
{

}