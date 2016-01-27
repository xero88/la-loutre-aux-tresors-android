package ch.xero88.qoqa.Service.Callback;

import android.app.Activity;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.SaveCallback;

import ch.xero88.qoqa.Activity.MainActivity;
import ch.xero88.qoqa.R;

/**
 * Created by Anthony on 22/01/2016.
 */
public class OrderCallback implements SaveCallback{

    Activity activity;

    public OrderCallback(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void done(ParseException e) {

        if (e == null) {
            Toast.makeText(activity, activity.getString(R.string.main_activity_order_send), Toast.LENGTH_LONG).show();
            ((MainActivity)activity).goToCouponFragment();
        } else {
            if(activity != null)
                ((MainActivity)activity).openErrorFragment("");
        }
    }
}