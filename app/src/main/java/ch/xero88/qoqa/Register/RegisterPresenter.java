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

package ch.xero88.qoqa.Register;

import android.support.annotation.NonNull;

import ch.xero88.qoqa.Data.Users.UsersRepository;
import ch.xero88.qoqa.Model.User;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link RegisterFragment}), retrieves the data and updates
 * the UI as required.
 */
public class RegisterPresenter implements RegisterContract.UserActionsListener {

    @NonNull
    private final UsersRepository mUsersRepository;
    @NonNull
    private final RegisterContract.View mRegisterView;

    public RegisterPresenter(@NonNull UsersRepository usersRepository,
                            @NonNull RegisterContract.View RegisterView) {
        mUsersRepository = checkNotNull(usersRepository);
        mRegisterView = checkNotNull(RegisterView);
        RegisterView.setUserActionListener(this);
    }

    @Override
    public void register(String firstname, String lastname, String email, String password) {

        mRegisterView.showRegisterInProgress();

        User user = new User();
        user.setUsername(email);
        user.setPassword(password);
        user.setFirstName(firstname);
        user.setLastName(lastname);

        mUsersRepository.register(user, new UsersRepository.RegisterUserCallback() {

            @Override
            public void onRegisterComplete() {
                mRegisterView.showRegisterComplete();
            }

            @Override
            public void onErrorAtAttempt(String message) {
                mRegisterView.showRegisterFailed(message);
            }
        });

    }

}
