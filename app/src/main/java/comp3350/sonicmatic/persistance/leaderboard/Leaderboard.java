package comp3350.sonicmatic.persistance.leaderboard;

import java.util.ArrayList;

import comp3350.sonicmatic.objects.musicArtist.LeaderboardArtist;
import comp3350.sonicmatic.interfaces.IPersistentItem;

public class Leaderboard implements IPersistentItem {

    ArrayList<LeaderboardArtist> board;

    public Leaderboard(ArrayList<LeaderboardArtist> bd){
        board = bd;
    }

    @Override
    public String getPrimaryKey() {
        String keys = "";
        for (LeaderboardArtist artist: this.board){
            keys += artist.getName() + " ";
        }

        return keys;
    }

    public ArrayList<LeaderboardArtist> getLeaderboard(){
        return this.board;
    }

    public boolean equals(Leaderboard other){
        return this.getLeaderboard().equals(other.getLeaderboard());
    }

    public LeaderboardArtist artistAt(int index){
        return this.board.get(index);
    }

    public int size(){
        return this.board.size();
    }
}
