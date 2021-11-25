package dk.tec.apidemo2;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class SwapiDataService {
    Context context;

    public SwapiDataService(Context context) {
        this.context = context;
    }

    // Request for JSON object
    public void getJsonObject(String url, SwapiResponseListener listener) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onDataReady(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onErrorResonse(error.getMessage());
                    }
                }
        );
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    // Request for JSON-Array object
    public void getJsoArrayObject(String url, SwapiResponseListener listener) {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //jsonObject[0] = response;
                        listener.onDataReady(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onErrorResonse(error.getMessage());
                    }
                }
        );
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

}
