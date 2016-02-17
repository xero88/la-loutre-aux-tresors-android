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

package ch.xero88.qoqa.Data.Users;

import android.support.annotation.NonNull;

import ch.xero88.qoqa.Model.User;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load users from the a data source.
 */
public class ConcreteUsersRepository implements UsersRepository {

    private final UsersServiceApi mUsersServiceApi;

    public ConcreteUsersRepository(@NonNull UsersServiceApi usersServiceApi) {
        mUsersServiceApi = checkNotNull(usersServiceApi);
    }

    @Override
    public void register(@NonNull User user, @NonNull final RegisterUserCallback callback) {
        mUsersServiceApi.register(user, new UsersServiceApi.RegisterUserServiceCallback() {
            @Override
            public void onRegisterComplete() {
                callback.onRegisterComplete();
            }

            @Override
            public void onErrorAtAttempt(String message) {
                callback.onErrorAtAttempt(message);
            }
        });
    }

    @Override
    public void login(@NonNull User user, @NonNull final LoginUserCallback callback) {
        mUsersServiceApi.login(user.getUsername(), user.getPassword(), new UsersServiceApi.LoginUserServiceCallback() {
            @Override
            public void onLoginComplete(User loggedUser) {
                callback.onLoginComplete(loggedUser);
            }

            @Override
            public void onErrorAtAttempt(String message) {
                callback.onErrorAtAttempt(message);
            }
        });
    }
}
