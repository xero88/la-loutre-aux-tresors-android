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

    public void register(User user, SignUpCallback callback){

        ParseUser pUser = new ParseUser();
        pUser.setUsername(user.getUsername());
        pUser.setPassword(user.getPassword());
        pUser.put(User.FIRSTNAME, user.getFirstName());
        pUser.put(User.LASTNAME, user.getLastName());

        pUser.signUpInBackground(callback);
    }

    public void login(String username, String password, LogInCallback callback){

        ParseUser.logInInBackground(username, password, callback);
    }
}
