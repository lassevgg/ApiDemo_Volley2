package dk.tec.apidemo2;

import org.json.JSONArray;
import org.json.JSONObject;

public interface SwapiResponseListener {
    public void onDataReady(JSONObject json);
    public void onDataReady(JSONArray json);
    public void onErrorResonse(String err);

}
