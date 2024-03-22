package comp3350.sonicmatic;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import comp3350.sonicmatic.presentation.MainActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreatePlaylistTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() throws InterruptedException {
        Thread.sleep(1000);

        // login
        onView(withId(R.id.login_username)).perform(typeText("Profile11"));
        onView(withId(R.id.login_pass)).perform(typeText("comp3350"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());
    }

    @Test
    public void createPlaylistTest() throws InterruptedException {

        // Wait for page to load
        Thread.sleep(1000);

        // on library page
        onView(withId(R.id.navigation_dashboard)).perform(click());
        onView(withId(R.id.add_playlist)).perform(click());

        Thread.sleep(1000);

        // on create playlist with some name
        onView(withId(R.id.playlistName)).perform(clearText()); // clear pre loaded text
        onView(withId(R.id.playlistName)).perform(typeText("Test"));

        Thread.sleep(1000);

        onView(withId(R.id.create_playlist)).perform(click());

        Thread.sleep(1000);

        // go back to home and add some music to it
        onView(withId(R.id.navigation_home)).perform(click());
        Thread.sleep(1000);

        // on home page, now add music to playlist
        onView(withId(R.id.song_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, clickAddToPlaylistButton()));
        Thread.sleep(1000);

        // on add to playlist fragment
        onView(withId(R.id.add_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, selectPlaylist()));
        Thread.sleep(1000);

        onView(withId(R.id.add_done)).perform(click());
        Thread.sleep(1000);

        // On library page
        onView(withId(R.id.navigation_dashboard)).perform(click());
        Thread.sleep(1000);

        // open the playlist
        onView(withId(R.id.libRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Thread.sleep(1000);

        // Removing the music from playlist
        onView(withId(R.id.list_detail_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, removeMusicFromPlaylist()));
        Thread.sleep(2000);
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

    private ViewAction removeMusicFromPlaylist()
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
                ImageView remove = view.findViewById(R.id.playlist_music_remove);
                remove.performClick();
            }
        };
    }
}