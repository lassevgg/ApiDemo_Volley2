package dk.tec.apidemo2;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    //public static final String API = "https://swapi.dev/api/";
    public static final String API = "http://10.0.2.2:8080/src/api";
    ListView lstNames;
    private SwapiDataService dataService;
    ArrayList<String> subject, url;
    MyListAdapter myListAdapter;
    private Button btnCreateNew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateNew = findViewById(R.id.btnCreateNew);
        lstNames = findViewById(R.id.lstNames);
        dataService = new SwapiDataService(this);

        subject = new ArrayList<>();
        url = new ArrayList<>();

        myListAdapter = new MyListAdapter(this, subject, url);

        lstNames.setAdapter(myListAdapter);

        lstNames.setOnItemClickListener((parent, view, position, id) -> {
            TextView txtUrl = parent.getChildAt(position).findViewById(R.id.url);
            Intent intent = new Intent(this, SubjectActivity.class);
            intent.putExtra("url", txtUrl.getText());
            startActivity(intent);
        });

        fetchData();

        btnCreateNew.setOnClickListener(v -> {

        });
    }

    private void fetchData() {
        dataService.getJsoArrayObject(API, new SwapiResponseListener() {
            @Override
            public void onDataReady(JSONObject json) {
                Iterator<String> keys = json.keys();
                while(keys.hasNext()) {
                    String k = keys.next();
                    try {
                        String v = json.getString(k);
                        subject.add(Helpers.capitalize(k));
                        url.add(v);
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                myListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onDataReady(JSONArray array) throws JSONException {

                for (int i = 0; i < array.length(); i++){
                    JSONObject json = array.getJSONObject(i);
                    Iterator<String> keys = json.keys();

                    while(keys.hasNext()) {
                        String k = keys.next();
                        try {
                            String v = json.getString(k);
                            subject.add(Helpers.capitalize(k));
                            url.add(v);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                myListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorResonse(String err) {
                // Skrive kode der håndterer HTTP-fejl
                Toast.makeText(MainActivity.this, err, Toast.LENGTH_SHORT).show();
            }
        });
    }
}