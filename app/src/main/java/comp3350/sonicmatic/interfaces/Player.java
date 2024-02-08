package comp3350.sonicmatic.interfaces;

import android.content.Context;

import comp3350.sonicmatic.exceptions.NoMusicException;

public interface Player
{

    boolean isPlaying();
    boolean isPaused();
    boolean isStopped();

    void start() throws NoMusicException;
    void stop() throws NoMusicException;
    void pause() throws NoMusicException;
    void resume() throws NoMusicException;
    void seek(int seekTo) throws NoMusicException;

    int getMillisecDuration();
    int getMillisecPosition();


    void loadSongFromPath(String path);
    Song getCurrentSong();

    String [] getSongPaths();

}
