package comp3350.sonicmatic.objects;

import java.util.ArrayList;

import comp3350.sonicmatic.interfaces.IArtist;

public class Album {

    private String title;
    private IArtist artist;
    private ArrayList<MusicTrack> tracks;

    public Album(String title, IArtist artist)
    {
        this.title = title;
        this.artist = artist;
        this.tracks = new ArrayList<>();
    }

    public Album(String title, IArtist artist, ArrayList<MusicTrack> tracks)
    {
        this.title = title;
        this.artist = artist;
        this.tracks = tracks;
    }

    public String getTitle()
    {
        return title;
    }

    public IArtist getArtist()
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
