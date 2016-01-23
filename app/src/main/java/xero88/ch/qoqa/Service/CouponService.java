package xero88.ch.qoqa.Service;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import xero88.ch.qoqa.Model.Coupon;

/**
 * Created by Anthony on 23/01/2016.
 */
public class CouponService {

    public void getCoupons(ParseUser currentUser, int byType, FindCallback<ParseObject> callback) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Coupon");
        query.whereEqualTo("user", currentUser);
        query.whereEqualTo("type", byType);
        query.findInBackground(callback);
    }

    public void getCoupons(ParseUser currentUser, FindCallback<ParseObject> callback) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Coupon");
        query.whereEqualTo("user", currentUser);
        query.findInBackground(callback);
    }
}
