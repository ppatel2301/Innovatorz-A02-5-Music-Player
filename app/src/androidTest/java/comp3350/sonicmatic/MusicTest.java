package comp3350.sonicmatic;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.contrib.RecyclerViewActions;

import comp3350.sonicmatic.presentation.MainActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MusicTest {

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
    public void playMusic() throws InterruptedException {
        // Wait for page to load
        Thread.sleep(1000);

        // on home page
        onView(withId(R.id.song_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(withId(R.id.play_pause_button)).perform(click());
        // listen to music for 1 sec
        Thread.sleep(1000);
    }
}