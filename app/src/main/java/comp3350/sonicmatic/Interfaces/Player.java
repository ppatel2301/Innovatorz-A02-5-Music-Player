package comp3350.sonicmatic.Interfaces;

public interface Player
{

    public boolean isPlaying();
    public boolean isPaused();
    public boolean isStopped();

    public void start();
    public void stop();
    public void pause();
    public void resume();

    public void loadSongFromPath(String path);
    public void loadSong(Song song);

}
