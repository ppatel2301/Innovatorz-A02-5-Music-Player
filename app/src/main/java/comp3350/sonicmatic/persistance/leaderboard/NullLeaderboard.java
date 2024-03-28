package comp3350.sonicmatic.persistance.leaderboard;

import java.util.ArrayList;
import java.util.HashSet;

import comp3350.sonicmatic.business.access.AccessSong;
import comp3350.sonicmatic.application.Services;
import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.objects.musicArtist.LeaderboardArtist;

public class NullLeaderboard extends Leaderboard{
    private static final HashSet<LeaderboardArtist> emptyLeaderboard = new HashSet<>();
    private static NullLeaderboard nullLeaderboard = null;

    public static NullLeaderboard getNullLeaderboard(){
        if (nullLeaderboard == null) {
            nullLeaderboard = new NullLeaderboard();
        }
        return nullLeaderboard;
    }

    private NullLeaderboard(ArrayList<LeaderboardArtist> board){
        super(board);
    }

    private NullLeaderboard(){
        AccessSong accessSong;
        ArrayList<ISong> allSongs;

        if (Services.hasContext())
        {
            accessSong = new AccessSong();
            allSongs = accessSong.getAllSongs();

            for (ISong song: allSongs){
                emptyLeaderboard.add(new LeaderboardArtist(song.getArtist(), 0));
            }

            this.board = new ArrayList<>(emptyLeaderboard);
        }
        else
        {
            this.board = new ArrayList<>();
        }

    }
}
