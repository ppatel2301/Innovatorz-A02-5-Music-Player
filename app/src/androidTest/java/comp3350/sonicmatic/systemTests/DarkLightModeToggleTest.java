package comp3350.sonicmatic.systemTests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import comp3350.sonicmatic.R;
import comp3350.sonicmatic.presentation.MainActivity;

public class DarkLightModeToggleTest {

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
    }

    @Test
    public void testDarkLightMode()
    {
        onView(withId(R.id.navigation_profile)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.dark_light_mode_toggle)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.drawer_nav_view)).perform(swipeRight());
        SystemClock.sleep(1000);
    }
}