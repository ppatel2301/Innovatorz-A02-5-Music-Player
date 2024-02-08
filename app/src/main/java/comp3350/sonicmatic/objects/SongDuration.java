package comp3350.sonicmatic.objects;

import comp3350.sonicmatic.Interfaces.SongLength;

public class SongDuration implements SongLength
{

    // ** instance variables **
    private int minutes;
    private int lastSeconds;

    // ** constructors **
    public SongDuration(int min, int sec)
    {
        minutes = min;
        lastSeconds = sec;
    }

    public SongDuration(int sizeInBytes)
    {
        // some math to figure out how long a song is given the size of the bytes making up the music
    }

    // ** accessors **
    @Override
    public int getMinutes()
    {
        return minutes;
    }

    @Override
    public int getLastSeconds()
    {
        return lastSeconds;
    }

    @Override
    public int getTotalSeconds()
    {
        return (minutes * 60) + lastSeconds;
    }

}
