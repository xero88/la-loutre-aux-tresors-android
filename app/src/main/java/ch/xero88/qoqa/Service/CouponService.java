package ch.xero88.qoqa.Service;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by Anthony on 23/01/2016.
 */
public class CouponService {

    public void getCoupons(ParseUser currentUser, int byType, boolean used, FindCallback<ParseObject> callback) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Coupon");
        query.whereEqualTo("user", currentUser);
        query.whereEqualTo("type", byType);
        query.whereEqualTo("used", used);
        query.findInBackground(callback);
    }

    public void getCoupons(ParseUser currentUser, FindCallback<ParseObject> callback) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Coupon");
        query.whereEqualTo("user", currentUser);
        query.findInBackground(callback);
    }
}
