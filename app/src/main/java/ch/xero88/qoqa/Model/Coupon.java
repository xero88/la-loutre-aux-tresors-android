package ch.xero88.qoqa.Model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by Anthony on 23/01/2016.
 */
@ParseClassName("Coupon")
public class Coupon extends ParseObject {

    public String getCouponId() {
        return getString("couponId");
    }

    public void setCouponId(String couponId) {
        put("couponId", couponId);
    }

    public String getType() {
        return getString("type");
    }

    public void setType(String type) {
        put("type", type);
    }

    public static ParseQuery<Coupon> getQuery() {
        return ParseQuery.getQuery(Coupon.class);
    }

}