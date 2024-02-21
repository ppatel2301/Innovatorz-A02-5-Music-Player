package comp3350.sonicmatic.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import comp3350.sonicmatic.comparator.MusicComparatorByArtist;
import comp3350.sonicmatic.comparator.MusicComparatorByTitle;

public class Playlist{
    private String playlistName;
    private ArrayList<MusicTrack> playlist;

    public Playlist(String name)
    {
        playlistName = name;
        playlist = new ArrayList<>();
    }

    public Playlist(String name, ArrayList<MusicTrack> list)
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

    public ArrayList<MusicTrack> getPlaylist()
    {
        return playlist;
    }

    public void addMusicTracks(MusicTrack musicTrack)
    {
        playlist.add(musicTrack);
    }

    public void removeMusicTracks(MusicTrack musicTrack)
    {
        playlist.remove(musicTrack);
    }

    public ArrayList<MusicTrack> filterByArtist ()
    {
        playlist.sort(new MusicComparatorByArtist());
        return playlist;
    }

    public ArrayList<MusicTrack> filterByTitle ()
    {
        playlist.sort(new MusicComparatorByTitle());
        return playlist;
    }

    public ArrayList<MusicTrack> filterByAlbum ()
    {
//        return Collections.sort(playlist, Comparator.comparing(MusicTrack::getAlbumTitle));
        return null;
    }
}