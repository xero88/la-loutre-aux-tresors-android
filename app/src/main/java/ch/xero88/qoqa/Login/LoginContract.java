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

/**
 * This specifies the contract between the view and the presenter.
 */
public interface LoginContract {

    interface View {

        void showLoginInProgress();

        void showLoginFailed(String message);

        void showLoginComplete();

        void setUserActionListener(UserActionsListener listener);

    }

    interface UserActionsListener {

        void login(String email, String password);
    }
}
