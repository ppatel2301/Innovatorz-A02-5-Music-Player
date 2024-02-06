package comp3350.sonicmatic.data;

import java.util.ArrayList;
import comp3350.sonicmatic.Interfaces.Database;
import comp3350.sonicmatic.objects.MusicTrack;

public class StubDatabase implements Database {
    private final ArrayList<MusicTrack> dbList;

    public StubDatabase(){
        dbList = new ArrayList<MusicTrack>();
    }

    @Override
    public void uploadSong(MusicTrack song) {
        dbList.add(song);
    }

    @Override
    public MusicTrack getSong(String name) throws Exception {
        for (MusicTrack song: this.dbList){
            if (song.getName().equals(name)) return song;
        }
        throw new Exception("Song not found");
    }
}
