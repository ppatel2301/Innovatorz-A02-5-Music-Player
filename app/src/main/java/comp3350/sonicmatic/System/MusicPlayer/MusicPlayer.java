package comp3350.sonicmatic.System.MusicPlayer;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;

import comp3350.sonicmatic.Interfaces.Player;
import comp3350.sonicmatic.Interfaces.Song;

public class MusicPlayer implements Player
{

    private enum States {PLAYING, PAUSED, IDLE}

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
        state = States.PLAYING;
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
        state = States.PLAYING;
    }


    @Override
    public void loadSongFromPath(String path)
    {

        if (state == States.IDLE) // only proceed if this is the case
        {

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
