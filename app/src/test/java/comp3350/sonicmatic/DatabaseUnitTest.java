package comp3350.sonicmatic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import comp3350.sonicmatic.data.StubDatabase;
import comp3350.sonicmatic.objects.MusicTrack;

public class DatabaseUnitTest {
    StubDatabase db = new StubDatabase();

    @Test
    public void uploadSongTest(){
        MusicTrack track = new MusicTrack();
        db.uploadSong(track);
    }

    @Test
    public void getSong(){
        MusicTrack[] tracks ={
                new MusicTrack(),
                new MusicTrack(),
                new MusicTrack(),
                new MusicTrack()
        };
        ArrayList<MusicTrack> lst = new ArrayList<MusicTrack>(Arrays.asList(tracks));
        db = new StubDatabase(lst);

    }

}