package comp3350.sonicmatic.musicPlayer;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;


import java.io.FileDescriptor;
import java.io.IOException;

import comp3350.sonicmatic.exceptions.NoMusicException;
import comp3350.sonicmatic.interfaces.IPlayer;
import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.objects.musicArtist.MusicArtist;
import comp3350.sonicmatic.objects.musicTrack.MusicTrack;
import comp3350.sonicmatic.objects.songDuration.SongDuration;

public class MusicPlayer implements IPlayer
{

    // ** class constants **
    public static final int NO_MUSIC = -1;

    private enum States {PLAYING, PAUSED, IDLE}

    // ** class variables **
    public static Context context = null;

    // ** instance variables **
    private States state;

    private static MediaPlayer music;

    private ISong playMe;

    // ** constructors **
    public MusicPlayer()
    {
        state = States.IDLE;
    }

    // ** accessors **

    @Override
    public boolean isPlaying()
    {
        return state == States.PLAYING;
    }

    @Override
    public boolean isPaused()
    {
        return state == States.PAUSED;
    }

    @Override
    public boolean isStopped()
    {
        return state == States.IDLE;
    }

    @Override
    public void start() throws NoMusicException
    {
        if (music != null)
        {
            music.start();
            state = States.PLAYING;
        }
        else
        {
            throw new NoMusicException();
        }
    }

    @Override
    public void stop() throws NoMusicException
    {
        if (music != null && state != States.IDLE)
        {
            music.stop();
            music.release();
            music = null;
            state = States.IDLE;
        }
        else if (music == null)
        {
            throw new NoMusicException();
        }
    }

    @Override
    public void pause() throws NoMusicException
    {
        if (music != null && state == States.PLAYING && music.isPlaying())
        {
            music.pause();
            state = States.PAUSED;
        }
        else if (music == null)
        {
            throw new NoMusicException();
        }

    }

    @Override
    public void resume() throws NoMusicException
    {
        if (music != null && state == States.PAUSED)
        {
            music.start();
            state = States.PLAYING;
        }
        else if (music == null)
        {
            throw new NoMusicException();
        }
    }

    @Override
    public void seek(int seekTo) throws NoMusicException
    {
        if (music != null && state != States.IDLE && seekTo >=0 && seekTo < music.getDuration())
        {
            music.seekTo(seekTo);
        }
        else
        {
            throw new NoMusicException();
        }
    }

    @Override
    public int getMillisecDuration()
    {
        int millis = NO_MUSIC;

        if (music != null)
        {
            millis = music.getDuration();
        }

        return  millis;
    }

    @Override
    public int getMillisecPosition()
    {
        int millis = NO_MUSIC;

        if (music != null)
        {
            millis = music.getCurrentPosition();
        }

        return millis;
    }

    public String [] getSongPaths()
    {
        String [] paths = null;
        AssetManager assetManager;

        if (context != null)
        {
            assetManager = context.getAssets();

            try {
                paths = assetManager.list("music");
            } catch(IOException ioe)
            {
                paths = null;
            }

        }

        return paths;
    }

    @Override
    public void loadSongFromPath(String path)
    {
        AssetFileDescriptor afd = null;
        MediaMetadataRetriever metadata = new MediaMetadataRetriever();

        FileDescriptor fd;
        long start;
        long length;

        ISong loadMe = null;

        String title;
        String artist;
        String duration;

        if (state == States.IDLE && music == null && context != null)
        {
            try {
                afd = context.getAssets().openFd(path);

                fd = afd.getFileDescriptor();
                start = afd.getStartOffset();
                length = afd.getLength();

                music = new MediaPlayer();

                metadata.setDataSource(fd, start, length);
                music.setDataSource(fd, start, length);
                afd.close();

                title = metadata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                artist = metadata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                duration = metadata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

                music.prepare();

                loadSong(new MusicTrack(title, new MusicArtist(artist), new SongDuration(duration), path));
            } catch (IOException ioe)
            {
               playMe = null;
            }
        }
    }

    @Override
    public ISong getCurrentSong()
    {
        ISong returnMe = null;

        if (playMe != null)
        {
            returnMe = new MusicTrack(playMe);
        }
        return returnMe;
    }

    private void loadSong(ISong song)
    {
       if (song != null && state == States.IDLE)
       {
           playMe = song;
       }
    }
}
