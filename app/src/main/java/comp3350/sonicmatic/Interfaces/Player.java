package comp3350.sonicmatic.Interfaces;

public interface Player
{

    boolean isPlaying();
    boolean isPaused();
    boolean isStopped();

    void start();
    void stop();
    void pause();
    void resume();

    void loadSongFromPath(String path);
    void loadSong(Song song);

}
