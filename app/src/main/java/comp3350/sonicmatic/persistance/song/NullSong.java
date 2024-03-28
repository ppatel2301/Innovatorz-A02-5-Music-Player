package comp3350.sonicmatic.persistance.song;

public class NullSong extends Song
{
    private static final String NULL_SONG = "NULL MUSIC TRACK";

    private static NullSong nullSong = null;

    public static NullSong getNullSong()
    {
        if (nullSong == null)
        {
            nullSong = new NullSong();
        }

        return nullSong;
    }

    private NullSong()
    {
        super(NULL_SONG,0);
    }
}
