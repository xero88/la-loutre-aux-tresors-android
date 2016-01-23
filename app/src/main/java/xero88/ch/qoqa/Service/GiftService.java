package xero88.ch.qoqa.Service;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import xero88.ch.qoqa.Model.Gift;

/**
 * Created by Anthony on 23/01/2016.
 */
public class GiftService {

    public void getAllGifts(FindCallback<Gift> callback) {
        ParseQuery<Gift> query = ParseQuery.getQuery("Gift");
        //query.fromLocalDatastore();
        query.findInBackground(callback);
    }

    public void getAllGifts(int byType, FindCallback<Gift> callback) {

        ParseQuery<Gift> query = ParseQuery.getQuery("Gift");
        query.whereEqualTo("type", byType);
        query.findInBackground(callback);
    }

    public void getGifts(ParseUser currentUser, FindCallback<Gift> callback) {

        ParseQuery<Gift> query = ParseQuery.getQuery("Gift");
        query.whereEqualTo("winner", currentUser);
        query.findInBackground(callback);
    }

    public void getGifts(ParseUser currentUser, int byType, FindCallback<Gift> callback) {

        ParseQuery<Gift> query = ParseQuery.getQuery("Gift");
        query.whereEqualTo("winner", currentUser);
        query.whereEqualTo("type", byType);
        query.findInBackground(callback);
    }

}
