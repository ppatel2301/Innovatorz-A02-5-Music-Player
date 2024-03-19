package comp3350.sonicmatic.business.accessLeaderboardTest;

import java.util.ArrayList;

import comp3350.sonicmatic.objects.musicArtist.LeaderboardArtist;
import comp3350.sonicmatic.persistance.leaderboard.Leaderboard;
import comp3350.sonicmatic.persistance.leaderboard.LeaderboardPersistence;

public class FakeLeaderboardDB extends LeaderboardPersistence {

    private ArrayList<LeaderboardArtist> fakeArtists;

    public FakeLeaderboardDB(){
        super("","");

        fakeArtists = new ArrayList<>();
        this.populate();
    }

    private void populate() {
        fakeArtists.add(new LeaderboardArtist("Artist1", 16));
        fakeArtists.add(new LeaderboardArtist("Artist2", 10));
        fakeArtists.add(new LeaderboardArtist("Artist3", 9));
        fakeArtists.add(new LeaderboardArtist("Artist4", 8));
        fakeArtists.add(new LeaderboardArtist("Artist5", 4));
    }

    // Override methods

    @Override
    public Leaderboard get(){
        return new Leaderboard(this.fakeArtists);
    }

}
