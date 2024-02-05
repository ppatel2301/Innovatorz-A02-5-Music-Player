package comp3350.sonicmatic.Objects;

import comp3350.sonicmatic.Interfaces.Song;
import comp3350.sonicmatic.Interfaces.Artist;
import comp3350.sonicmatic.Interfaces.SongLength;

public class MusicTrack implements Song
{

    // ** instance variables **
    private SongLength songDuration;
    private String path;
    private String name;
    private int sizeInBytes;
    private Artist artist;

    // ** constructors **

    public MusicTrack(String filePath)
    {
        if (filePath != null && filePath != "")
        {
            path = filePath;
            initMetadata(path);
        }
    }

    // ** accessors **

    @Override
    public String getPath()
    {
        return path;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public int getLength()
    {
        return -1;
    }

    @Override
    public Artist getArtist()
    {
        return artist;
    }

    @Override
    public int getSizeInBytes()
    {
        return sizeInBytes;
    }

    // ** mutators **
    private void initMetadata(String filePath)
    {
        // read file, get needed metadata
    }

}
