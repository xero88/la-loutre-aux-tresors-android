package ch.xero88.qoqa.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.xero88.qoqa.Activity.MainActivity;
import ch.xero88.qoqa.Adapter.GiftAdapter;
import ch.xero88.qoqa.Model.Gift;
import ch.xero88.qoqa.R;
import ch.xero88.qoqa.Service.GiftService;

/**
 * Created by Anthony on 21/01/2016.
 */
public class GiftFragment extends Fragment implements FindCallback<Gift> {

    public static final String SHOW_WINNING_GIFTS = "SHOW_WINNING_GIFTS";

    @Bind(R.id.giftListView) ListView giftListView;
    @Bind(R.id.giftProgressBar) ProgressBar giftProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_gift_list, container, false);
        ButterKnife.bind(this, view);

        giftProgressBar.setVisibility(View.VISIBLE);

        GiftService service = new GiftService();

        if(getArguments() != null && getArguments().getBoolean(GiftFragment.SHOW_WINNING_GIFTS)){
            service.getWinnedGifts(this);
        }
        else
            service.getActivGifts(this);

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void done(List<Gift> gifts, ParseException e) {

        if (e == null) {
            giftProgressBar.setVisibility(View.GONE);
            giftListView.setAdapter(new GiftAdapter(getActivity(), gifts));
        } else {
            Log.e("Gift", "Error: " + e.getMessage());

            MainActivity activity = ((MainActivity) getActivity());
            if(activity != null)
                activity.openErrorFragment("");
        }
    }


}
