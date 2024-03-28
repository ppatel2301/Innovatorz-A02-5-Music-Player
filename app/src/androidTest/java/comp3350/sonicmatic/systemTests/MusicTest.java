package comp3350.sonicmatic.systemTests;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
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

import android.os.SystemClock;

import androidx.test.espresso.contrib.RecyclerViewActions;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.presentation.MainActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MusicTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        SystemClock.sleep(1000);

        // login
        onView(ViewMatchers.withId(R.id.login_username)).perform(typeText("Profile11"));
        onView(withId(R.id.login_pass)).perform(typeText("comp3350"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());
    }

    @Test
    public void playMusic(){
        // Wait for the page to load
        SystemClock.sleep(2000);

        // on home page
        onView(withId(R.id.song_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        // Wait for page to load
        SystemClock.sleep(1000);

        onView(withId(R.id.play_pause_button)).perform(click());

        // listen to music for 2 sec
        SystemClock.sleep(2000);

        // Pause the music
        onView(withId(R.id.play_pause_button)).perform(click());

        SystemClock.sleep(2000);
    }
}