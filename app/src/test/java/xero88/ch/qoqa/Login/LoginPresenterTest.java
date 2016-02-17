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

package xero88.ch.qoqa.Login;


import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ch.xero88.qoqa.Data.Users.UsersRepository;
import ch.xero88.qoqa.Data.Users.UsersRepository.LoginUserCallback;
import ch.xero88.qoqa.Login.LoginContract;
import ch.xero88.qoqa.Login.LoginPresenter;
import ch.xero88.qoqa.Model.User;

import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.*;

/**
 * Unit tests for the implementation of {@link LoginPresenter}
 */
public class LoginPresenterTest {


    private User mUser = new User();

    @Mock
    private UsersRepository mUsersRepository;

    @Mock
    private LoginContract.View mLoginView;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<LoginUserCallback> mLoadLoginUserCallbackCaptor;

    private LoginPresenter mLoginPresenter;

    @Before
    public void setupNotesPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mLoginPresenter = new LoginPresenter(mUsersRepository, mLoginView);

        // fixtures
        mUser.setFirstName("Von");
        mUser.setLastName("Miller");
        mUser.setUsername("von.miller@broncos.us");
        mUser.setPassword("Broncos50superBowlWinners");
    }

    @Test
    public void onLogin_ShowsProgressBar() {

        // When try to login
        mLoginPresenter.login("random@random.ch", "123456");

        // The login progress is show
        verify(mLoginView).showLoginInProgress();
    }

    @Test
    public void onLoginFail_ShowFail() {

        // When try to login
        mLoginPresenter.login("random@random.ch", "123456");

        // Callback is captured and invoked with stubbed user
        verify(mUsersRepository).login(isA(User.class), mLoadLoginUserCallbackCaptor.capture());
        mLoadLoginUserCallbackCaptor.getValue().onErrorAtAttempt("error");

        // The login progress is show
        verify(mLoginView).showLoginFailed(anyString());
    }

    @Test
    public void onLoginSuccess_ShowLoginComplete() {

        // When try to login
        mLoginPresenter.login("random@random.ch", "123456");

        // Callback is captured and invoked with stubbed user
        verify(mUsersRepository).login(isA(User.class), mLoadLoginUserCallbackCaptor.capture());
        mLoadLoginUserCallbackCaptor.getValue().onLoginComplete(mUser);

        // The login progress is show
        verify(mLoginView).showLoginComplete();
    }

}
