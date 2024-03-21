package comp3350.sonicmatic.persistance.leaderboard;

import android.annotation.SuppressLint;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ServiceConfigurationError;

import comp3350.sonicmatic.application.Services;
import comp3350.sonicmatic.interfaces.IArtist;
import comp3350.sonicmatic.interfaces.IPersistentItem;
import comp3350.sonicmatic.interfaces.ISong;
import comp3350.sonicmatic.objects.musicArtist.LeaderboardArtist;
import comp3350.sonicmatic.persistance.Persistence;
import comp3350.sonicmatic.persistance.leaderboard.Leaderboard;

public class LeaderboardPersistence extends Persistence {

    public static final NullLeaderboard NULL_LEADERBOARD = NullLeaderboard.getNullLeaderboard();

    public LeaderboardPersistence(String dbName, String dbPath) {
        super(dbName, dbPath);
    }

    @Override
    protected Leaderboard fromResultSet(ResultSet rs) throws SQLException {
        HashMap<String, Integer> mapping = new HashMap<>();
        ArrayList<LeaderboardArtist> leaderboard = new ArrayList<>();
        try {
            while (rs.next()){
                ISong song = Services.createSongFromPath(rs.getString("file_name_ext"));
                String artistName = song.getArtist().getName();
                if (mapping.containsKey(artistName)){
                    assert (mapping.get(artistName) != null);
                    mapping.put(artistName, mapping.get(artistName) +1);
                }
                else{
                    mapping.put(artistName, 1);
                }
            }
        }
        catch (SQLException sqle){
            return NullLeaderboard.getNullLeaderboard();
        }

        for (String key : mapping.keySet())
        {
            leaderboard.add(new LeaderboardArtist(key, mapping.get(key)));
        }

        leaderboard.sort(LeaderboardArtist::compareTo);
        return new Leaderboard(leaderboard);
    }

    @Override
    public Leaderboard get(String primaryKey) {
        // The primaryKey parameter is ignored
        return this.get();
    }

    public Leaderboard get(){
        Leaderboard retrieved;

        try (final Connection c = getConnection()){
            final ResultSet queryResult;
            final Statement statement = c.createStatement();

            final int sizeLimit = 5;

            @SuppressLint("DefaultLocale") // IDE-inserted line
            final String query = "SELECT file_name_ext FROM playlist_songs;";

            queryResult = statement.executeQuery(query);

            retrieved = this.fromResultSet(queryResult);

        }
        catch (final SQLException sqle){
            retrieved = NULL_LEADERBOARD;
        }

        return retrieved;
    }

    @Override
    public Leaderboard update(IPersistentItem item) {
        if (item instanceof Leaderboard)
            return (Leaderboard) item;
        else
            return NULL_LEADERBOARD;
    }

    @Override
    public boolean insert(IPersistentItem item) {
        return false;
    }

    @Override
    public boolean delete(IPersistentItem item) {
        return false;
    }
}
