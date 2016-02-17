package ch.xero88.qoqa.Service;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import ch.xero88.qoqa.Model.User;

/**
 * Created by Anthony on 21/01/2016.
 */
public class UserService {

    /*
     * Register a new parse User (with username / password / firstname / lastname)
     *
     */
    public void register(User user, SignUpCallback parseSignUpCallaback){

        ParseUser pUser = new ParseUser();
        pUser.setUsername(user.getUsername());
        pUser.setPassword(user.getPassword());
        pUser.put(User.FIRSTNAME, user.getFirstName());
        pUser.put(User.LASTNAME, user.getLastName());

        pUser.signUpInBackground(parseSignUpCallaback);
    }

    /*
     * Login a parse User
     *
     */
    public void login(String username, String password, LogInCallback parseLogInCallback){

        ParseUser.logInInBackground(username, password, parseLogInCallback);
    }
}
