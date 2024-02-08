package comp3350.sonicmatic.objects;

import comp3350.sonicmatic.interfaces.Song;
import comp3350.sonicmatic.interfaces.Artist;
import comp3350.sonicmatic.interfaces.SongLength;

public class MusicTrack implements Song
{

    // ** instance variables **
    private SongLength songDuration;
    private String path;
    private String name;
    private Artist artist;

    // ** constructors **

    public MusicTrack(Song song)
    {
        this.name = song.getTitle();
        this.artist = song.getArtist();
        this.songDuration = song.getDuration();
        this.path = song.getPath();
    }

    public MusicTrack(String name, Artist artist, SongLength songDuration, String path)
    {
        this.name = name;
        this.artist = artist;
        this.songDuration = songDuration;
        this.path = path;
    }

    // ** accessors **

    @Override
    public String getPath()
    {
        return path;
    }

    @Override
    public String getTitle()
    {
        return name;
    }

    @Override
    public SongLength getDuration()
    {
        return new SongDuration(songDuration);
    }

    @Override
    public Artist getArtist()
    {
        return new MusicArtist(this.name);
    }


}
