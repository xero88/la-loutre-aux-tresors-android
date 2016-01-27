package ch.xero88.qoqa;

import android.content.Intent;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseUser;

import ch.xero88.qoqa.Activity.LoginActivity;
import ch.xero88.qoqa.Activity.MainActivity;
import ch.xero88.qoqa.Model.Gift;

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

            // Parse : subscribe in channel by the name of user
            ParsePush.subscribeInBackground("QoQa_" + currentUser.getObjectId());

            // Get on main if logged
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            // ... Or login / signUp
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }



}
