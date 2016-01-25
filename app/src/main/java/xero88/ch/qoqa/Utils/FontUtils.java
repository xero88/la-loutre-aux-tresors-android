package xero88.ch.qoqa.Utils;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

/**
 * Created by Anthony on 25/01/2016.
 */
public class FontUtils {

    public static void setText(String name, TextView titleLabel, String font, Context context) {
        SpannableStringBuilder sBuilder = new SpannableStringBuilder();
        sBuilder.append(name);
        CalligraphyTypefaceSpan typefaceSpan = new CalligraphyTypefaceSpan(TypefaceUtils.load(context.getAssets(), font));
        sBuilder.setSpan(typefaceSpan, 0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        titleLabel.setText(sBuilder, TextView.BufferType.SPANNABLE);
    }
}
