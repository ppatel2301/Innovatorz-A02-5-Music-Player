package comp3350.sonicmatic.systemTests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.presentation.MainActivity;

public class UserPlaybackHistoryTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        SystemClock.sleep(1000);

        acceptPermissions();

        // login
        onView(ViewMatchers.withId(R.id.login_username)).perform(typeText("Profile11"));
        onView(withId(R.id.login_pass)).perform(typeText("comp3350"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());
    }

    @Test
    public void testUserHistory()
    {
        // Play music and pause from home page
        onView(withId(R.id.song_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Wait for page to load
        SystemClock.sleep(2000);

        onView(withId(R.id.play_pause_button)).perform(click());

        // listen to music for 2 sec
        SystemClock.sleep(2000);

        // Pause the music
        onView(withId(R.id.play_pause_button)).perform(click());

        // View playback history
        onView(withId(R.id.navigation_profile)).perform(click());
        SystemClock.sleep(2000);
    }

    private void acceptPermissions()
    {
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        int height = uiDevice.getDisplayHeight()/2;
        int width = uiDevice.getDisplayWidth()/2;
        int offset = 100;

        uiDevice.click(width, height + offset);
        uiDevice.click(width, height + (offset/2));
        uiDevice.click(width, height + (offset/2));
    }
}