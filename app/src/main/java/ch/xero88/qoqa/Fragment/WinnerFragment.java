package ch.xero88.qoqa.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.xero88.qoqa.Activity.MainActivity;
import ch.xero88.qoqa.Model.Gift;
import ch.xero88.qoqa.R;
import ch.xero88.qoqa.Service.GiftService;

/**
 * Created by Anthony on 21/01/2016.
 */
public class WinnerFragment extends Fragment implements GetCallback<Gift> {


    @Bind(R.id.giftName) TextView giftName;
    @Bind(R.id.giftProgressBar) ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_winner, container, false);
        ButterKnife.bind(this, view);

        GiftService service = new GiftService();
        service.getGift(getArguments().getString("giftId"), this);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void done(Gift gift, ParseException e) {
        if (e == null) {
            initGift(gift);
            Log.d("Coupons", "Retrieved gift");
        } else {
            Log.d("Coupons", "Error: " + e.getMessage());
            MainActivity activity = ((MainActivity) getActivity());
            if(activity != null)
                activity.openErrorFragment("");
        }
    }

    private void initGift(Gift gift) {

        giftName.setVisibility(View.VISIBLE);
        giftName.setText(gift.getName());
        progressBar.setVisibility(View.GONE);

    }

}
