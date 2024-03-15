package comp3350.sonicmatic.persistance.leaderboard;

import java.util.ArrayList;

import comp3350.sonicmatic.interfaces.IArtist;

public class NullLeaderboard extends Leaderboard{
    private static final ArrayList<IArtist> emptyLeaderboard = new ArrayList<>();
    private static NullLeaderboard nullLeaderboard = null;

    public static NullLeaderboard getNullLeaderboard(){
        if (nullLeaderboard == null)
            nullLeaderboard = new NullLeaderboard();
        return nullLeaderboard;
    }

    private NullLeaderboard(){
        super(emptyLeaderboard);
    }
}
