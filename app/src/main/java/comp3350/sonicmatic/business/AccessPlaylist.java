package comp3350.sonicmatic.business;

import java.util.ArrayList;

import comp3350.sonicmatic.application.Services;
import comp3350.sonicmatic.interfaces.IPlaylist;
import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.persistance.playlist.PlaylistPersistence;

public class AccessPlaylist {

    private PlaylistPersistence playlistPersistence;
    private ArrayList<ISong> tracks;

    public AccessPlaylist(){
        this.playlistPersistence = Services.getPlaylistPersistence();
    }

    public AccessPlaylist(final PlaylistPersistence playlistPersistence)
    {
        this.playlistPersistence = playlistPersistence;
    }

    public ArrayList<IPlaylist> getPlaylist(String user)
    {
        return null;
    }
}