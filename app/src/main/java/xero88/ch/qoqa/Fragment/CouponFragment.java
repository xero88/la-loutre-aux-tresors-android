package xero88.ch.qoqa.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xero88.ch.qoqa.Model.CouponType;
import xero88.ch.qoqa.R;
import xero88.ch.qoqa.Service.CouponService;

/**
 * Created by Anthony on 21/01/2016.
 */
public class CouponFragment extends Fragment implements FindCallback<ParseObject> {

    @Bind(R.id.platineKeyCount) TextView platineKeyCount;
    @Bind(R.id.bronzeKeysCount) TextView bronzeKeyCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_coupon1, container, false);
        ButterKnife.bind(this, view);

        CouponService service = new CouponService();
        service.getCoupons(ParseUser.getCurrentUser(), CouponType.BRONZE, this);
        service.getCoupons(ParseUser.getCurrentUser(), CouponType.PLATINE, this);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void done(List<ParseObject> objects, ParseException e) {

        if (e == null) {
            initMyCouponsListView(objects);
            Log.d("Coupons", "Retrieved " + objects.size() + " coupons");
        } else {
            Log.d("Coupons", "Error: " + e.getMessage());
        }
    }

    private void initMyCouponsListView(List<ParseObject> objects) {

        if(objects.size() != 0 && objects.get(0).get("type") == CouponType.BRONZE){
            bronzeKeyCount.setText(objects.size() + "");
        }
        else if(objects.size() != 0 && objects.get(0).get("type") == CouponType.PLATINE){
            platineKeyCount.setText(objects.size() + "");
        }

    }
}
