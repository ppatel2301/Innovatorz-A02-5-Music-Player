package comp3350.sonicmatic.logic;

import java.util.ArrayList;

import comp3350.sonicmatic.interfaces.IArtist;
import comp3350.sonicmatic.interfaces.Database;

public class LeaderboardAccessor {
    static ArrayList<IArtist> getTopArtists(Database db, int number){
        return db.getTopArtists(number);
    }
}
