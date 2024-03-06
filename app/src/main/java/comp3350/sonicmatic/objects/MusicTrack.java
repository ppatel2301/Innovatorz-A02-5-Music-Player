package comp3350.sonicmatic.objects;

import java.io.Serializable;

import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.interfaces.IArtist;
import comp3350.sonicmatic.interfaces.ISongLength;

public class MusicTrack implements ISong, Serializable
{

    // ** instance variables **
    private ISongLength songDuration;
    private String path;
    private String name;
    private IArtist artist;

    // ** constructors **

    public MusicTrack(ISong song)
    {
        this.name = song.getTitle();
        this.artist = song.getArtist();
        this.songDuration = song.getDuration();
        this.path = song.getPath();
    }

    public MusicTrack(String name, IArtist artist, ISongLength songDuration, String path)
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
    public ISongLength getDuration()
    {
        return new SongDuration(songDuration);
    }

    @Override
    public IArtist getArtist()
    {
        return new MusicArtist(this.artist.getName());
    }

}