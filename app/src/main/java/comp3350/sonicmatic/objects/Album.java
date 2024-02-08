package comp3350.sonicmatic.objects;

import java.util.ArrayList;

public class Album {

    private String title;
    private Artist artist;
    private ArrayList<MusicTrack> tracks;

    public Album(String title, Artist artist)
    {
        this.title = title;
        this.artist = artist;
        this.tracks = null;
    }

    public Album(String title, Artist artist, ArrayList<MusicTrack> tracks)
    {
        this.title = title;
        this.artist = artist;
        this.tracks = tracks;
    }

    public String getTitle()
    {
        return title;
    }

    public Artist getArtist()
    {
        return artist;
    }

    public ArrayList<MusicTrack> getTracks()
    {
        return tracks;
    }

    public void uploadToAlbums(MusicTrack musicTrack)
    {
        tracks.add(musicTrack);
    }
}
