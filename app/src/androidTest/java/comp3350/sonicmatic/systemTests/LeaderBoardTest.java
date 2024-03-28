package comp3350.sonicmatic.systemTests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.os.SystemClock;
import android.view.View;
import android.widget.CheckBox;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.presentation.MainActivity;

public class LeaderBoardTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp(){
        SystemClock.sleep(1000);

        // login
        onView(ViewMatchers.withId(R.id.login_username)).perform(typeText("Profile11"));
        onView(withId(R.id.login_pass)).perform(typeText("comp3350"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());

        addMusicToPlaylist(6);
    }

    @Test
    public void leaderBoardTest()
    {
        onView(withId(R.id.navigation_leader_board)).perform(click());
        SystemClock.sleep(1000);
    }

    private void addMusicToPlaylist(int songs)
    {
        for(int i = 0; i < songs; i++)
        {
            onView(withId(R.id.song_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(i, clickAddToPlaylistButton()));
            // on add to playlist fragment
            onView(withId(R.id.add_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, selectPlaylist()));
            onView(withId(R.id.add_done)).perform(click());
        }
    }

    private ViewAction clickAddToPlaylistButton()
    {
        return new ViewAction() {
            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                View button = view.findViewById(R.id.add_to_playlist);
                button.performClick();
            }
        };
    }

    private ViewAction selectPlaylist()
    {
        return new ViewAction() {
            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                CheckBox checkBox = view.findViewById(R.id.add_check_box);
                checkBox.performClick();
            }
        };
    }
}
