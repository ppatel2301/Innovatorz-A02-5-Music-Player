package comp3350.sonicmatic.interfaces;

import java.util.ArrayList;

public interface IPlaylist {

    String getPlaylistName();
    void removePlaylist();
    ArrayList<ISong> getPlaylist();
    void addMusicTracks(ISong musicTrack);
    void removeMusicTracks(ISong remove);
    void updatePlaylist(ArrayList<ISong> updated);
    ArrayList<ISong> filterByArtist();
    ArrayList<ISong> filterByTitle();
}
