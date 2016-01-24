package xero88.ch.qoqa.Adapter;

/**
 * Created by Anthony on 23/01/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import xero88.ch.qoqa.Model.Gift;
import xero88.ch.qoqa.R;
import xero88.ch.qoqa.Utils.DateUtils;


/**
 * Created by anthonybernardo on 11.06.15.
 */
public class GiftAdapter extends ArrayAdapter<Gift> {

    private final Context context;
    private List<Gift> data;

    public GiftAdapter(Activity activity, List<Gift> data) {
        super(activity.getApplicationContext(), 0, data);

        this.context = activity.getApplicationContext();
        this.data = data;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Gift gift = data.get(position);
        final View rowView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_gift, parent, false);
        TextView titleLabel = (TextView) rowView.findViewById(R.id.titleLabel);
        titleLabel.setText(gift.getName());

        // countdown
        final Date giftDrawDate = gift.getDrawDate();
        new CountDownTimer(DateUtils.getMiliSecDiff(giftDrawDate), 1000) {

            TextView countdownLabel = (TextView) rowView.findViewById(R.id.countDownLabel);

            @Override
            public void onTick(long millisUntilFinished) {
                countdownLabel.setText(DateUtils.getCountdown(giftDrawDate));
            }

            @Override
            public void onFinish() {
                countdownLabel.setText("Draw in any sec :-)");// TODO change
            }
        }.start();

        return rowView;
    }
}

