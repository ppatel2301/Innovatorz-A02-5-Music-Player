package comp3350.sonicmatic;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertTrue;

import android.os.SystemClock;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import comp3350.sonicmatic.presentation.MainActivity;

public class ArtistProfileTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() throws InterruptedException {
        SystemClock.sleep(1000);

        // login in as artist
        onView(withId(R.id.login_username)).perform(typeText("BenD1337"));
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
    public void uploadMusicForUserToListen() throws UiObjectNotFoundException {

        // Wait for page to load
        SystemClock.sleep(1000);

        onView(withId(R.id.navigation_profile)).perform(click());

        // opens the upload page (download page)
        onView(withId(R.id.upload_Tracks)).perform(click());

        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        UiObject frame = uiDevice.findObject(new UiSelector());

        UiObject firstItem = uiDevice.findObject(new UiSelector().clickable(true));
        firstItem.click();

        firstItem.getChild(firstItem.getSelector());

        SystemClock.sleep(1000);
//
//        System.out.println(frame.getText());
//        System.out.println(frame.getChild(frame.getSelector()).getChildCount());
//        UiObject child = frame.getChild(frame.getSelector());
//        System.out.println(child.getText());
//        System.out.println(child.getChild(child.getSelector()).getChildCount());
//        System.out.println(child.getChild(child.getSelector()).getChild((child.getChild(child.getSelector()).getSelector())).getText());

//        clickFirstItem.click();
//        clickFirstItem.getChild(new UiSelector()).click();
    }
}
