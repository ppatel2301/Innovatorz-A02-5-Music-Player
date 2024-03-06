package comp3350.sonicmatic.objects;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import comp3350.sonicmatic.interfaces.ISongLength;
import comp3350.sonicmatic.objects.songduration.SongDuration;

public class SongDurationTest {

    private final int MILLIS = 420690;

    // calculate the expected minutes, last seconds, and total seconds
    private final int MINUTES = Math.round((MILLIS / 1000.0f))/60;
    private final int LAST_SECS = Math.round(((Math.round((MILLIS / 1000.0f))/60.0f) - MINUTES) * 60);
    private final int TOTAL_SECS = (MINUTES * 60) + LAST_SECS;

    private final String MILLIS_STR = ""+MILLIS;
    private ISongLength duration;

    @Before
    public void setup()
    {
        duration = new SongDuration(MILLIS_STR);
    }

    @After
    public void teardown()
    {
        duration = null;
    }

    @Test
    public void getMinutesTest()
    {
        int minutes = duration.getMinutes();

        assertEquals("Minutes do not match match the expected", true, minutes == MINUTES);
    }

    @Test
    public void getLastSecondsTest()
    {
        int lastSeconds = duration.getLastSeconds();

        assertEquals("Last seconds do not match the expected", true, lastSeconds == LAST_SECS);
    }

    @Test
    public void getTotalSecondsTest()
    {
        int totalSeconds = duration.getTotalSeconds();

        assertEquals("Total seconds do not match the expected", true, totalSeconds == TOTAL_SECS);
    }

    @Test
    public void invalidMillisStringTest()
    {
        ISongLength songLength = new SongDuration("haha not a number! LOL :p");

        assertEquals("Invalid data not recognized as invalid", true, songLength.getMinutes() == SongDuration.NO_DATA && songLength.getLastSeconds() == SongDuration.NO_DATA);
    }

    @Test
    public void negativeMillisStringTest()
    {
        ISongLength songLength = new SongDuration("-"+MILLIS_STR);

        assertEquals("Invalid data not recognized as invalid", true, songLength.getMinutes() == SongDuration.NO_DATA && songLength.getLastSeconds() == SongDuration.NO_DATA);
    }
}