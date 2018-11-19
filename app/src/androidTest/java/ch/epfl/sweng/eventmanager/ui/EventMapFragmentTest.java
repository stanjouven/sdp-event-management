package ch.epfl.sweng.eventmanager.ui;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import ch.epfl.sweng.eventmanager.R;
import ch.epfl.sweng.eventmanager.test.EventTestRule;
import ch.epfl.sweng.eventmanager.ui.eventShowcase.EventShowcaseActivity;

import static android.os.SystemClock.sleep;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class EventMapFragmentTest {

    @Rule
    public final EventTestRule<EventShowcaseActivity> mActivityRule =
            new EventTestRule<>(EventShowcaseActivity.class, 2);

    @Before
    public void setup() {
        sleep(100);

        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open());

        sleep(100);

        // Display Schedule Events
        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_map));
    }

    @After
    public void finish() {
        mActivityRule.finishActivity();
    }
}
