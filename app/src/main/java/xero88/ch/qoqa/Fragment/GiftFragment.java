package xero88.ch.qoqa.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import xero88.ch.qoqa.Adapter.GiftAdapter;
import xero88.ch.qoqa.Model.Gift;
import xero88.ch.qoqa.R;
import xero88.ch.qoqa.Service.GiftService;

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

        giftProgressBar.setVisibility(View.GONE);
        giftListView.setAdapter(new GiftAdapter(getActivity(), gifts));
    }


}
