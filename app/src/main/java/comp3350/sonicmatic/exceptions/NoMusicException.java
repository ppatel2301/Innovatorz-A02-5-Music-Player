package comp3350.sonicmatic.exceptions;

public class NoMusicException extends Exception
{
    public NoMusicException()
    {
        super("NoMusicException - music player has no music to play");
    }
}
