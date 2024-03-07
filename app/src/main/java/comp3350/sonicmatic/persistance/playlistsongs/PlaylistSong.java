package comp3350.sonicmatic.persistance.playlistsongs;

import comp3350.sonicmatic.interfaces.IPersistentItem;

public class PlaylistSong implements IPersistentItem
{

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
        return ""; // no pk for this, only combination of foreign keys
    }

    public String getFileNameExt()
    {
        return fileNameExt;
    }

    public int getPlaylistId()
    {
        return playlistId;
    }

}

