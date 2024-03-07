package comp3350.sonicmatic.data;

import java.util.ArrayList;
import comp3350.sonicmatic.interfaces.Database;
import comp3350.sonicmatic.interfaces.IArtist;
import comp3350.sonicmatic.objects.MusicArtist;
import comp3350.sonicmatic.objects.MusicTrack;

public class StubDatabase implements Database {
    private final ArrayList<MusicTrack> dbList;

    public StubDatabase(){
        dbList = new ArrayList<MusicTrack>();
    }

    /* Constructor for testing purposes */
    public StubDatabase(ArrayList<MusicTrack> db){
        dbList = db;
    }
    @Override
    public void uploadSong(MusicTrack song) {
        dbList.add(song);
    }

    @Override
    public MusicTrack getSong(String name) throws Exception {
        for (MusicTrack song: this.dbList){
            if (song.getTitle().equals(name)) return song;
        }
        throw new Exception("Song not found");
    }

    @Override
    public ArrayList<IArtist> getTopArtists(int n) {
        ArrayList<IArtist> list =  new ArrayList<IArtist>(n);
        for (int i = 0; i< n; i++){
            list.add(new MusicArtist(String.format("Name %d", i)));
        }
        return list;
    }
}
