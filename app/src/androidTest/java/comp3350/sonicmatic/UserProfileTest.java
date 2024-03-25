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

import android.os.SystemClock;

import comp3350.sonicmatic.presentation.MainActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserProfileTest {

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

        // Open the profile info
        onView(withId(R.id.navigation_profile)).perform(click());

        // Look at your profile for some time, before it goes away
        SystemClock.sleep(2000);
    }
}