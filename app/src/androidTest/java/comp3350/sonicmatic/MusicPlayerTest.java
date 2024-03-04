package comp3350.sonicmatic;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import comp3350.sonicmatic.musicplayer.MusicPlayer;
import comp3350.sonicmatic.exceptions.NoMusicException;
import comp3350.sonicmatic.interfaces.IPlayer;
import comp3350.sonicmatic.interfaces.ISong;

public class MusicPlayerTest {

    private IPlayer player;
    private Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    private final String testSong = "music/Archetype.mp3";

    @Before
    public void setup()
    {
        MusicPlayer.context = context;
        player = new MusicPlayer();
        player.loadSongFromPath(testSong);
    }

    @After
    public void teardown()
    {
        player = null;
    }

    @Test
    public void startTest()
    {
        boolean thrown = false;

        try
        {
            player.start();

            assertEquals("Player should play a song", true, player.isPlaying());

            player.stop();

        } catch (NoMusicException nme)
        {
            thrown = true;
        }

        assertEquals("No Music Exception Thrown", false, thrown);

    }

    @Test
    public void stopTest()
    {
        boolean thrown = false;

        try
        {
            player.start();
            player.stop();

        } catch (NoMusicException nme)
        {
            thrown = true;
        }

        assertEquals("Player should be stopped", true, player.isStopped());
        assertEquals("No Music Exception thrown", false, thrown);

    }

    @Test
    public void pauseTest()
    {
        boolean thrown = false;

        try
        {
            player.start();
            player.pause();

            assertEquals("Player should pause", true, player.isPaused());

            player.stop();

        } catch (NoMusicException nme)
        {
            thrown = true;
        }

        assertEquals("No Music Exception thrown", false, thrown);

    }

    @Test
    public void resumeTest()
    {
        int millisAfterPause = MusicPlayer.NO_MUSIC;
        int millisAfterResume;

        boolean thrown = false;

        try
        {
            player.start();
            player.pause();

            millisAfterPause = player.getMillisecPosition();

            player.resume();

        } catch (NoMusicException nme)
        {
            thrown = true;
        }

        assertEquals("Player should resume", true, player.isPlaying());

        millisAfterResume = player.getMillisecPosition();

        assertEquals("Player should have resumed after pause location. Exception may have occured.", true, millisAfterPause <= millisAfterResume && millisAfterPause != MusicPlayer.NO_MUSIC);

        assertEquals("No Music Exception thrown", false, thrown);
    }

    @Test
    public void seekTest()
    {

        final int seekToMillis = 420; // seek to this millisecond
        int postSeek;

        boolean thrown = false;

        try
        {
            player.start();
            player.pause();

            player.seek(seekToMillis);
        } catch (NoMusicException nme)
        {
            thrown = true;
        }

        postSeek = player.getMillisecPosition();

        assertEquals("Player should have seeked to correct location", true, postSeek == seekToMillis);
        assertEquals("No Music Exception thrown", false, thrown);
    }

    @Test
    public void seekTestStopped()
    {
        final int seekToMillis = 420;
        int postSeek;

        boolean thrown = false;

        try
        {
            player.start();
            player.stop(); // will destroy media player

            player.seek(seekToMillis);

        } catch (NoMusicException nme)
        {
            thrown = true;
        }
        assertEquals("No Music Exception should have been thrown", true, thrown);

    }

    @Test
    public void seekTestBeyond()
    {
        final int seekToMillis = Integer.MAX_VALUE; // probably shoudln't seek here...
        int postSeek;

        boolean thrown = false;

        try
        {
            player.start();
            player.seek(seekToMillis);

        } catch(NoMusicException nme)
        {
            thrown = true;
        }

        assertEquals("Exception wasn't thrown when we seeked too far", true, thrown);

    }

    @Test
    public void getMillisecDurationTest()
    {

        final int EXPECTED = 275000; // test song is 4 minutes and 35 seconds long, exactly 275000 milliseconds
        final int THRESHOLD = 275999;

        boolean thrown = false;
        int duration = player.getMillisecDuration();

        // expecting 4:35, which is exactly 275000 milliseconds
        // will test between 275000 and 275999 to account for any fractions of seconds that could exist

        assertEquals("Invalid millisecond count", true, EXPECTED <= duration && duration <= THRESHOLD);

    }

    public void getMillisecDurationTestNoSong()
    {

        boolean thrown = false;
        int duration;

        try
        {

            // start then destroy to get null media player
            player.start();
            player.stop();

        } catch (NoMusicException nme)
        {
            thrown = true;
        }

        duration = player.getMillisecDuration();

        assertEquals("Duration should be NO_MUSIC", true, duration == MusicPlayer.NO_MUSIC);
        assertEquals("No Music Exception thrown", false, thrown);
    }


    @Test
    public void getMillisecPositionTestNoSong()
    {
        boolean thrown = false;
        int position;

        try
        {

            // start then destroy to get null media player
            player.start();
            player.stop();

        } catch (NoMusicException nme)
        {
            thrown = true;
        }

        position = player.getMillisecPosition();

        assertEquals("Position should be NO_MUSIC", true, position == MusicPlayer.NO_MUSIC);
        assertEquals("No Music Exception thrown", false, thrown);

    }

    @Test
    public void getSongPathsTest()
    {

        // there are 2 songs in assets/music. They are Cyberwaste.mp3 and Archetype.mp3

        final int EXPECTED_COUNT = 2;

        String [] paths = player.getSongPaths();
        int pathCount = paths.length;

        assertEquals("Unexpected number of paths returned", EXPECTED_COUNT, pathCount);

        if (pathCount == EXPECTED_COUNT)
        {
            assertEquals("First song in paths was not Archetype.mp3, it was "+paths[0], true, paths[0].equals("Archetype.mp3"));
            assertEquals("Second song in paths was not Cyberwaste.mp3, is was"+paths[1], true, paths[1].equals("Cyberwaste.mp3"));
        }

    }

    @Test
    public void loadSongFromPathTest()
    {
        // already loaded a song in the before column. Expecting it to be Archetype by Fear Factory with path music/Archetype.mp3 and duration of 4:35

        ISong test = player.getCurrentSong();

        String title = test.getTitle();
        String artist = test.getArtist().getName();

        int minutes = test.getDuration().getMinutes();
        int lastSeconds = test.getDuration().getLastSeconds();

        String path = test.getPath();

        assertEquals("Song name should be Archetype", true, title.equals("Archetype"));
        assertEquals("Song artist should be Fear Factory", true, artist. equals("Fear Factory"));
        assertEquals("Song path should be" + testSong, true, path.equals(testSong));
        assertEquals("Song durtation should be 4:35", true, minutes == 4 && lastSeconds ==35);

    }


}