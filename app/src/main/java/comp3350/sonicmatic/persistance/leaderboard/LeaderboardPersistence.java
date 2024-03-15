package comp3350.sonicmatic.persistance.leaderboard;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import comp3350.sonicmatic.interfaces.IArtist;
import comp3350.sonicmatic.interfaces.IPersistentItem;
import comp3350.sonicmatic.objects.LeaderboardArtist;
import comp3350.sonicmatic.persistance.Persistence;
import comp3350.sonicmatic.persistance.leaderboard.Leaderboard;

public class LeaderboardPersistence extends Persistence {

    public static final NullLeaderboard NULL_LEADERBOARD = NullLeaderboard.getNullLeaderboard();

    public LeaderboardPersistence(String dbName, String dbPath) {
        super(dbName, dbPath);
    }

    @Override
    protected Leaderboard fromResultSet(ResultSet rs) throws SQLException {
        ArrayList<LeaderboardArtist> leaderboard = new ArrayList<>();
        try {
            while (rs.next()){
                LeaderboardArtist artist = new LeaderboardArtist(rs.getString("name"), rs.getInt("metric"));
                leaderboard.add(artist);
            }
        }
        catch (SQLException sqle){
            return NullLeaderboard.getNullLeaderboard();
        }
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

            final String query = String.format("WITH artists_per_playlist as " +
                    "(SELECT PLAYLISTS.name as playlist_name FROM PLAYLISTS " +
                    "LEFT JOIN PLAYLIST_SONGS on PLAYLISTS.playlist_id = PLAYLIST_SONGS.playlist_id " +
                    "LEFT JOIN SONGS on PLAYLIST_SONGS.file_name_ext = SONGS.file_name_ext " +
                    "LEFT JOIN ALBUMS on SONGS.album_id = ALBUMS.album_id " +
                    "LEFT JOIN PROFILES on ALBUMS.artist_id = PROFILES username) " +
                    "SELECT PROFILES.display_name, count(playlist_name) " +
                    "GROUP BY ALBUMS.artist_id LIMIT %d;", sizeLimit);

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
