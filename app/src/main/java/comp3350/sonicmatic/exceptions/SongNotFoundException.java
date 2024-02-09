package comp3350.sonicmatic.exceptions;

public class SongNotFoundException extends Exception {

    public SongNotFoundException(String songName)
    {
        super("Song \""+songName+"\" not found");
    }

}
