package ch.epfl.sweng.eventmanager.ui.ticketing.activities;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import ch.epfl.sweng.eventmanager.R;
import ch.epfl.sweng.eventmanager.test.ticketing.MockStacks;
import ch.epfl.sweng.eventmanager.ticketing.ErrorCodes;
import ch.epfl.sweng.eventmanager.ticketing.data.ApiResult;
import ch.epfl.sweng.eventmanager.ui.ticketing.TicketingActivity;
import ch.epfl.sweng.eventmanager.ui.ticketing.TicketingConfigurationPickerActivity;
import ch.epfl.sweng.eventmanager.ui.ticketing.TicketingLoginActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class LoginTest extends ActivityTest<TicketingLoginActivity> {


    public LoginTest() {
        super(3, TicketingLoginActivity.class);
    }

    @Test
    public void testValidLogin() {
        Assert.assertFalse(getTicketingService().isLoggedIn());

        onView(withId(R.id.ticketing_login_email)).perform(typeText(MockStacks.AUTHORIZED_USER), closeSoftKeyboard());
        onView(withId(R.id.ticketing_login_password)).perform(typeText(MockStacks.PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.ticketing_login_sign_in_button)).perform(click());

        Intents.intended(Matchers.allOf(
                IntentMatchers.hasComponent(TicketingConfigurationPickerActivity.class.getName()),
                // IntentMatchers.hasType(TicketingScanActivity.class.getTypeName()),
                IntentMatchers.hasExtra(TicketingActivity.SELECTED_EVENT_ID, eventId),
                IntentMatchers.hasExtra(TicketingActivity.TICKETING_CONFIGURATION, repository.getEvent(eventId).getValue().getTicketingConfiguration())
        ));

        Intents.assertNoUnverifiedIntents();
        Assert.assertTrue(getTicketingService().isLoggedIn());
    }

    @Test
    public void testInvalidLogin() {
        Assert.assertFalse(getTicketingService().isLoggedIn());

        onView(withId(R.id.ticketing_login_sign_in_button)).perform(click());

        Intents.assertNoUnverifiedIntents();

        onView(withId(R.id.ticketing_login_email)).perform(typeText("clearly_invalid_value"), closeSoftKeyboard());
        onView(withId(R.id.ticketing_login_password)).perform(typeText(MockStacks.PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.ticketing_login_sign_in_button)).perform(click());

        Intents.assertNoUnverifiedIntents();

        Assert.assertFalse(getTicketingService().isLoggedIn());
    }

    @Test
    public void testFailingLogin() {
        Assert.assertFalse(getTicketingService().isLoggedIn());

        getTicketingService().failNextWith(Collections.singletonList(new ApiResult.ApiError(ErrorCodes.NOT_FOUND.getCode())));

        onView(withId(R.id.ticketing_login_email)).perform(typeText(MockStacks.AUTHORIZED_USER), closeSoftKeyboard());
        onView(withId(R.id.ticketing_login_password)).perform(typeText(MockStacks.PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.ticketing_login_sign_in_button)).perform(click());

        //onView(withText(Matchers.startsWith("Erreur de connexion"))).inRoot(new ToastMatcher()).check(matches(isDisplayed()));
        Intents.assertNoUnverifiedIntents();

        Assert.assertFalse(getTicketingService().isLoggedIn());
    }

    @After
    public void cleanUp() {
        getTicketingService().logout();
    }
}