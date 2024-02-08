package comp3350.sonicmatic;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import comp3350.sonicmatic.data.StubDatabase;
import comp3350.sonicmatic.objects.MusicTrack;

public class DatabaseUnitTest {
    private StubDatabase db;
    private ArrayList<MusicTrack> lst;

    @Before
    public void setUp(){
        db = new StubDatabase();
        MusicTrack[] tracks ={
                 new MusicTrack("Elan's file path")
        };
        lst = new ArrayList<MusicTrack>(Arrays.asList(tracks));
        db = new StubDatabase(lst);
    }

    @Test
    public void uploadSongTest(){
        MusicTrack track = new MusicTrack("Other file path");
        db.uploadSong(track);
    }



    @Test
    public void getSong(){
        MusicTrack first = db.getSong("First song's name");
        MusicTrack failure = db.getSong("Let It Go - From Disney's Frozen");

        assertEquals(lst.get(0), first);
        assertNull(failure);
    }

}