package comp3350.sonicmatic.objects.musicArtist;



public class NullMusicArtist extends MusicArtist
{
    private static final String NULL_ARTIST = "UNKNOWN ARTIST";
    private static NullMusicArtist nullMusicArtist = null;

    public static NullMusicArtist getNullMusicArtist()
    {
        if (nullMusicArtist == null)
        {
            nullMusicArtist = new NullMusicArtist();
        }

        return nullMusicArtist;
    }

    private NullMusicArtist()
    {
        super(NULL_ARTIST);
    }
}
