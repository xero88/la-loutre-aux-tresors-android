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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import ch.xero88.qoqa.Activity.MainActivity;
import ch.xero88.qoqa.Injection;
import ch.xero88.qoqa.R;
import ch.xero88.qoqa.Utils.ValidationUtils;

/**
 * Main UI for the login screen. Users can login with email and password
 */
public class LoginFragment extends Fragment implements LoginContract.View {

    @Bind(R.id.fakeFocus) View mFakeFocusView;
    @Bind(R.id.login_progress) ProgressBar mLoginProgressBar;
    @Bind(R.id.usernameField) EditText mUsernameField;
    @Bind(R.id.passwordField) EditText mPasswordField;
    @Bind(R.id.loginButton) Button mLoginButton;
    @Bind(R.id.orTextView) TextView mOrTextView;
    @Bind(R.id.registerButton) Button mRegisterButton;

    private LoginContract.UserActionsListener mActionListener;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionListener = new LoginPresenter(Injection.provideUserRepository(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, root);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do login
                attemptLogin();
            }
        });

        return root;
    }

    @Override
    public void showLoginInProgress() {
        showProgressBar(true);
    }

    @Override
    public void showLoginFailed(String message) {
        showProgressBar(false);
        mPasswordField.setError(getString(R.string.error_invalid_password_2));
        mPasswordField.requestFocus();
    }

    @Override
    public void showLoginComplete() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void setUserActionListener(LoginContract.UserActionsListener listener) {
        mActionListener = listener;
    }

    private void attemptLogin() {

        // TODO maybe we can clean up with some methods in ValidationUtils
        // like : public boolean checkEmpty(View field, Message error);
        // returning cancel value (true if is empty)

        // Reset errors.
        mUsernameField.setError(null);
        mPasswordField.setError(null);

        // Store values at the time of the login attempt.
        String email = mUsernameField.getText().toString();
        String password = mPasswordField.getText().toString();

        boolean cancel = false;
        View focusView = mFakeFocusView;
        mFakeFocusView.requestFocus();

        if (TextUtils.isEmpty(email)) {
            mUsernameField.setError(getString(R.string.error_field_required));
            focusView = mUsernameField;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !ValidationUtils.isPasswordValid(password)) {
            mPasswordField.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordField;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mUsernameField.setError(getString(R.string.error_field_required));
            focusView = mUsernameField;
            cancel = true;
        } else if (!ValidationUtils.isEmailValid(email)) {
            mUsernameField.setError(getString(R.string.error_invalid_email));
            focusView = mUsernameField;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError(getString(R.string.error_field_required));
            focusView = mPasswordField;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            mActionListener.login(email, password);

        }
    }

    private void showProgressBar(boolean show) {

        mLoginProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);

        mUsernameField.setVisibility(show ? View.GONE : View.VISIBLE);
        mPasswordField.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginButton.setVisibility(show ? View.GONE : View.VISIBLE);
        mOrTextView.setVisibility(show ? View.GONE : View.VISIBLE);
        mRegisterButton.setVisibility(show ? View.GONE : View.VISIBLE);
    }
}
