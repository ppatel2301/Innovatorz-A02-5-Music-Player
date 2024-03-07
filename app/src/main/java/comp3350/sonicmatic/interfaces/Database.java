package comp3350.sonicmatic.interfaces;

import java.util.ArrayList;

import comp3350.sonicmatic.objects.MusicTrack;

public interface Database {
    public void uploadSong(MusicTrack song);

    public MusicTrack getSong(String name) throws Exception;

    public ArrayList<IArtist> getTopArtists(int n);
}
