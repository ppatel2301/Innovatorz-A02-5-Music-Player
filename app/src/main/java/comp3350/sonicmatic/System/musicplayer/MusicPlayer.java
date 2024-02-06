package comp3350.sonicmatic.System.musicplayer;

import java.io.File;
import java.io.IOException;

import comp3350.sonicmatic.interfaces.Player;
import comp3350.sonicmatic.interfaces.Song;

public class MusicPlayer implements Player
{

    private enum States {PLAYING, PAUSED, IDLE, BAD_PATH}

    // ** instance variables **
    private States state;

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
    public void start()
    {
        if (state == States.IDLE)
        {
            state = States.PLAYING;
        }
    }

    @Override
    public void stop()
    {
        state = States.IDLE;
    }

    @Override
    public void pause()
    {
        state = States.PAUSED;
    }

    @Override
    public void resume()
    {
        if (state == States.PAUSED)
        {
            state = States.PLAYING;
        }
    }


    @Override
    public void loadSongFromPath(String path)
    {

        File readme = new File(path);

        if (state == States.IDLE || state == States.BAD_PATH) // only proceed if this is the case
        {
            try
            {

            // put file reading here

            } catch(IOException ioe) {
                state = States.BAD_PATH;
                System.out.println(ioe);
            }

        }

    }

    @Override
    public void loadSong(Song song)
    {
        loadSongFromPath(song.getPath());
    }
}
/*
package comp3350.sonicmatic.System.MusicPlayer;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import comp3350.sonicmatic.Interfaces.Player;
import comp3350.sonicmatic.Interfaces.Song;

public class MusicPlayer implements Player
{

    private enum States {PLAYING, PAUSED, IDLE, BAD_PATH}

    // ** instance variables **
    private States state;
    private AudioTrack mp3Player;

    // ** constructors **
    public MusicPlayer()
    {
        mp3Player = createMP3Player();

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

    // ** mutators **

    /// @brief Creates an AudioTrack object passed into an mp3 player
    ///
    /// @return New AudioTrack mp3 player
    private AudioTrack createMP3Player()
    {
        final int ENCODING = AudioFormat.ENCODING_PCM_16BIT;
        final int SAMPLE_RATE = 44100;
        final int CHANNEL_CONFIG = AudioFormat.CHANNEL_OUT_STEREO;

        AudioTrack create_me = new AudioTrack.Builder()
                .setAudioAttributes(new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build())
                .setAudioFormat(new AudioFormat.Builder()
                        .setEncoding(ENCODING)
                        .setSampleRate(SAMPLE_RATE)
                        .setChannelMask(CHANNEL_CONFIG)
                        .build())
                .setBufferSizeInBytes(AudioTrack.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, ENCODING))
                .build();

        return create_me;
    }

    @Override
    public void start()
    {
        if (state == States.IDLE)
        {
            state = States.PLAYING;
        }
    }

    @Override
    public void stop()
    {
        state = States.IDLE;
    }

    @Override
    public void pause()
    {
        state = States.PAUSED;
    }

    @Override
    public void resume()
    {
        if (state == States.PAUSED)
        {
            state = States.PLAYING;
        }
    }


    @Override
    public void loadSongFromPath(String path)
    {

        File readme = new File(path);
        byte bytes[];

        if (state == States.IDLE || state == States.BAD_PATH) // only proceed if this is the case
        {
            try
            {
                bytes = Files.readAllBytes(readme.toPath());



            } catch(IOException ioe) {
                state = States.BAD_PATH;
                System.out.println(ioe);
            }

        }

    }

    @Override
    public void loadSong(Song song)
    {
        loadSongFromPath(song.getPath());
    }

    private byte [] getSong()
    {
        // get the byte array that actually makes up the music to play
        // this will be written to the audio track for playing

        return null;
    }

}
*/