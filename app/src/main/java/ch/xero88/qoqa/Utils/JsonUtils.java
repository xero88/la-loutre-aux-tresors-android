package ch.xero88.qoqa.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by abernardo on 26.01.16.
 */
public class JsonUtils {

    public static int getIntInJson(JSONObject jsonObject, String intToRetrive) {

        try {
            return jsonObject.getInt(intToRetrive);
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static String getStringInJson(JSONObject jsonObject, String stringToRetrieve) {
        try {
            return jsonObject.getString(stringToRetrieve);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }

    }
}
