package comp3350.sonicmatic.database;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import comp3350.sonicmatic.application.Services;
import comp3350.sonicmatic.persistance.leaderboard.Leaderboard;
import comp3350.sonicmatic.persistance.leaderboard.LeaderboardPersistence;
import comp3350.sonicmatic.persistance.leaderboard.NullLeaderboard;

public class LeaderboardPersistenceTest {
    private LeaderboardPersistence leaderboardPersistence;

    @Before
    public void setup(){
        Services.setContext(InstrumentationRegistry.getInstrumentation().getTargetContext());
        leaderboardPersistence = Services.getLeaderboardPersistence();
    }

    @Test
    public void testGet(){
        Leaderboard leaderboard = (Leaderboard) leaderboardPersistence.get();

        assertNotEquals(leaderboard, NullLeaderboard.getNullLeaderboard());
    }
}
