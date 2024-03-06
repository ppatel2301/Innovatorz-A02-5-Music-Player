package comp3350.sonicmatic.objects.abstracts;

import java.util.ArrayList;

import comp3350.sonicmatic.comparator.MusicComparatorByArtist;
import comp3350.sonicmatic.comparator.MusicComparatorByTitle;
import comp3350.sonicmatic.objects.musictrack.MusicTrack;


public class MusicTrackPlaylist {
    private String playlistName;
    private ArrayList<MusicTrack> playlist;

    public MusicTrackPlaylist(String name)
    {
        playlistName = name;
        playlist = new ArrayList<>();
    }

    public MusicTrackPlaylist(String name, ArrayList<MusicTrack> list)
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

}
