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
import ch.xero88.qoqa.Login.LoginContract;
import ch.xero88.qoqa.R;
import ch.xero88.qoqa.Utils.ValidationUtils;

/**
 * Main UI for the Register screen. Users can Register with email and password
 */
public class RegisterFragment extends Fragment implements RegisterContract.View {

    @Bind(R.id.fakeFocus) View mFakeFocusView;
    @Bind(R.id.register_progress) ProgressBar mRegisterProgressBar;
    @Bind(R.id.firstnameField) EditText mFirstnameField;
    @Bind(R.id.lastnameField) EditText mLastnameField;
    @Bind(R.id.usernameField) EditText mUsernameField;
    @Bind(R.id.passwordField) EditText mPasswordField;
    @Bind(R.id.registerButton) Button mRegisterButton;
    @Bind(R.id.orTextView) TextView mOrTextView;
    @Bind(R.id.loginButton) Button mLoginButton;

    private RegisterContract.UserActionsListener mActionListener;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionListener = new RegisterPresenter(Injection.provideUserRepository(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, root);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do Register
                attemptRegister();
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return root;
    }

    @Override
    public void showRegisterInProgress() {
        showProgressBar(true);
    }

    @Override
    public void setUserActionListener(RegisterContract.UserActionsListener listener) {
        mActionListener = listener;
    }

    @Override
    public void showRegisterFailed(String message) {
        showProgressBar(false);
        mPasswordField.setError(getString(R.string.error_invalid_password_2));
        mPasswordField.requestFocus();
    }

    @Override
    public void showRegisterComplete() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }

    private void attemptRegister() {

        // TODO maybe we can clean up with some methods in ValidationUtils
        // like : public boolean checkEmpty(View field, Message error);
        // returning cancel value (true if is empty)

        // Reset errors.
        mFirstnameField.setError(null);
        mLastnameField.setError(null);
        mUsernameField.setError(null);
        mPasswordField.setError(null);


        // Store values at the time of the Register attempt.
        String firstname = mFirstnameField.getText().toString();
        String lastname = mLastnameField.getText().toString();
        String email = mUsernameField.getText().toString();
        String password = mPasswordField.getText().toString();

        boolean cancel = false;
        View focusView = mFakeFocusView;
        mFakeFocusView.requestFocus();


        if (TextUtils.isEmpty(firstname)) {
            mFirstnameField.setError(getString(R.string.error_field_required));
            focusView = mFirstnameField;
            cancel = true;
        }

        if (TextUtils.isEmpty(lastname)) {
            mLastnameField.setError(getString(R.string.error_field_required));
            focusView = mLastnameField;
            cancel = true;
        }

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
            // There was an error; don't attempt Register and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            mActionListener.register(firstname, lastname, email, password);

        }
    }

    private void showProgressBar(boolean show) {

        mRegisterProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);

        mUsernameField.setVisibility(show ? View.GONE : View.VISIBLE);
        mPasswordField.setVisibility(show ? View.GONE : View.VISIBLE);
        mRegisterButton.setVisibility(show ? View.GONE : View.VISIBLE);
        mOrTextView.setVisibility(show ? View.GONE : View.VISIBLE);
        mRegisterButton.setVisibility(show ? View.GONE : View.VISIBLE);
    }
}
