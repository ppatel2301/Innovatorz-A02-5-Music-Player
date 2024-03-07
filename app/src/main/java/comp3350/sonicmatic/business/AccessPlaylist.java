package comp3350.sonicmatic.business;

import java.util.ArrayList;

import comp3350.sonicmatic.application.Services;
import comp3350.sonicmatic.interfaces.IPlaylist;
import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.persistance.playlist.PlaylistPersistence;
import comp3350.sonicmatic.persistance.playlistsong.PlaylistSongPersistence;

public class AccessPlaylist {

    // ** instance variables **
    private PlaylistPersistence playlistPersistence;
    private PlaylistSongPersistence playlistSongPersistence;

    // ** constructors **
    public AccessPlaylist(){
        this.playlistPersistence = Services.getPlaylistPersistence();
        this.playlistSongPersistence = Services.getPlaylistSongPersistence();
    }

    public AccessPlaylist(final PlaylistPersistence playlistPersistence, final PlaylistSongPersistence playlistSongPersistence)
    {
        this.playlistPersistence = playlistPersistence;
        this.playlistSongPersistence = playlistSongPersistence;
    }

    public ArrayList<IPlaylist> getPlaylist(String user)
    {
        return null;
    }
}