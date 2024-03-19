package comp3350.sonicmatic.business.accessLeaderboardTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import comp3350.sonicmatic.business.AccessLeaderboard;
import comp3350.sonicmatic.persistance.leaderboard.Leaderboard;
import comp3350.sonicmatic.persistance.leaderboard.NullLeaderboard;

public class AccessLeaderboardTest {

    private AccessLeaderboard accessLeaderboard;

    @Before
    public void setUp(){
        accessLeaderboard = new AccessLeaderboard(new FakeLeaderboardDB());
    }

    @Test
    public void testGetLeaderboard()
    {
        Leaderboard leaderboard = accessLeaderboard.getLeaderboard();
        assertNotEquals("NullLeaderboard not expected", NullLeaderboard.getNullLeaderboard(),leaderboard);
    }

}
