package comp3350.sonicmatic.objects;

import comp3350.sonicmatic.interfaces.IArtist;
import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.interfaces.ISongLength;
import comp3350.sonicmatic.objects.musicArtist.NullMusicArtist;
import comp3350.sonicmatic.objects.songDuration.SongDuration;

public class NoContextSong implements ISong
{
    private String path;

    public NoContextSong(String path)
    {
        this.path = path;
    }

    @Override
    public String getPath()
    {
        return path;
    }

    @Override
    public String getTitle()
    {
        return "No Song";
    }

    @Override
    public ISongLength getDuration()
    {
        return new SongDuration("0");
    }

    @Override
    public IArtist getArtist()
    {
        return NullMusicArtist.getNullMusicArtist();
    }
}
