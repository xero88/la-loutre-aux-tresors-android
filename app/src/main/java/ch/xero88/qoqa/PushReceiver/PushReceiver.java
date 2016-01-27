package ch.xero88.qoqa.PushReceiver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import ch.xero88.qoqa.Activity.MainActivity;
import ch.xero88.qoqa.Utils.JsonUtils;


public class PushReceiver extends ParsePushBroadcastReceiver {

    @Override
    protected Class<? extends Activity> getActivity(Context context, Intent intent) {
        return super.getActivity(context, intent);
    }

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        super.onPushReceive(context, intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        Intent i = new Intent(context, MainActivity.class);

        String jsonData = intent.getExtras().getString("com.parse.Data");
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonObject != null) {

            String giftId = JsonUtils.getStringInJson(jsonObject, "giftId");
            i.putExtra("giftId", giftId);

        }

        i.putExtras(intent.getExtras());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}

