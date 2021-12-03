package dk.tec.apidemo2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface SwapiResponseListener {
    public void onDataReady(JSONObject json);
    public void onDataReady(JSONArray json) throws JSONException;
    public void onErrorResonse(String err);

}
