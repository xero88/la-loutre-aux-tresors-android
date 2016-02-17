package ch.xero88.qoqa.Data.Users.Parse;

import android.support.annotation.NonNull;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import ch.xero88.qoqa.Data.Users.UsersServiceApi;
import ch.xero88.qoqa.Model.User;

/**
 * Created by Anthony on 13/02/2016.
 */
public class UsersServiceParseApiImpl implements UsersServiceApi {

    public static final String FIRSTNAME = "firstname";
    public static final String LASTNAME = "lastname";

    @Override
    public void register(@NonNull User user, @NonNull final RegisterUserServiceCallback callback) {

        ParseUser pUser = new ParseUser();
        pUser.setUsername(user.getUsername());
        pUser.setPassword(user.getPassword());
        pUser.put(User.FIRSTNAME, user.getFirstName());
        pUser.put(User.LASTNAME, user.getLastName());

        pUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    callback.onRegisterComplete();
                } else {
                    callback.onErrorAtAttempt(e.getMessage());
                }
            }
        });
    }

    @Override
    public void login(@NonNull String username, @NonNull String password, @NonNull final LoginUserServiceCallback callback) {

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (e == null) {

                    // create a generic user (not a PARSE user)
                    User user = new User();
                    user.setId(parseUser.getObjectId());
                    user.setFirstName((String) parseUser.get(FIRSTNAME));
                    user.setLastName((String) parseUser.get(LASTNAME));
                    user.setUsername(parseUser.getUsername());

                    callback.onLoginComplete(user);
                } else {
                    callback.onErrorAtAttempt(e.getMessage());
                }
            }
        });

    }


}
