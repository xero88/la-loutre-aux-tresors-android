package ch.xero88.qoqa.Data.Users;


import android.support.annotation.NonNull;

import ch.xero88.qoqa.Model.User;

/**
 * Created by Anthony on 13/02/2016.
 */
public interface UsersServiceApi {

    interface RegisterUserServiceCallback {

        void onRegisterComplete();
        void onErrorAtAttempt(String message);
    }

    interface LoginUserServiceCallback {

        void onLoginComplete(User loggedUser);
        void onErrorAtAttempt(String message);
    }

    void register(@NonNull User user, @NonNull RegisterUserServiceCallback callback);
    void login(@NonNull String username, @NonNull String password, @NonNull LoginUserServiceCallback callback);

}
