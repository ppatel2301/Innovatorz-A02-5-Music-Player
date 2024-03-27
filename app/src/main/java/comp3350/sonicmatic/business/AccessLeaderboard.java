package comp3350.sonicmatic.business;

import comp3350.sonicmatic.application.Services;
import comp3350.sonicmatic.objects.musicArtist.LeaderboardArtist;
import comp3350.sonicmatic.persistance.leaderboard.Leaderboard;
import comp3350.sonicmatic.persistance.leaderboard.LeaderboardPersistence;
import comp3350.sonicmatic.persistance.leaderboard.NullLeaderboard;

public class AccessLeaderboard {

    private LeaderboardPersistence leaderboardPersistence;
    private Leaderboard leaderboard = NullLeaderboard.getNullLeaderboard();

    public AccessLeaderboard(){leaderboardPersistence = Services.getLeaderboardPersistence();}

    public AccessLeaderboard(final LeaderboardPersistence customPersistence){
        this.leaderboardPersistence = customPersistence;
    }

    public Leaderboard getLeaderboard(){
        Leaderboard toReturn = leaderboardPersistence.get();
        if(!toReturn.getLeaderboard().isEmpty())
        {
            leaderboard = toReturn;
        }
        return leaderboard;
    }

    public LeaderboardArtist getArtistAt(int index){
        return this.leaderboard.artistAt(index);
    }
}
