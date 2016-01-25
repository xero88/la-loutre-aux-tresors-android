package xero88.ch.qoqa.Adapter;

/**
 * Created by Anthony on 23/01/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;
import xero88.ch.qoqa.Model.CouponType;
import xero88.ch.qoqa.Model.Gift;
import xero88.ch.qoqa.R;
import xero88.ch.qoqa.Utils.DateUtils;
import xero88.ch.qoqa.Utils.FontUtils;


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

        // apply font
        FontUtils.setText(gift.getName(), titleLabel, "fonts/Primitive.ttf", context);

        // chest
        ImageView chestImageView = (ImageView) rowView.findViewById(R.id.chestImage);
        if(gift.getType() == CouponType.BRONZE)
            chestImageView.setImageResource(R.drawable.chest_bronze);
        if(gift.getType() == CouponType.SILVER)
            chestImageView.setImageResource(R.drawable.chest_silver);
        if(gift.getType() == CouponType.GOLD)
            chestImageView.setImageResource(R.drawable.chest_gold);

        // countdown
        final Date giftDrawDate = gift.getDrawDate();
        new CountDownTimer(DateUtils.getMiliSecDiff(giftDrawDate), 1000) {

            TextView countdownLabel = (TextView) rowView.findViewById(R.id.countDownLabel);

            @Override
            public void onTick(long millisUntilFinished) {
                FontUtils.setText(DateUtils.getCountdown(giftDrawDate), countdownLabel, "fonts/Primitive.ttf", context);
            }

            @Override
            public void onFinish() {
                FontUtils.setText("Draw in any sec !", countdownLabel, "fonts/Primitive.ttf", context);// TODO change
                countdownLabel.setTextColor(Color.RED);
            }
        }.start();

        return rowView;
    }


}

