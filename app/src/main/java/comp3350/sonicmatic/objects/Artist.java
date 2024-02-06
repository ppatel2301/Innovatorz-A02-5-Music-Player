package comp3350.sonicmatic.objects;

import java.util.ArrayList;

public class Artist {

    private String artistName;
    private ArrayList<MusicTrack> tracks;
    private ArrayList<Album> albums;

    public Artist()
    {
        artistName = "";
        tracks = null;
        albums = null;
    }

    public Artist(String name, ArrayList<MusicTrack> tracks, ArrayList<Album> albums)
    {
        artistName = name;
        this.tracks = tracks;
        this.albums = albums;
    }

    public String getArtistName()
    {
        return artistName;
    }

    public void uploadMusic(MusicTrack music) {
        if(music != null)
        {
            tracks.add(music);
        }else{
            throw new NullPointerException();
        }
    }

    public void uploadToAlbum(Album album){
        albums.add(album);
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }
}
