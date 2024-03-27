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
import org.junit.runner.manipulation.Ordering;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;
import android.os.Environment;
import android.os.FileUtils;
import android.os.SystemClock;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import comp3350.sonicmatic.presentation.MainActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserProfileTest {

    private final int MUSIC_X = 70;
    private final int MUSIC_Y = 600;

    @Rule
    public ActivityScenarioRule<MainActivity> activityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp(){
        SystemClock.sleep(1000);


    }

    @Test
    public void loginTest(){

        onView(withId(R.id.login_username)).perform(typeText("Profile11"));
        onView(withId(R.id.login_pass)).perform(typeText("comp3350"));
        Espresso.closeSoftKeyboard();
        SystemClock.sleep(1000);
        onView(withId(R.id.loginButton)).perform(click());

        SystemClock.sleep(1000);

        // Open the profile info
        onView(withId(R.id.navigation_profile)).perform(click());

        // Look at your profile for some time, before it goes away
        SystemClock.sleep(2000);

        uploadMusic();
    }

    public void uploadMusic(){

        // Wait for page to load
        SystemClock.sleep(1000);

        // opens the upload page (download page)
        onView(withId(R.id.upload_Tracks)).perform(click());

        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        uiDevice.click(MUSIC_X,MUSIC_Y);

        // Wait for the music to upload to the database
        SystemClock.sleep(2000);
    }
}