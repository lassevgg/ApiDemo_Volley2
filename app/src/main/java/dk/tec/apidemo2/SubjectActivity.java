package dk.tec.apidemo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class SubjectActivity extends AppCompatActivity {

    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        txt = findViewById(R.id.txt);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        txt.setText(url + "\n");
        fetcData(url);
    }

    private void fetcData(String url) {
        SwapiDataService dataService = new SwapiDataService(SubjectActivity.this);
        dataService.getJsonObject(url, new SwapiResponseListener() {
            @Override
            public void onDataReady(JSONObject json) {
                txt.setText("");
                try {
                    // For https://swapi.dev/api er det "results" der er interessant
                    JSONArray jsonArray = json.getJSONArray("results");

                    // Iteration over jsonArray
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        // Iteration over jsonObject.keys()
                        Iterator<String> keys = jsonObject.keys();
                        while(keys.hasNext()) {
                            String k = keys.next();

                            // hvis elementet er et json-array, spring det over
                            if(jsonObject.get(k) instanceof JSONArray) continue;

                            String v = jsonObject.getString(k);
                            txt.append(Helpers.capitalize(k).replace("_", " ") + ": " + v + "\n");
                        }
                        txt.append("\n");
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDataReady(JSONArray json) { }

            @Override
            public void onErrorResonse(String err) {
                // Skrive kode der håndterer HTTP-fejl
                Toast.makeText(SubjectActivity.this, err, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}