package comp3350.sonicmatic.objects.musicTrack;

import comp3350.sonicmatic.objects.musicArtist.NullMusicArtist;
import comp3350.sonicmatic.objects.songDuration.NullDuration;

public class NullMusicTrack extends MusicTrack
{
    private static final String NULL_TRACK = "NULL MUSIC TRACK";
    private static NullMusicTrack nullMusicTrack = null;

    public static NullMusicTrack getNullMusicTrack()
    {
        if (nullMusicTrack == null)
        {
            nullMusicTrack = new NullMusicTrack();
        }

        return nullMusicTrack;
    }

    private NullMusicTrack()
    {
        super(NULL_TRACK, NullMusicArtist.getNullMusicArtist(), NullDuration.getNullDuration(), NULL_TRACK);
    }
}
