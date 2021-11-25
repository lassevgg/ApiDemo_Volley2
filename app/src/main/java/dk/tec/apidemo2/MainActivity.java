package dk.tec.apidemo2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    public static final String SWAPI_DEV_API = "https://swapi.dev/api/";
    //    public static final String SWAPI_DEV_API = "https://api.nobelprize.org/2.1/laureates";
    //    private TextView txtData;
    ListView lstNames;
    //    ArrayAdapter adapter;
    private SwapiDataService dataService;
    ArrayList<String> subject, url;
    MyListAdapter myListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
//            Toast.makeText(this, "P: " + txtUrl.getText(), Toast.LENGTH_SHORT).show();
        });

        fetcData();
    }

    private void fetcData() {
        dataService.getJsonObject(SWAPI_DEV_API, new SwapiResponseListener() {
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
            public void onDataReady(JSONArray json) { }

            @Override
            public void onErrorResonse(String err) {
                // Skrive kode der håndterer HTTP-fejl
                Toast.makeText(MainActivity.this, err, Toast.LENGTH_SHORT).show();
            }
        });
    }
}