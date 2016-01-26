package xero88.ch.qoqa;

import android.content.Intent;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;

import xero88.ch.qoqa.Activity.LoginActivity;
import xero88.ch.qoqa.Activity.MainActivity;
import xero88.ch.qoqa.Model.Gift;

/**
 * Created by Anthony on 21/01/2016.
 */
public class Application extends android.app.Application  {

    @Override
    public void onCreate() {
        super.onCreate();


        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "Csu3X9Ra2stVGcmhPw2qMYKXAMuIdcIVcs8DUOcJ", "VVE0tcsrIGTYFi1SlH8lDJXxRJG7rDtvbULLRrxx");
        ParseObject.registerSubclass(Gift.class);
        // TODO les autres
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // Get on main if logged
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            // ... Or login / signUp
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }



}
