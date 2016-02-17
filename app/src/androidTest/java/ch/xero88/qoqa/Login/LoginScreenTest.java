package ch.xero88.qoqa.Login;

/*
 * Copyright 2015, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.xero88.qoqa.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Tests for the notes screen, the main screen which contains a grid of all notes.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginScreenTest {


    /**
     * {@link ActivityTestRule} is a JUnit {@link Rule @Rule} to launch your activity under test.
     *
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    public ActivityTestRule<LoginActivity> mLoginActivityTestRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void fillLoginFieldAndLogin_showProgressBarAndHomePage() throws Exception {

        String newUsername = "bernara3@gmail.com";
        String newPassword = "123456";

        // Click on the add note button
        onView(withId(R.id.usernameField)).perform(click());

        // Fill the username and password
        onView(withId(R.id.usernameField)).perform(typeText(newUsername));
        onView(withId(R.id.passwordField)).perform(typeText(newPassword),
                closeSoftKeyboard());

        // Click on login button
        onView(withId(R.id.loginButton)).perform(click());

        // Check if progress bar & welcome page is displayed
        //onView(withId(R.id.login_progress)).check(matches(isDisplayed()));
        onView(withId(R.id.welcomeTitle)).check(matches(isDisplayed()));
    }



}