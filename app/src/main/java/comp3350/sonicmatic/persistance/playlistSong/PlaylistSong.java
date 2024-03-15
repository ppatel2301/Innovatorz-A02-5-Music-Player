package comp3350.sonicmatic.persistance.playlistSong;

import comp3350.sonicmatic.interfaces.IPersistentItem;

public class PlaylistSong implements IPersistentItem
{

    // ** class constants **
    public final static int FNAME_INDEX = 0;
    public final static int ID_INDEX = 1;

    // ** instance variables **
    private String fileNameExt;
    private int playlistId;

    // ** constructors **
    public PlaylistSong(String fileNameExt, int playlistId)
    {
        this.fileNameExt = fileNameExt;
        this.playlistId = playlistId;
    }

    public PlaylistSong(PlaylistSong copyMe)
    {
        this.fileNameExt = copyMe.fileNameExt;
        this.playlistId = copyMe.playlistId;
    }

    // ** accessors **

    @Override
    public String getPrimaryKey()
    {
        // no pk for this, only combination of foreign keys
        return "";
    }

    public String getFileNameExt()
    {
        return fileNameExt;
    }

    public int getPlaylistId()
    {
        return playlistId;
    }

    public String [] makePrimaryKey()
    {
        return new String[]{getFileNameExt(), ""+getPlaylistId()};
    }

    public boolean equals(PlaylistSong other)
    {
        return other != null && fileNameExt.equals(other.getFileNameExt()) && playlistId == other.getPlaylistId();
    }
}

