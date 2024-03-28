package comp3350.sonicmatic.systemTests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.os.SystemClock;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.presentation.MainActivity;

public class ArtistProfileTest {

    private final int MUSIC_X = 450;
    private final int MUSIC_Y = 800;

    @Rule
    public ActivityScenarioRule<MainActivity> activityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() throws InterruptedException {
        SystemClock.sleep(1000);

        // login in as artist
        onView(ViewMatchers.withId(R.id.login_username)).perform(typeText("BenD1337"));
        onView(withId(R.id.login_pass)).perform(typeText("Password1"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());
    }

    @Test
    public void profileTest()
    {
        onView(withId(R.id.navigation_profile)).perform(click());
        SystemClock.sleep(1000);
    }

    @Test
    public void uploadMusicForUserToListen() {

        // Wait for page to load
        SystemClock.sleep(1000);

        onView(withId(R.id.navigation_profile)).perform(click());

        // opens the upload page (download page)
        onView(withId(R.id.upload_Tracks)).perform(click());

        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        uiDevice.click(MUSIC_X,MUSIC_Y);
    }
}