package xero88.ch.qoqa.Service;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by Anthony on 22/01/2016.
 */
public class OrderService {

    public void sendOrder(ParseUser user, SaveCallback callback){
        ParseObject order = new ParseObject("Order");
        order.put("user", user);
        order.saveInBackground(callback);
    }
}
