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

package ch.xero88.qoqa.Login;

import android.support.annotation.NonNull;
import android.util.Log;

import ch.xero88.qoqa.Data.Users.UsersRepository;
import ch.xero88.qoqa.Model.User;
import ch.xero88.qoqa.Utils.EspressoIdlingResource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link LoginFragment}), retrieves the data and updates
 * the UI as required.
 */
public class LoginPresenter implements LoginContract.UserActionsListener {

    @NonNull
    private final UsersRepository mUsersRepository;
    @NonNull
    private final LoginContract.View mLoginView;

    public LoginPresenter(@NonNull UsersRepository usersRepository,
                            @NonNull LoginContract.View loginView) {
        mUsersRepository = checkNotNull(usersRepository);
        mLoginView = checkNotNull(loginView);
        loginView.setUserActionListener(this);
    }

    @Override
    public void login(String email, String password) {

        mLoginView.showLoginInProgress();

        User user = new User();
        user.setUsername(email);
        user.setPassword(password);

        mUsersRepository.login(user, new UsersRepository.LoginUserCallback() {

            @Override
            public void onLoginComplete(User loggedUser) {
                mLoginView.showLoginComplete();
            }

            @Override
            public void onErrorAtAttempt(String message) {
                mLoginView.showLoginFailed(message);
            }
        });

    }
}
