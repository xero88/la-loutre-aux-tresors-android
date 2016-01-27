package ch.xero88.qoqa.Service;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import ch.xero88.qoqa.Model.Gift;

/**
 * Created by Anthony on 23/01/2016.
 */
public class GiftService {

    public void getWinnedGifts(FindCallback<Gift> callback) {
        ParseQuery<Gift> query = ParseQuery.getQuery("Gift");
        query.orderByAscending("drawDate");
        query.whereNotEqualTo("winner", null);
        //query.fromLocalDatastore();
        query.findInBackground(callback);
    }

    public void getActivGifts(FindCallback<Gift> callback) {
        ParseQuery<Gift> query = ParseQuery.getQuery("Gift");
        query.orderByAscending("drawDate");
        query.whereEqualTo("winner", null);
        //query.fromLocalDatastore();
        query.findInBackground(callback);
    }

    public void getAllGifts(FindCallback<Gift> callback) {
        ParseQuery<Gift> query = ParseQuery.getQuery("Gift");
        query.orderByAscending("drawDate");
        //query.fromLocalDatastore();
        query.findInBackground(callback);
    }


    public void getGift(String giftId, GetCallback<Gift> callback) {

        ParseQuery<Gift> query = ParseQuery.getQuery("Gift");
        query.whereEqualTo("objectId", giftId);
        query.getFirstInBackground(callback);
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
