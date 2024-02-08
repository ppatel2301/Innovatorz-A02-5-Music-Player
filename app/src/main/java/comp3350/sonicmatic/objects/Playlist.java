package comp3350.sonicmatic.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Playlist {
    private String playlistName;
    private ArrayList<MusicTrack> playlist;

    public Playlist(String name)
    {
        playlistName = name;
        playlist = null;
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

    public ArrayList<MusicTrack> filterByArtist ()
    {
//        return Collections.sort(playlist, Comparator.comparing(MusicTrack::getArtistName));
        return null;
    }

    public ArrayList<MusicTrack> filterByTitle ()
    {
//        return Collections.sort(playlist, Comparator.comparing(MusicTrack::getTitle));
        return null;
    }

    public ArrayList<MusicTrack> filterByAlbum ()
    {
//        return Collections.sort(playlist, Comparator.comparing(MusicTrack::getAlbumTitle));
        return null;
    }
}
