package comp3350.sonicmatic;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.res.Resources;
import android.os.SystemClock;
import android.view.KeyEvent;

import androidx.appcompat.widget.SearchView;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import comp3350.sonicmatic.presentation.MainActivity;

public class BrowseCatalogTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp(){
        SystemClock.sleep(1000);

        // login
        onView(withId(R.id.login_username)).perform(typeText("Profile11"));
        onView(withId(R.id.login_pass)).perform(typeText("comp3350"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());
    }

    @Test
    public void browseTest(){
        onView(withId(R.id.navigation_browse)).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.searchView)).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.searchView))
                .check(matches(isDisplayed()));

        onView(isAssignableFrom(SearchView.SearchAutoComplete.class)).perform(typeText("arch"));
        SystemClock.sleep(1000);
    }
}