package xero88.ch.qoqa;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by Anthony on 21/01/2016.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "Csu3X9Ra2stVGcmhPw2qMYKXAMuIdcIVcs8DUOcJ", "VVE0tcsrIGTYFi1SlH8lDJXxRJG7rDtvbULLRrxx");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

}
