package xero88.ch.qoqa.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xero88.ch.qoqa.Activity.MainActivity;
import xero88.ch.qoqa.Model.CouponType;
import xero88.ch.qoqa.R;
import xero88.ch.qoqa.Service.CouponService;

/**
 * Created by Anthony on 21/01/2016.
 */
public class CouponFragment extends Fragment implements FindCallback<ParseObject> {

    @Bind(R.id.goldKeyCount) TextView goldKeyCount;
    @Bind(R.id.silverKeysCount) TextView silverKeyCount;
    @Bind(R.id.bronzeKeysCount) TextView bronzeKeyCount;
    @Bind(R.id.goldKeyImage) ImageView goldKeyImage;
    @Bind(R.id.silverKeysImage) ImageView silverKeyImage;
    @Bind(R.id.bronzeKeysImage) ImageView bronzeKeyImage;
    @Bind(R.id.couponProgressBar) ProgressBar couponProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_coupon, container, false);
        ButterKnife.bind(this, view);

        couponProgressBar.setVisibility(View.VISIBLE);
        goldKeyCount.setVisibility(View.GONE);
        silverKeyCount.setVisibility(View.GONE);
        bronzeKeyCount.setVisibility(View.GONE);
        goldKeyImage.setVisibility(View.GONE);
        silverKeyImage.setVisibility(View.GONE);
        bronzeKeyImage.setVisibility(View.GONE);

        CouponService service = new CouponService();
        service.getCoupons(ParseUser.getCurrentUser(), CouponType.BRONZE, false, this);
        service.getCoupons(ParseUser.getCurrentUser(), CouponType.SILVER, false, this);
        service.getCoupons(ParseUser.getCurrentUser(), CouponType.GOLD, false, this);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void done(List<ParseObject> objects, ParseException e) {

        couponProgressBar.setVisibility(View.GONE);
        goldKeyCount.setVisibility(View.VISIBLE);
        silverKeyCount.setVisibility(View.VISIBLE);
        bronzeKeyCount.setVisibility(View.VISIBLE);
        goldKeyImage.setVisibility(View.VISIBLE);
        silverKeyImage.setVisibility(View.VISIBLE);
        bronzeKeyImage.setVisibility(View.VISIBLE);

        if (e == null) {
            initMyCouponsListView(objects);
            Log.d("Coupons", "Retrieved " + objects.size() + " coupons");
        } else {
            Log.d("Coupons", "Error: " + e.getMessage());
            MainActivity activity = ((MainActivity) getActivity());
            if(activity != null)
                activity.openErrorFragment("");
        }
    }

    private void initMyCouponsListView(List<ParseObject> objects) {

        if(objects.size() != 0 && objects.get(0).getInt("type") == CouponType.BRONZE){
            bronzeKeyCount.setText(objects.size() + " " + getString(R.string.bronzeKey));
        }
        else if(objects.size() != 0 && objects.get(0).getInt("type") == CouponType.SILVER){
            silverKeyCount.setText(objects.size() + " " + getString(R.string.silverKey));
        }
        else if(objects.size() != 0 && objects.get(0).getInt("type") == CouponType.GOLD){
            goldKeyCount.setText(objects.size() + " " + getString(R.string.goldKey));
        }
    }
}
