package comp3350.sonicmatic.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import comp3350.sonicmatic.comparator.MusicComparatorByArtist;
import comp3350.sonicmatic.comparator.MusicComparatorByTitle;

import comp3350.sonicmatic.interfaces.IPlaylist;
import comp3350.sonicmatic.interfaces.ISong;

public class MusicTrackPlaylist implements IPlaylist {
    private String playlistName;
    private ArrayList<ISong> playlist;

    public MusicTrackPlaylist(String name)
    {
        playlistName = name;
        playlist = new ArrayList<>();
    }

    public MusicTrackPlaylist(String name, ArrayList<ISong> list)
    {
        playlistName = name;
        playlist = list;
    }

    public String getPlaylistName()
    {
        return playlistName;
    }

    public void removePlaylist()
    {
        playlist = null;
    }

    public ArrayList<ISong> getPlaylist()
    {
        return playlist;
    }

    public void addMusicTracks(ISong musicTrack)
    {
        playlist.add(musicTrack);
    }

    public void removeMusicTracks(ISong musicTrack)
    {
        playlist.remove(musicTrack);
    }

    public ArrayList<ISong> filterByArtist ()
    {
        playlist.sort(new MusicComparatorByArtist());
        return playlist;
    }

    public ArrayList<ISong> filterByTitle ()
    {
        playlist.sort(new MusicComparatorByTitle());
        return playlist;
    }

}
