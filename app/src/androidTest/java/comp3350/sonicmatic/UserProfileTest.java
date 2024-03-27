package comp3350.sonicmatic;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.os.SystemClock;

import comp3350.sonicmatic.presentation.MainActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserProfileTest {

    private final int MUSIC_X = 200;
    private final int MUSIC_Y = 800;

    @Rule
    public ActivityScenarioRule<MainActivity> activityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp(){
        SystemClock.sleep(1000);

        // login in as artist
        onView(withId(R.id.login_username)).perform(typeText("Profile11"));
        onView(withId(R.id.login_pass)).perform(typeText("comp3350"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());
    }

    @Test
    public void loginTest(){
        // Open the profile info
        onView(withId(R.id.navigation_profile)).perform(click());

        // Look at your profile for some time, before it goes away
        SystemClock.sleep(1000);
    }

    @Test
    public void uploadMusic(){

        // Wait for page to load
        SystemClock.sleep(1000);

        onView(withId(R.id.navigation_profile)).perform(click());

        // opens the upload page (download page)
        onView(withId(R.id.upload_Tracks)).perform(click());

        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        uiDevice.click(MUSIC_X, MUSIC_Y);

        // Wait for the music to upload to the database
        SystemClock.sleep(2000);
    }
}